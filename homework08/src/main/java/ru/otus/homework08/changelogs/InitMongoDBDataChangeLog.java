package ru.otus.homework08.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework08.repository.AuthorRepository;
import ru.otus.homework08.repository.BookRepository;
import ru.otus.homework08.repository.CommentRepository;
import ru.otus.homework08.repository.GenreRepository;
import ru.otus.homework08.model.Author;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Comment;
import ru.otus.homework08.model.Genre;


@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author pushkin = new Author("1", "Пушкин Александр Сергеевич");
    private Author kyte = new Author("2", "Кайт Том");
    private Author mcConnell = new Author("3", "Макконел Стивен");
    private Genre fiction = new Genre("1", "Фантастика");
    private Genre novell = new Genre("2", "Роман");
    private Genre science = new Genre("3", "Научпоп");
    private Book fictionAndOrdinaryPeople = new Book("1", "Фантастика и обычные люди", kyte, fiction);
    private Book novellAndSomething = new Book("2", "Онегин и прочий бред", pushkin, novell);
    private Book code = new Book("3", "Совершенный код", mcConnell, science);
    private Comment good = new Comment("1", "Хорошо", fictionAndOrdinaryPeople);
    private Comment bad = new Comment("2", "Плохо", fictionAndOrdinaryPeople);
    private Comment soso = new Comment("3", "Ни рыба не мясо", fictionAndOrdinaryPeople);




    @ChangeSet(order = "000", id = "dropDB", author = "vaskovsky", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "vaskovsky", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository){
        authorRepository.save(pushkin);
        authorRepository.save(kyte);
        authorRepository.save(mcConnell);
    }

    @ChangeSet(order = "002", id = "initGenres", author = "vaskovsky", runAlways = true)
    public void initGenres(GenreRepository genreRepository){
        genreRepository.save(fiction);
        genreRepository.save(novell);
        genreRepository.save(science);
    }

    @ChangeSet(order = "003", id = "initBooks", author = "vaskovsky", runAlways = true)
    public void initBooks(BookRepository bookRepository){
        bookRepository.save(fictionAndOrdinaryPeople);
        bookRepository.save(novellAndSomething);
        bookRepository.save(code);
    }

    @ChangeSet(order = "004", id = "initComments", author = "vaskovsky", runAlways = true)
    public void initComments(CommentRepository commentRepository){
        commentRepository.save(good);
        commentRepository.save(bad);
        commentRepository.save(soso);
    }
}
