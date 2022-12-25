package ru.otus.homework14.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework14.model.mongo.Author;
import ru.otus.homework14.model.mongo.Book;
import ru.otus.homework14.model.mongo.Comment;
import ru.otus.homework14.model.mongo.Genre;
import ru.otus.homework14.repository.AuthorRepository;
import ru.otus.homework14.repository.BookRepository;
import ru.otus.homework14.repository.CommentRepository;
import ru.otus.homework14.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@SpringBatchTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ImportJobTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    @Autowired
    private MongoItemReader<Book> readerBook;
    @Autowired
    private MongoTemplate template;
    @Autowired
    private CustomAuthorWriter customAuthorWriter;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CustomGenreWriter customGenreWriter;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private CustomBookWriter customBookWriter;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CustomCommentWriter customCommentWriter;
    @Autowired
    private CommentRepository commentRepository;


    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(JobConfig.IMPORT_JOB_NAME);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }

    @Test
    public void givenMockedStep_whenReaderCalled_thenSuccess() throws Exception {
        StepExecution stepExecution = MetaDataInstanceFactory
                .createStepExecution();

        StepScopeTestUtils.doInStepScope(stepExecution, () -> {
            Book actualBook;
            readerBook.open(stepExecution.getExecutionContext());
            while ((actualBook = readerBook.read()) != null) {

                Book expectedBook = template.findById(actualBook.getId(), Book.class);
                assertThat(actualBook.getName()).isEqualTo(expectedBook.getName());
                assertThat(actualBook.getAuthor().getId()).isEqualTo(expectedBook.getAuthor().getId());
                assertThat(actualBook.getGenre().getId()).isEqualTo(expectedBook.getGenre().getId());

            }
            readerBook.close();
            return null;
        });
    }

    @Test
    public void shouldCorrectSaveAuthor() {
        Author author = new Author("1", "My name");
        customAuthorWriter.write(List.of(author));
        Optional<ru.otus.homework14.model.rdb.Author> a = authorRepository.findById(1L);
        assertNotNull(a.get());
        assertThat(a.get().getName()).isEqualTo("My name");
    }

    @Test
    public void shouldCorrectSaveGenre() {
        Genre genre = new Genre("2", "My favorite genre");
        customGenreWriter.write(List.of(genre));
        Optional<ru.otus.homework14.model.rdb.Genre> g = genreRepository.findById(1L);
        assertNotNull(g.get());
        assertThat(g.get().getName()).isEqualTo("My favorite genre");
    }

    @Test
    public void shouldCorrectSaveBook() {
        Author author = new Author("1", "My name");
        Genre genre = new Genre("2", "My favorite genre");
        Book book = new Book("3", "My book", author, genre);
        customAuthorWriter.write(List.of(author));
        customGenreWriter.write(List.of(genre));
        customBookWriter.write(List.of(book));
        Optional<ru.otus.homework14.model.rdb.Book> b = bookRepository.findById(1L);
        assertNotNull(b.get());
        assertThat(b.get().getName()).isEqualTo("My book");
        assertThat(b.get().getAuthorId()).isEqualTo(1L);
        assertThat(b.get().getGenreId()).isEqualTo(1L);
    }

    @Test
    public void shouldCorrectSaveComment() {
        Author author = new Author("1", "My name");
        Genre genre = new Genre("2", "My favorite genre");
        Book book = new Book("3", "My book", author, genre);
        Comment comment = new Comment("4", "My lovely testimonials", book);
        customAuthorWriter.write(List.of(author));
        customGenreWriter.write(List.of(genre));
        customBookWriter.write(List.of(book));
        customCommentWriter.write(List.of(comment));
        Optional<ru.otus.homework14.model.rdb.Comment> c = commentRepository.findById(1L);
        assertNotNull(c.get());
        assertThat(c.get().getDescription()).isEqualTo("My lovely testimonials");
        assertThat(c.get().getBookId()).isEqualTo(1L);
    }
}
