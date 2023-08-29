package com.example.demo.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Entity
@Getter
@Setter
public class Squad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long squadId;

    // Add more fields here: name, description and etc
    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL)
    //@JoinColumn(name = "developers")
    private List<Developer> developers;

    public void addDeveloper(Developer developer) {
        if (developers == null) {
            developers = new ArrayList<>();
        }
        developers.add(developer);
        developer.setSquad(this);
    }

    public void removeDeveloper(Developer developer) {
        if (developer != null) {
            developers.remove(developer);
            developer.setSquad(null);
        }


    }
}
