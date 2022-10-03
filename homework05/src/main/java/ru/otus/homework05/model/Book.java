package ru.otus.homework05.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
    private long id;
    private String name;
    private Author author;
    private Genre genre;
}
