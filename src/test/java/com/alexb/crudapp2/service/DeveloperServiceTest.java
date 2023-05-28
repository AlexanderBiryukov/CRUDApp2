package com.alexb.crudapp2.service;

import com.alexb.crudapp2.model.Developer;
import com.alexb.crudapp2.model.Skill;
import com.alexb.crudapp2.model.Specialty;
import com.alexb.crudapp2.model.Status;
import com.alexb.crudapp2.repository.DeveloperRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeveloperServiceTest {
    @Mock
    DeveloperRepository developerRepository;
    DeveloperService developerService;

    @Test
    public void addDeveloperTest() {
        List<Skill> skillList = new ArrayList<>();
        skillList.add(new Skill("Java"));
        skillList.add(new Skill("Spring"));
        Developer developer = new Developer(
                "Ivan", "Ivanov", skillList, new Specialty("Backend developer"), Status.ACTIVE);

        when(developerRepository.save(developer)).thenReturn(developer);

        Developer developerFromDB = developerService.creationDev(developer);

        Mockito.verify(developerRepository, Mockito.times(1)).save(developer);
    }


}