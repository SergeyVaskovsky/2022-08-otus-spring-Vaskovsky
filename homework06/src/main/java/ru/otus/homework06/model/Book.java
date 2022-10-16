package ru.otus.homework06.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.otus.homework06.model.Author;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @EqualsAndHashCode.Exclude
    @Column(name = "name")
    private String name;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    private Author author;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    private Genre genre;
}
