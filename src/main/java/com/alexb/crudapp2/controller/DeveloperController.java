package com.alexb.crudapp2.controller;

import com.alexb.crudapp2.entity.DeveloperEntity;
import com.alexb.crudapp2.entity.SkillEntity;
import com.alexb.crudapp2.entity.SpecialtyEntity;
import com.alexb.crudapp2.entity.Status;
import com.alexb.crudapp2.service.DeveloperService;

import java.util.List;

public class DeveloperController {
    private final DeveloperService developerService = new DeveloperService();

    public List<DeveloperEntity> getListAllDevelopers() {
        return developerService.getAllDevelopers();
    }

    public DeveloperEntity createDeveloper(String firstName, String lastName, List<SkillEntity> skills, SpecialtyEntity specialty) {
        DeveloperEntity developer = new DeveloperEntity();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setSkills(skills);
        developer.setSpecialty(specialty);
        developer.setStatus(Status.ACTIVE);
        return developerService.creationDev(developer);

    }

    public DeveloperEntity getDevelopersById(Long id) {
        return developerService.getDevById(id);
    }

    public DeveloperEntity updateDeveloper(long id, String firstName, String lastName, List<SkillEntity> skills, SpecialtyEntity specialty) {
        DeveloperEntity developer = new DeveloperEntity(firstName, lastName, skills, specialty, Status.ACTIVE);
        developer.setId(id);
        return developerService.editDev(developer);
    }

    public void deleteDeveloperById(Long id) {
        developerService.deleteDev(id);
    }
}
