package com.alexb.crudapp2.service;

import com.alexb.crudapp2.model.Developer;
import com.alexb.crudapp2.repository.DeveloperRepository;
import com.alexb.crudapp2.repository.jdbc.JdbcDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperService {
    public final DeveloperRepository developerRepository = new JdbcDeveloperRepositoryImpl();

    public List<Developer> getAllDevelopers() {
        return developerRepository.getAll();
    }

    public Developer creationDev(Developer developer) {
        return developerRepository.save(developer);
    }

    public Developer getDevById(Long id) {
        return developerRepository.getById(id);
    }

    public Developer editDev(Developer developer) {
        return developerRepository.update(developer);
    }

    public void deleteDev(Long id) {
        developerRepository.deleteById(id);
    }


}
