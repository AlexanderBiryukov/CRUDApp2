package com.alexb.crudapp2.controller;

import com.alexb.crudapp2.model.Skill;
import com.alexb.crudapp2.service.SkillService;

import java.util.List;

public class SkillController {
    private final SkillService skillService = new SkillService();

    public List<Skill> getListAllSkills() {
        return skillService.getAllSkills();
    }

    public Skill createSkill(String name) {
        Skill skill = new Skill();
        skill.setName(name);
        return skillService.creationSkill(skill);
    }

    public Skill updateSkill(long id, String name) {
        Skill skill = new Skill();
        skill.setId(id);
        skill.setName(name);
        return skillService.editSkill(skill);
    }

    public void deleteSkillById(Long id) {
        skillService.deleteSkill(id);
    }

}

