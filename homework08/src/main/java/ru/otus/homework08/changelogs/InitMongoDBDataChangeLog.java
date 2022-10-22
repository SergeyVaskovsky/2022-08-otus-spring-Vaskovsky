package ru.otus.homework08.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework08.dao.AuthorDao;
import ru.otus.homework08.dao.BookDao;
import ru.otus.homework08.dao.CommentDao;
import ru.otus.homework08.dao.GenreDao;
import ru.otus.homework08.model.Author;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Comment;
import ru.otus.homework08.model.Genre;


@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author pushkin = new Author(1L, "Пушкин Александр Сергеевич");
    private Author kyte = new Author(2L, "Кайт Том");
    private Author mcConnell = new Author(3L, "Макконел Стивен");
    private Genre fiction = new Genre(1L, "Фантастика");
    private Genre novell = new Genre(2L, "Роман");
    private Genre science = new Genre(3L, "Научпоп");
    private Book fictionAndOrdinaryPeople = new Book(1L, "Фантастика и обычные люди", kyte, fiction);
    private Book novellAndSomething = new Book(2L, "Онегин и прочий бред", pushkin, novell);
    private Book code = new Book(3L, "Совершенный код", mcConnell, science);
    private Comment good = new Comment(1L, "Хорошо", fictionAndOrdinaryPeople);
    private Comment bad = new Comment(2L, "Плохо", fictionAndOrdinaryPeople);
    private Comment soso = new Comment(3L, "Ни рыба не мясо", fictionAndOrdinaryPeople);




    @ChangeSet(order = "000", id = "dropDB", author = "vaskovsky", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "vaskovsky", runAlways = true)
    public void initAuthors(AuthorDao authorDao){
        authorDao.save(pushkin);
        authorDao.save(kyte);
        authorDao.save(mcConnell);
    }

    @ChangeSet(order = "002", id = "initGenres", author = "vaskovsky", runAlways = true)
    public void initGenres(GenreDao genreDao){
        genreDao.save(fiction);
        genreDao.save(novell);
        genreDao.save(science);
    }

    @ChangeSet(order = "003", id = "initBooks", author = "vaskovsky", runAlways = true)
    public void initBooks(BookDao bookDao){
        bookDao.save(fictionAndOrdinaryPeople);
        bookDao.save(novellAndSomething);
        bookDao.save(code);
    }

    @ChangeSet(order = "004", id = "initComments", author = "vaskovsky", runAlways = true)
    public void initComments(CommentDao commentDao){
        commentDao.save(good);
        commentDao.save(bad);
        commentDao.save(soso);
    }
}
