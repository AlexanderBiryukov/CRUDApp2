package com.alexb.crudapp2.service;

import com.alexb.crudapp2.entity.SkillEntity;
import com.alexb.crudapp2.repository.SkillRepository;
import com.alexb.crudapp2.repository.jdbc.JdbcSkillRepositoryImpl;

import java.util.List;

public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService() {
        this.skillRepository = new JdbcSkillRepositoryImpl();
    }

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<SkillEntity> getAllSkills() {
        return skillRepository.getAll();
    }

    public SkillEntity creationSkill(SkillEntity skill) {
        return skillRepository.save(skill);
    }

    public SkillEntity editSkill(SkillEntity skill) {
        return skillRepository.update(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }

}
