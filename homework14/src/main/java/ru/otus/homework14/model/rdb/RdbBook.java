package ru.otus.homework14.model.rdb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class RdbBook {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "authorId")
    private long authorId;

    @Column(name = "genreId")
    private long genreId;

    public RdbBook(String name, Long authorId, Long genreId) {
        this.name = name;
        this.authorId = authorId;
        this.genreId = genreId;
    }
}
