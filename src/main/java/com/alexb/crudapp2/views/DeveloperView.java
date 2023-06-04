package com.alexb.crudapp2.views;

import com.alexb.crudapp2.controller.DeveloperController;
import com.alexb.crudapp2.entity.DeveloperEntity;
import com.alexb.crudapp2.entity.SkillEntity;
import com.alexb.crudapp2.entity.SpecialtyEntity;
import com.alexb.crudapp2.entity.Status;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DeveloperView {
    private final Scanner scanner = new Scanner(System.in);
    private final DeveloperController developerController = new DeveloperController();
    private final SpecialtyView specialtyView = new SpecialtyView();
    private final SkillView skillView = new SkillView();

    public void showToDevelopers() {
        List<DeveloperEntity> listDeveloper = developerController.getListAllDevelopers();
        if (listDeveloper.isEmpty()) {
            System.out.println("\nNone developers\n");
        } else {
            listDeveloper.forEach(a -> {
                if (a.getStatus().equals(Status.ACTIVE)) {
                    System.out.println(a + "\n");
                }
            });
        }
    }

    public void createDeveloper() {
        System.out.println("Enter first name: ");
        String fn = scanner.nextLine().trim();
        System.out.println("Enter last name: ");
        String ln = scanner.nextLine().trim();

        SpecialtyEntity specialty = setSpecialty();
        List<SkillEntity> skills = setSkills();

        System.out.println("\nDeveloper created: ");
        System.out.println(developerController.createDeveloper(fn, ln, skills, specialty));
    }

    public void editDeveloper() {
        List<DeveloperEntity> developerList = developerController.getListAllDevelopers();
        DeveloperEntity developer;
        if (developerList.isEmpty()) {
            System.out.println("List of developers is empty");
        } else {
            System.out.println("Select a developer to update from the list" +
                    "(Enter a ordinal number): \n");
            showToDevelopers();
            developer = developerController
                    .getDevelopersById(Long.parseLong(scanner.nextLine().trim()));

            System.out.println("Enter new first name: ");
            String newFn = scanner.nextLine().trim();
            System.out.println("Enter new last name: ");
            String newLn = scanner.nextLine().trim();

            SpecialtyEntity specialty = setSpecialty();
            List<SkillEntity> skills = setSkills();
            System.out.println("Developer updated: ");
            System.out.println(developerController
                    .updateDeveloper(developer.getId(), newFn, newLn, skills, specialty));
        }
    }

    public void deleteDeveloper() {
        if (developerController.getListAllDevelopers().isEmpty()) {
            System.out.println("List of developers is empty");
        } else {
            System.out.println("Select a developer to delete from the list" +
                    "(Enter a ordinal number): \n");
            showToDevelopers();
            developerController.deleteDeveloperById(Long.parseLong(scanner.nextLine().trim()));
            System.out.println("Developer deleted");
        }
    }


    public List<SkillEntity> setSkills() {
        String[] arrayId;
        List<SkillEntity> skillList = skillView.outAllSkills();
        if (skillList.isEmpty()) {
            skillList = null;
        } else {
            System.out.println("Select skills for developer" +
                    "(enter ordinal numbers separated by commas):");
            skillList
                    .forEach(s -> System.out.println(s.getId() + "." + s.getName()));
            arrayId = scanner.nextLine().split(",");
            List<Long> listIdSkills = Arrays.stream(arrayId)
                    .toList()
                    .stream()
                    .map(Long::parseLong)
                    .toList();
            skillList = skillList
                    .stream()
                    .filter(a -> listIdSkills.contains(a.getId()))
                    .collect(Collectors.toList());
        }
        return skillList;
    }

    public SpecialtyEntity setSpecialty() {
        long id;
        SpecialtyEntity specialty;
        List<SpecialtyEntity> specialtyList = specialtyView.outAllSpecialties();
        if (specialtyList.isEmpty()) {
            specialty = null;
        } else {
            System.out.println("Select a developer specialty from the list(Enter ordinal number):");
            specialtyList
                    .forEach(s -> System.out.println(s.getId() + "." + s.getName()));
            id = Long.parseLong(scanner.nextLine().trim());
            specialty = specialtyList.stream()
                    .filter(a -> a.getId() == id)
                    .findFirst()
                    .orElse(null);
        }
        return specialty;
    }

}
