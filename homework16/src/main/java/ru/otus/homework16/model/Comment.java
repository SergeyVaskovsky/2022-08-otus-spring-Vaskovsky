package ru.otus.homework16.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @EqualsAndHashCode.Exclude
    @Column(name = "description")
    private String description;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    private static LocalDateTime lastAddCommentInstant = LocalDateTime.now();
    public Comment(String description) {
        this.description = description;
    }
    @PrePersist
    public void onInsert(){
        lastAddCommentInstant = LocalDateTime.now();
    }

    public static LocalDateTime getLastAddCommentInstant(){
        return lastAddCommentInstant;
    }
}
