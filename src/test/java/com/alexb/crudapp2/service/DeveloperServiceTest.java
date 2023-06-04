package com.alexb.crudapp2.service;

import com.alexb.crudapp2.entity.DeveloperEntity;
import com.alexb.crudapp2.entity.SkillEntity;
import com.alexb.crudapp2.entity.SpecialtyEntity;
import com.alexb.crudapp2.entity.Status;
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

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class DeveloperServiceTest {
    @Mock
    DeveloperRepository developerRepository;
    @InjectMocks
    DeveloperService developerService;

    private DeveloperEntity getDeveloper() {
        List<SkillEntity> skillList = new ArrayList<>();
        skillList.add(new SkillEntity("Java"));
        skillList.add(new SkillEntity("Spring"));
        return new DeveloperEntity(
                "Ivan", "Ivanov", skillList, new SpecialtyEntity("Backend developer"), Status.ACTIVE);
    }

    @Test
    public void createDeveloperTest() {
        Mockito.when(developerRepository.save(any())).thenReturn(getDeveloper());
        DeveloperEntity developerFromDB = developerService.creationDev(getDeveloper());
        Assertions.assertNotNull(developerFromDB);
        Assertions.assertEquals(getDeveloper(), developerFromDB);
        Mockito.verify(developerRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void readDeveloperTest() {
        List<SkillEntity> skillList = new ArrayList<>();
        skillList.add(new SkillEntity("Java"));
        skillList.add(new SkillEntity("Spring"));
        DeveloperEntity developer = new DeveloperEntity(
                "Ivan", "Ivanov", skillList, new SpecialtyEntity("Backend developer"), Status.ACTIVE);

        List<DeveloperEntity> developerList = new ArrayList<>();
        developerList.add(developer);
        developerList.add(developer);

        Mockito.when(developerRepository.getAll()).thenReturn(developerList);

        List<DeveloperEntity> developersFromDB = developerService.getAllDevelopers();

        Assertions.assertEquals(developerList, developersFromDB);
        Mockito.verify(developerRepository, Mockito.times(1)).getAll();
    }

    @Test
    public void updateDeveloperTest() {
        List<SkillEntity> skillList = new ArrayList<>();
        skillList.add(new SkillEntity("Java"));
        skillList.add(new SkillEntity("Spring"));
        DeveloperEntity developer = new DeveloperEntity(
                "Ivan", "Ivanov", skillList, new SpecialtyEntity("Backend developer"), Status.ACTIVE);

        Mockito.when(developerRepository.update(developer)).thenReturn(developer);

        DeveloperEntity developerFromDB = developerService.editDev(developer);

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
