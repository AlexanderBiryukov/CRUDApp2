package com.alexb.crudapp2.service;

import com.alexb.crudapp2.model.Specialty;
import com.alexb.crudapp2.repository.SpecialtyRepository;
import com.alexb.crudapp2.repository.jdbc.JdbcSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyService {
    SpecialtyRepository specialtyRepository = new JdbcSpecialtyRepositoryImpl();

    public List<Specialty> getAllSpecialties() {
        return specialtyRepository.getAll();
    }

    public Specialty creationSpecialty(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public Specialty editSpecialty(Specialty specialty) {
        return specialtyRepository.update(specialty);
    }

    public void deleteSpecialty(Long id) {
        specialtyRepository.deleteById(id);
    }

}
