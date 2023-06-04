package com.alexb.crudapp2.service;

import com.alexb.crudapp2.entity.DeveloperEntity;
import com.alexb.crudapp2.repository.DeveloperRepository;
import com.alexb.crudapp2.repository.jdbc.JdbcDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public DeveloperService() {
        this.developerRepository = new JdbcDeveloperRepositoryImpl();
    }

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public List<DeveloperEntity> getAllDevelopers() {
        return developerRepository.getAll();
    }

    public DeveloperEntity creationDev(DeveloperEntity developer) {
        return developerRepository.save(developer);
    }

    public DeveloperEntity getDevById(Long id) {
        return developerRepository.getById(id);
    }

    public DeveloperEntity editDev(DeveloperEntity developer) {
        return developerRepository.update(developer);
    }

    public void deleteDev(Long id) {
        developerRepository.deleteById(id);
    }


}
