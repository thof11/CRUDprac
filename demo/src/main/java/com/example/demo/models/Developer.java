package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
//@EqualsAndHashCode
@Getter
@Setter
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long developerId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private int age;

    @Column
    private String occupation;

    @ManyToOne // pass arguments to that annotation
    @JoinColumn(name = "squad_id")
    @JsonIgnore
    private Squad squad;
}
