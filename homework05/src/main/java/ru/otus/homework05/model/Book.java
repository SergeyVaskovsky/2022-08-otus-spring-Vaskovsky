package ru.otus.homework05.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class Book {
    private long id;
    @EqualsAndHashCode.Exclude
    private String name;
    @EqualsAndHashCode.Exclude
    private Author author;
    @EqualsAndHashCode.Exclude
    private Genre genre;
}
