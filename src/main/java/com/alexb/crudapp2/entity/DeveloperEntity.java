package com.alexb.crudapp2.entity;

import java.util.List;
import java.util.Objects;

public class DeveloperEntity {
    private long id;
    private String firstName;
    private String lastName;
    private List<SkillEntity> skills;
    private SpecialtyEntity specialty;
    private Status status;

    public DeveloperEntity() {
    }

    public DeveloperEntity(String firstName, String lastName, List<SkillEntity> skills, SpecialtyEntity specialty, Status status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = skills;
        this.specialty = specialty;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<SkillEntity> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillEntity> skills) {
        this.skills = skills;
    }

    public SpecialtyEntity getSpecialty() {
        return specialty;
    }

    public void setSpecialty(SpecialtyEntity specialty) {
        this.specialty = specialty;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + "." + firstName + " " + lastName +
                "\nSpecialty: " + specialty +
                "\nSkills: " + skills + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeveloperEntity developer = (DeveloperEntity) o;
        return id == developer.id && Objects.equals(firstName, developer.firstName) && Objects.equals(lastName, developer.lastName) && Objects.equals(skills, developer.skills) && Objects.equals(specialty, developer.specialty) && status == developer.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, skills, specialty, status);
    }
}
