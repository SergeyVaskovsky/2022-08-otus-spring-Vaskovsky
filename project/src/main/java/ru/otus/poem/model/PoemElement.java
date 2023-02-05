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
//@Table(name = "poem_element")
@NoArgsConstructor
@AllArgsConstructor
public class PoemElement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @NotNull
    long id;
}
