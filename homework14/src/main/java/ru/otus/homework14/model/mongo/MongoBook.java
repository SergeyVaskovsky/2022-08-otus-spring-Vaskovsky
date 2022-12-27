package ru.otus.homework14.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "book")
public class MongoBook {
    @Id
    private String id;

    @EqualsAndHashCode.Exclude
    private String name;

    @EqualsAndHashCode.Exclude
    private MongoAuthor mongoAuthor;

    @EqualsAndHashCode.Exclude
    private MongoGenre mongoGenre;

    public MongoBook(String name, MongoAuthor mongoAuthor, MongoGenre mongoGenre) {
        this.name = name;
        this.mongoAuthor = mongoAuthor;
        this.mongoGenre = mongoGenre;
    }
}
