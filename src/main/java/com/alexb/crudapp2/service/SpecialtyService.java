package com.alexb.crudapp2.service;

import com.alexb.crudapp2.entity.SpecialtyEntity;
import com.alexb.crudapp2.repository.SpecialtyRepository;
import com.alexb.crudapp2.repository.jdbc.JdbcSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public  SpecialtyService() {
        this.specialtyRepository = new JdbcSpecialtyRepositoryImpl();
    }
    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public List<SpecialtyEntity> getAllSpecialties() {
        return specialtyRepository.getAll();
    }

    public SpecialtyEntity creationSpecialty(SpecialtyEntity specialty) {
        return specialtyRepository.save(specialty);
    }

    public SpecialtyEntity editSpecialty(SpecialtyEntity specialty) {
        return specialtyRepository.update(specialty);
    }

    public void deleteSpecialty(Long id) {
        specialtyRepository.deleteById(id);
    }

}
