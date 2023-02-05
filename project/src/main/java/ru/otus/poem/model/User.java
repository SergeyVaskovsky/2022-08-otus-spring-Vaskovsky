package ru.otus.poem.model;

import lombok.Value;

@Value
public class User {
    long id;
    String email;
    String name;
    String password;
}
