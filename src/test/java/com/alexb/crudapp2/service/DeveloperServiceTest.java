package com.alexb.crudapp2.service;

import com.alexb.crudapp2.model.Developer;
import com.alexb.crudapp2.model.Skill;
import com.alexb.crudapp2.model.Specialty;
import com.alexb.crudapp2.model.Status;
import com.alexb.crudapp2.repository.DeveloperRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DeveloperServiceTest {
    @Mock
    DeveloperRepository developerRepository;
    @InjectMocks
    DeveloperService developerService;

    @Test
    public void createDeveloperTest() {
        List<Skill> skillList = new ArrayList<>();
        skillList.add(new Skill("Java"));
        skillList.add(new Skill("Spring"));
        Developer developer = new Developer(
                "Ivan", "Ivanov", skillList, new Specialty("Backend developer"), Status.ACTIVE);

        Mockito.when(developerRepository.save(developer)).thenReturn(developer);

        Developer developerFromDB = developerService.creationDev(developer);

        Assertions.assertNotNull(developerFromDB);
        Assertions.assertEquals(developer, developerFromDB);
        Mockito.verify(developerRepository, Mockito.times(1)).save(developer);
    }

    @Test
    public void readDeveloperTest() {
        List<Skill> skillList = new ArrayList<>();
        skillList.add(new Skill("Java"));
        skillList.add(new Skill("Spring"));
        Developer developer = new Developer(
                "Ivan", "Ivanov", skillList, new Specialty("Backend developer"), Status.ACTIVE);

        List<Developer> developerList = new ArrayList<>();
        developerList.add(developer);
        developerList.add(developer);

        Mockito.when(developerRepository.getAll()).thenReturn(developerList);

        List<Developer> developersFromDB = developerService.getAllDevelopers();

        Assertions.assertEquals(developerList, developersFromDB);
        Mockito.verify(developerRepository, Mockito.times(1)).getAll();
    }

    @Test
    public void updateDeveloperTest() {
        List<Skill> skillList = new ArrayList<>();
        skillList.add(new Skill("Java"));
        skillList.add(new Skill("Spring"));
        Developer developer = new Developer(
                "Ivan", "Ivanov", skillList, new Specialty("Backend developer"), Status.ACTIVE);

        Mockito.when(developerRepository.update(developer)).thenReturn(developer);

        Developer developerFromDB = developerService.editDev(developer);

        Assertions.assertEquals(developer, developerFromDB);
        Assertions.assertNotNull(developerFromDB);
        Mockito.verify(developerRepository, Mockito.times(1)).update(developer);
    }

    @Test
    public void deleteDeveloperTest() {
        developerService.deleteDev(1L);
        Mockito.verify(developerRepository, Mockito.times(1)).deleteById(1L);
    }


}