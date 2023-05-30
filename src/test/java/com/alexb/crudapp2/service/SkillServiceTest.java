package com.alexb.crudapp2.service;

import com.alexb.crudapp2.model.Skill;
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
        Skill skill = new Skill("Java");
        Mockito.when(skillRepository.save(skill)).thenReturn(skill);

        Skill skillFromDB = skillService.creationSkill(skill);
        Assertions.assertNotNull(skillFromDB);
        Assertions.assertEquals(skill, skillFromDB);
        Mockito.verify(skillRepository, Mockito.times(1)).save(skill);
    }

    @Test
    public void readSkillTest() {
        List<Skill> skillList = new ArrayList<>();
        skillList.add(new Skill("Java"));
        skillList.add(new Skill("Spring"));

        Mockito.when(skillRepository.getAll()).thenReturn(skillList);

        List<Skill> skillsFromDB = skillService.getAllSkills();

        Assertions.assertEquals(skillList, skillsFromDB);
        Mockito.verify(skillRepository, Mockito.times(1)).getAll();
    }

    @Test
    public void updateSkillTest() {
        Skill skill = new Skill("Java");

        Mockito.when(skillRepository.update(skill)).thenReturn(skill);

        Skill skillFromDB = skillService.editSkill(skill);

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
