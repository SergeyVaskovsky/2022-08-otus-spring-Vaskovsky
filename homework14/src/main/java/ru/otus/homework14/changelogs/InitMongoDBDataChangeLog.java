package ru.otus.homework14.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework14.model.mongo.Author;
import ru.otus.homework14.model.mongo.Book;
import ru.otus.homework14.model.mongo.Comment;
import ru.otus.homework14.model.mongo.Genre;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {
    private Author pushkin = new Author("1", "Пушкин Александр Сергеевич");
    private Author kyte = new Author("2", "Кайт Том");
    private Author mcConnell = new Author("3", "Макконел Стивен");
    private Genre fiction = new Genre("1", "Фантастика");
    private Genre novell = new Genre("2", "Роман");
    private Genre science = new Genre("3", "Научпоп");
    private Book fictionAndOrdinaryPeople = new Book("1", "Фантастика и обычные люди", kyte, fiction);
    private Comment good = new Comment("1", "Хорошо", fictionAndOrdinaryPeople);
    private Comment bad = new Comment("2", "Плохо", fictionAndOrdinaryPeople);
    private Comment soso = new Comment("3", "Ни рыба не мясо", fictionAndOrdinaryPeople);
    private Book novellAndSomething = new Book("2", "Онегин и прочий бред", pushkin, novell);
    private Book code = new Book("3", "Совершенный код", mcConnell, science);

    @ChangeSet(order = "000", id = "dropDB", author = "vaskovsky", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "vaskovsky", runAlways = true)
    public void initAuthors(MongockTemplate template) {
        template.save(pushkin);
        template.save(kyte);
        template.save(mcConnell);
    }

    @ChangeSet(order = "002", id = "initGenres", author = "vaskovsky", runAlways = true)
    public void initGenres(MongockTemplate template) {
        template.save(fiction);
        template.save(novell);
        template.save(science);
    }

    @ChangeSet(order = "003", id = "initBooks", author = "vaskovsky", runAlways = true)
    public void initBooks(MongockTemplate template) {
        template.save(fictionAndOrdinaryPeople);
        template.save(novellAndSomething);
        template.save(code);
    }

    @ChangeSet(order = "004", id = "initComments", author = "vaskovsky", runAlways = true)
    public void initComments(MongockTemplate template) {
        template.save(good);
        template.save(bad);
        template.save(soso);
    }
}
