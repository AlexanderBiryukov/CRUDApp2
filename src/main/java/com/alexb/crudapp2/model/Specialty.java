package com.alexb.crudapp2.model;

import java.util.Objects;

public class Specialty {
    private String name;
    private long id;

    public Specialty() {
    }

    public Specialty(String name) {
        this.name = name;
    }

    public Specialty(String name, long id) {
        this.name = name;
        this.id = id;
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
        Specialty specialty = (Specialty) o;
        return id == specialty.id && Objects.equals(name, specialty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
