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
import ru.otus.homework14.model.mongo.MongoAuthor;
import ru.otus.homework14.model.mongo.MongoBook;
import ru.otus.homework14.model.mongo.MongoComment;
import ru.otus.homework14.model.mongo.MongoGenre;
import ru.otus.homework14.model.rdb.RdbAuthor;
import ru.otus.homework14.model.rdb.RdbBook;
import ru.otus.homework14.model.rdb.RdbComment;
import ru.otus.homework14.model.rdb.RdbGenre;
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
    private MongoItemReader<MongoBook> readerBook;
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
            MongoBook actualMongoBook;
            readerBook.open(stepExecution.getExecutionContext());
            while ((actualMongoBook = readerBook.read()) != null) {

                MongoBook expectedMongoBook = template.findById(actualMongoBook.getId(), MongoBook.class);
                assertThat(actualMongoBook.getName()).isEqualTo(expectedMongoBook.getName());
                assertThat(actualMongoBook.getMongoAuthor().getId()).isEqualTo(expectedMongoBook.getMongoAuthor().getId());
                assertThat(actualMongoBook.getMongoGenre().getId()).isEqualTo(expectedMongoBook.getMongoGenre().getId());

            }
            readerBook.close();
            return null;
        });
    }

    @Test
    public void shouldCorrectSaveAuthor() {
        MongoAuthor mongoAuthor = new MongoAuthor("1", "My name");
        customAuthorWriter.write(List.of(mongoAuthor));
        Optional<RdbAuthor> a = authorRepository.findById(1L);
        assertNotNull(a.get());
        assertThat(a.get().getName()).isEqualTo("My name");
    }

    @Test
    public void shouldCorrectSaveGenre() {
        MongoGenre mongoGenre = new MongoGenre("2", "My favorite genre");
        customGenreWriter.write(List.of(mongoGenre));
        Optional<RdbGenre> g = genreRepository.findById(1L);
        assertNotNull(g.get());
        assertThat(g.get().getName()).isEqualTo("My favorite genre");
    }

    @Test
    public void shouldCorrectSaveBook() {
        MongoAuthor mongoAuthor = new MongoAuthor("1", "My name");
        MongoGenre mongoGenre = new MongoGenre("2", "My favorite genre");
        MongoBook mongoBook = new MongoBook("3", "My book", mongoAuthor, mongoGenre);
        customAuthorWriter.write(List.of(mongoAuthor));
        customGenreWriter.write(List.of(mongoGenre));
        customBookWriter.write(List.of(mongoBook));
        Optional<RdbBook> b = bookRepository.findById(1L);
        assertNotNull(b.get());
        assertThat(b.get().getName()).isEqualTo("My book");
        assertThat(b.get().getAuthorId()).isEqualTo(1L);
        assertThat(b.get().getGenreId()).isEqualTo(1L);
    }

    @Test
    public void shouldCorrectSaveComment() {
        MongoAuthor mongoAuthor = new MongoAuthor("1", "My name");
        MongoGenre mongoGenre = new MongoGenre("2", "My favorite genre");
        MongoBook mongoBook = new MongoBook("3", "My book", mongoAuthor, mongoGenre);
        MongoComment mongoComment = new MongoComment("4", "My lovely testimonials", mongoBook);
        customAuthorWriter.write(List.of(mongoAuthor));
        customGenreWriter.write(List.of(mongoGenre));
        customBookWriter.write(List.of(mongoBook));
        customCommentWriter.write(List.of(mongoComment));
        Optional<RdbComment> c = commentRepository.findById(1L);
        assertNotNull(c.get());
        assertThat(c.get().getDescription()).isEqualTo("My lovely testimonials");
        assertThat(c.get().getBookId()).isEqualTo(1L);
    }
}
