package ru.otus.poem.model;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "poem")
@SequenceGenerator(name = "poem_gen", sequenceName = "poem_id_seq", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class Poem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poem_gen")
    @Column(name = "id")
    @NotNull
    private long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PoemElement> elements = new ArrayList<>();

    @Column(name = "publish_time")
    private LocalDateTime publishTime;

    public Poem(String title) {
        this.title = title;
    }
}
