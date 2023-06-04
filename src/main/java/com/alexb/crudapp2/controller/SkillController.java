package com.alexb.crudapp2.controller;

import com.alexb.crudapp2.entity.SkillEntity;
import com.alexb.crudapp2.service.SkillService;

import java.util.List;

public class SkillController {
    private final SkillService skillService = new SkillService();

    public List<SkillEntity> getListAllSkills() {
        return skillService.getAllSkills();
    }

    public SkillEntity createSkill(String name) {
        SkillEntity skill = new SkillEntity();
        skill.setName(name);
        return skillService.creationSkill(skill);
    }

    public SkillEntity updateSkill(long id, String name) {
        SkillEntity skill = new SkillEntity();
        skill.setId(id);
        skill.setName(name);
        return skillService.editSkill(skill);
    }

    public void deleteSkillById(Long id) {
        skillService.deleteSkill(id);
    }

}

