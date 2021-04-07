package com.rbc.lordsofplanets.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "lords")
public class Lord {

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
