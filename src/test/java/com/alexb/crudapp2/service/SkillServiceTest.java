package com.alexb.crudapp2.service;

import com.alexb.crudapp2.entity.SkillEntity;
import com.alexb.crudapp2.repository.SkillRepository;
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
public class SkillServiceTest {
    @Mock
    SkillRepository skillRepository;
    @InjectMocks
    SkillService skillService;

    @Test
    public void createSkillTest() {
        SkillEntity skill = new SkillEntity("Java");
        Mockito.when(skillRepository.save(skill)).thenReturn(skill);

        SkillEntity skillFromDB = skillService.creationSkill(skill);
        Assertions.assertNotNull(skillFromDB);
        Assertions.assertEquals(skill, skillFromDB);
        Mockito.verify(skillRepository, Mockito.times(1)).save(skill);
    }

    @Test
    public void readSkillTest() {
        List<SkillEntity> skillList = new ArrayList<>();
        skillList.add(new SkillEntity("Java"));
        skillList.add(new SkillEntity("Spring"));

        Mockito.when(skillRepository.getAll()).thenReturn(skillList);

        List<SkillEntity> skillsFromDB = skillService.getAllSkills();

        Assertions.assertEquals(skillList, skillsFromDB);
        Mockito.verify(skillRepository, Mockito.times(1)).getAll();
    }

    @Test
    public void updateSkillTest() {
        SkillEntity skill = new SkillEntity("Java");

        Mockito.when(skillRepository.update(skill)).thenReturn(skill);

        SkillEntity skillFromDB = skillService.editSkill(skill);

        Assertions.assertNotNull(skillFromDB);
        Assertions.assertEquals(skill, skillFromDB);
        Mockito.verify(skillRepository, Mockito.times(1)).update(skill);
    }

    @Test
    public void deleteSkillTest() {
        skillService.deleteSkill(1L);
        Mockito.verify(skillRepository, Mockito.times(1)).deleteById(1L);
    }
}
