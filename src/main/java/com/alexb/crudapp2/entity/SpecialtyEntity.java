package com.alexb.crudapp2.entity;

import java.util.Objects;

public class SpecialtyEntity {
    private long id;
    private String name;

    public SpecialtyEntity() {
    }

    public SpecialtyEntity(String name) {
        this.name = name;
    }

    public SpecialtyEntity(long id, String name) {
        this.id = id;
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
        SpecialtyEntity specialty = (SpecialtyEntity) o;
        return id == specialty.id && Objects.equals(name, specialty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
