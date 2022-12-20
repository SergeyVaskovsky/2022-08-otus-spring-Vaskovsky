package ru.otus.homework14.model.rdb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "bookId")
    private long bookId;
}
