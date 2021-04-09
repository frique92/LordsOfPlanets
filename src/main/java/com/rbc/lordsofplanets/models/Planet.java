package com.rbc.lordsofplanets.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "planets")
public class Planet {

    public Planet() {
    }

    public Planet(int id, String name, Lord lord) {
        this.id = id;
        this.name = name;
        this.lord = lord;
    }

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

    public Lord getLord() {
        return lord;
    }

    public void setLord(Lord lord) {
        this.lord = lord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return id == planet.id && name.equals(planet.name) && Objects.equals(lord, planet.lord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lord);
    }
}
