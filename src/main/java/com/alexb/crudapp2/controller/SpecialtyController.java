package com.alexb.crudapp2.controller;

import com.alexb.crudapp2.entity.SpecialtyEntity;
import com.alexb.crudapp2.service.SpecialtyService;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyService specialtyService = new SpecialtyService();

    public List<SpecialtyEntity> getListAllSpecialties() {
        return specialtyService.getAllSpecialties();
    }

    public SpecialtyEntity createSpecialty(String name) {
        SpecialtyEntity specialty = new SpecialtyEntity();
        specialty.setName(name);
        return specialtyService.creationSpecialty(specialty);
    }

    public SpecialtyEntity updateSpecialty(long id, String name) {
        SpecialtyEntity specialty = new SpecialtyEntity();
        specialty.setId(id);
        specialty.setName(name);
        return specialtyService.editSpecialty(specialty);
    }

    public void deleteSpecialtyById(Long id) {
        specialtyService.deleteSpecialty(id);
    }
}
