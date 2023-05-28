package com.alexb.crudapp2.model;

import java.util.Objects;

public class Skill {
    private String name;
    private long id;

    public Skill() {
    }

    public Skill(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public Skill(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "." + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id == skill.id && Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
