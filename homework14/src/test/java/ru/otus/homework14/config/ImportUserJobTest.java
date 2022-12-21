package ru.otus.homework14.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework14.model.mongo.Author;
import ru.otus.homework14.model.mongo.Book;
import ru.otus.homework14.model.mongo.Comment;
import ru.otus.homework14.model.mongo.Genre;
import ru.otus.homework14.service.AuthorService;
import ru.otus.homework14.service.BookService;
import ru.otus.homework14.service.CommentService;
import ru.otus.homework14.service.GenreService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@SpringBatchTest
class ImportUserJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private MongoItemReader<Book> readerBook;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private ItemProcessor<Book, ru.otus.homework14.model.rdb.Book> processorBook;
    @Autowired
    private ItemProcessor<Author, ru.otus.homework14.model.rdb.Author> processorAuthor;
    @Autowired
    private ItemProcessor<Genre, ru.otus.homework14.model.rdb.Genre> processorGenre;
    @Autowired
    private ItemProcessor<Comment, ru.otus.homework14.model.rdb.Comment> processorComment;

    @Autowired
    private MongoTemplate template;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(JobConfig.IMPORT_USER_JOB_NAME);
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
    public void shouldConvertSuccessfully() throws Exception {
        Author author = new Author("1", "My name");
        Genre genre = new Genre("2", "My favorite genre");
        Book book = new Book("3", "My book", author, genre);
        Comment comment = new Comment("4", "My lovely testimonials", book);
        ru.otus.homework14.model.rdb.Author toAuthor = processorAuthor.process(author);
        ru.otus.homework14.model.rdb.Genre toGenre = processorGenre.process(genre);
        ru.otus.homework14.model.rdb.Book toBook = processorBook.process(book);
        ru.otus.homework14.model.rdb.Comment toComment = processorComment.process(comment);

        assertNotNull(toAuthor);
        assertNotNull(toGenre);
        assertNotNull(toBook);
        assertNotNull(toComment);

        assertThat(toBook.getId()).isEqualTo(1);
        assertThat(toBook.getName()).isEqualTo("My book");
        assertThat(toBook.getAuthorId()).isEqualTo(1);
        assertThat(toBook.getGenreId()).isEqualTo(1);

        assertThat(toComment.getId()).isEqualTo(1);
        assertThat(toComment.getDescription()).isEqualTo("My lovely testimonials");
        assertThat(toComment.getBookId()).isEqualTo(1);
    }
}
