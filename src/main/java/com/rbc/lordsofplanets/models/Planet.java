package com.rbc.lordsofplanets.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Planet {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "lord_id")
    private Lord lord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return id == planet.id && Objects.equals(name, planet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
