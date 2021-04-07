package com.rbc.lordsofplanets.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "lords")
public class Lord {

    public Lord() {
    }

    public Lord(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Set<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(Set<Planet> planets) {
        this.planets = planets;
    }

    @OneToMany(mappedBy = "lord")
    Set<Planet> planets;

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private int age;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lord lord = (Lord) o;
        return id == lord.id && age == lord.age && Objects.equals(name, lord.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
