package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Squad {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    // Add more fields here: name, description and etc

    @OneToMany // pass arguments to that annotation
    private List<Developer> workers;
}
