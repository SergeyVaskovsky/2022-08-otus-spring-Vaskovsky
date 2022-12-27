package ru.otus.homework14.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework14.model.mongo.MongoAuthor;
import ru.otus.homework14.model.mongo.MongoBook;
import ru.otus.homework14.model.mongo.MongoComment;
import ru.otus.homework14.model.mongo.MongoGenre;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {
    private MongoAuthor pushkin = new MongoAuthor("1", "Пушкин Александр Сергеевич");
    private MongoAuthor kyte = new MongoAuthor("2", "Кайт Том");
    private MongoAuthor mcConnell = new MongoAuthor("3", "Макконел Стивен");
    private MongoGenre fiction = new MongoGenre("1", "Фантастика");
    private MongoGenre novell = new MongoGenre("2", "Роман");
    private MongoGenre science = new MongoGenre("3", "Научпоп");
    private MongoBook fictionAndOrdinaryPeople = new MongoBook("1", "Фантастика и обычные люди", kyte, fiction);
    private MongoComment good = new MongoComment("1", "Хорошо", fictionAndOrdinaryPeople);
    private MongoComment bad = new MongoComment("2", "Плохо", fictionAndOrdinaryPeople);
    private MongoComment soso = new MongoComment("3", "Ни рыба не мясо", fictionAndOrdinaryPeople);
    private MongoBook novellAndSomething = new MongoBook("2", "Онегин и прочий бред", pushkin, novell);
    private MongoBook code = new MongoBook("3", "Совершенный код", mcConnell, science);

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
