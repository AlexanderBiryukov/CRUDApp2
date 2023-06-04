package com.alexb.crudapp2.views;

import com.alexb.crudapp2.controller.SkillController;
import com.alexb.crudapp2.entity.SkillEntity;

import java.util.List;
import java.util.Scanner;


public class SkillView {
    private final SkillController skillController = new SkillController();
    private final Scanner scanner = new Scanner(System.in);

    //
    public void showToSkills() {
        List<SkillEntity> skillList = skillController.getListAllSkills();
        if (skillList.isEmpty()) {
            System.out.println("\nNone skills\n");
        } else {
            skillController.getListAllSkills().forEach(System.out::println);
        }
    }

    //
    public List<SkillEntity> outAllSkills() {
        return skillController.getListAllSkills();
    }

    //
    public void createSkill() {
        System.out.println("Enter the name of the skill: ");
        System.out.println(skillController.createSkill(scanner.nextLine().trim()));
        System.out.println("Skill created!");
    }
//
    public void editSkill() {
        String newName;
        long id;
        List<SkillEntity> skillList = skillController.getListAllSkills();
        if (skillList.isEmpty()) {
            System.out.println("List of skills is empty");
        } else {
            System.out.println("Select a skill to update from the list" +
                    "(Enter a ordinal number): \n");
            showToSkills();
            id = Long.parseLong(scanner.nextLine().trim());
            System.out.println("Enter new name skill: ");
            newName = scanner.nextLine().trim();
            skillController.updateSkill(id, newName);
            System.out.println("Skill updated!");
        }
    }
//
    public void deleteSkill() {
        long id;
        List<SkillEntity> skillList = skillController.getListAllSkills();
        if (skillList.isEmpty()) {
            System.out.println("List of skills is empty");
        } else {
            showToSkills();
            System.out.println("""

                    Select a skill to delete from the list(Enter a ordinal number):\s
                    """);
            id = Long.parseLong(scanner.nextLine().trim());
            skillController.deleteSkillById(id);
            System.out.println("Skill deleted!");
        }
    }
}
