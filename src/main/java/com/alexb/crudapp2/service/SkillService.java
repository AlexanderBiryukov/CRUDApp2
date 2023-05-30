package com.alexb.crudapp2.service;

import com.alexb.crudapp2.model.Skill;
import com.alexb.crudapp2.repository.SkillRepository;
import com.alexb.crudapp2.repository.jdbc.JdbcSkillRepositoryImpl;

import java.util.List;

public class SkillService {
    SkillRepository skillRepository;

    public SkillService() {
        this.skillRepository = new JdbcSkillRepositoryImpl();
    }

    public List<Skill> getAllSkills() {
        return skillRepository.getAll();
    }

    public Skill creationSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill editSkill(Skill skill) {
        return skillRepository.update(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }

}
