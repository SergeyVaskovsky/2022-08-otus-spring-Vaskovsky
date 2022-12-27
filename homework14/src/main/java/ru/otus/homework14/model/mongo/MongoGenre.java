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
@Document(collection = "genre")
public class MongoGenre {
    @Id
    private String id;

    @EqualsAndHashCode.Exclude
    private String name;
}
