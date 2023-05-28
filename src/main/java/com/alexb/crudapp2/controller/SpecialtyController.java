package com.alexb.crudapp2.controller;

import com.alexb.crudapp2.model.Specialty;
import com.alexb.crudapp2.service.SpecialtyService;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyService specialtyService = new SpecialtyService();

    public List<Specialty> getListAllSpecialties() {
        return specialtyService.getAllSpecialties();
    }

    public Specialty createSpecialty(String name) {
        Specialty specialty = new Specialty();
        specialty.setName(name);
        return specialtyService.creationSpecialty(specialty);
    }

    public Specialty updateSpecialty(long id, String name) {
        Specialty specialty = new Specialty();
        specialty.setId(id);
        specialty.setName(name);
        return specialtyService.editSpecialty(specialty);
    }

    public void deleteSpecialtyById(Long id) {
        specialtyService.deleteSpecialty(id);
    }
}
