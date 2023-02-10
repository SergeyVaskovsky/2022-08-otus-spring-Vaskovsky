package ru.otus.poem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "role_gen", sequenceName = "role_id_seq", allocationSize = 1)
public class Role {
    @Id
    @SequenceGenerator(name = "role_gen", sequenceName = "role_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;
}
