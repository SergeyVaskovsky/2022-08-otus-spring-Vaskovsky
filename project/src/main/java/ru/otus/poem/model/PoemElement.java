package ru.otus.poem.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "poem_element_gen", sequenceName = "poem_element_id_seq", allocationSize = 1)
public class PoemElement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poem_element_gen")
    @Column(name = "id")
    @NotNull
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Poem poem;
}
