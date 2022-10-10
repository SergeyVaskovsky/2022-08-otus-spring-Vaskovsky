package ru.otus.homework05.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class Genre {
    private long id;
    @EqualsAndHashCode.Exclude
    private String name;
}
