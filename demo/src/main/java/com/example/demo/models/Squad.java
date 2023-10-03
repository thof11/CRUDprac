package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Squad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long squadId;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL)
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Squad squad) {
            return Objects.equals(squad.getSquadId(), this.squadId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.squadId);
    }
}
