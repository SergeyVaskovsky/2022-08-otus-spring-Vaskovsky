package ru.otus.poem.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "comment_gen", sequenceName = "comment_id_seq", allocationSize = 1)
public class Comment {
    @Column(name = "publish_time")
    LocalDateTime publishTime;
    @Column(name = "moderated")
    boolean moderated;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_gen")
    @Column(name = "id")
    private long id;
    @NotNull
    @Column(name = "text")
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Poem poem;
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment rootComment;
}
