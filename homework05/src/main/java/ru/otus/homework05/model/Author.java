package ru.otus.homework05.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class Author {
    private long id;
    @EqualsAndHashCode.Exclude
    private String name;
}
