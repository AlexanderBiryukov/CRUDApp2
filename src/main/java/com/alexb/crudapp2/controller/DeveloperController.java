package com.alexb.crudapp2.controller;

import com.alexb.crudapp2.model.Developer;
import com.alexb.crudapp2.model.Skill;
import com.alexb.crudapp2.model.Specialty;
import com.alexb.crudapp2.model.Status;
import com.alexb.crudapp2.service.DeveloperService;

import java.util.List;

public class DeveloperController {
    private final DeveloperService developerService = new DeveloperService();

    public List<Developer> getListAllDevelopers() {
        return developerService.getAllDevelopers();
    }

    public Developer createDeveloper(String firstName, String lastName, List<Skill> skills, Specialty specialty) {
        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setSkills(skills);
        developer.setSpecialty(specialty);
        developer.setStatus(Status.ACTIVE);
        return developerService.creationDev(developer);

    }

    public Developer getDevelopersById(Long id) {
        return developerService.getDevById(id);
    }

    public Developer updateDeveloper(long id, String firstName, String lastName, List<Skill> skills, Specialty specialty) {
        Developer developer = new Developer(firstName, lastName, skills, specialty, Status.ACTIVE);
        developer.setId(id);
        return developerService.editDev(developer);
    }

    public void deleteDeveloperById(Long id) {
        developerService.deleteDev(id);
    }
}
