package com.alexb.crudapp2.views;

import com.alexb.crudapp2.controller.SpecialtyController;
import com.alexb.crudapp2.entity.SpecialtyEntity;

import java.util.List;
import java.util.Scanner;

public class SpecialtyView {
    private final Scanner scanner = new Scanner(System.in);
    private final SpecialtyController specialtyController = new SpecialtyController();


    public List<SpecialtyEntity> outAllSpecialties() {
        return specialtyController.getListAllSpecialties();
    }

    public void showToSpecialties() {
        List<SpecialtyEntity> specialtyList = specialtyController.getListAllSpecialties();
        if (specialtyList.isEmpty()) {
            System.out.println("\nNone specialties\n");
        } else {
            specialtyList.forEach(System.out::println);
        }
    }
//
    public void createSpecialty() {
        System.out.println("Enter the name of the specialty: ");
        specialtyController.createSpecialty(scanner.nextLine().trim());
        System.out.println("Specialty created");
    }

    public void editSpecialty() {
        String newName;
        long id;
        List<SpecialtyEntity> specialtyList = specialtyController.getListAllSpecialties();
        if (specialtyList.isEmpty()) {
            System.out.println("List of specialties is empty");
        } else {
            System.out.println("Select a specialty to update from the list" +
                    "(Enter a ordinal number): \n");
            showToSpecialties();
            id = Long.parseLong(scanner.nextLine().trim());
            System.out.println("Enter new name specialty: ");
            newName = scanner.nextLine().trim();
            specialtyController.updateSpecialty(id, newName);
            System.out.println("Specialty updated");
        }
    }

    public void deleteSpecialty() {
        long id;
        List<SpecialtyEntity> specialtyList = specialtyController.getListAllSpecialties();
        if (specialtyList.isEmpty()) {
            System.out.println("List of specialties is empty");
        } else {
            showToSpecialties();
            System.out.println("""

                    Select a specialty to delete from the list(Enter a ordinal number):\s
                    """);
            id = Long.parseLong(scanner.nextLine().trim());
            specialtyController.deleteSpecialtyById(id);
            System.out.println("Specialty deleted");
        }
    }
}
