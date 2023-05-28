package com.alexb.crudapp2.views;


import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner = new Scanner(System.in);
    private final SkillView skillView = new SkillView();
    private final SpecialtyView specialtyView = new SpecialtyView();

    private final DeveloperView developerView = new DeveloperView();


    public void start() {
        System.out.println("\nWelcome!");

        while (true) {
            mainMenu();
            String enterStartCommand = scanner.nextLine().trim();

            if (enterStartCommand.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            } else {
                switch (enterStartCommand) {
                    case "getAllDev" -> developerView.showToDevelopers();
                    case "addDev" -> developerView.createDeveloper();
                    case "updateDev" -> developerView.editDeveloper();
                    case "deleteDev" -> developerView.deleteDeveloper();
                    case "getAllSpec" -> specialtyView.showToSpecialties();
                    case "addSpec" -> specialtyView.createSpecialty();
                    case "updateSpec" -> specialtyView.editSpecialty();
                    case "deleteSpec" -> specialtyView.deleteSpecialty();
                    case "getAllSkill" -> skillView.showToSkills();
                    case "addSkill" -> skillView.createSkill();
                    case "updateSkill" -> skillView.editSkill();
                    case "deleteSkill" -> skillView.deleteSkill();
                    default -> System.out.println("Incorrect input");
                }
            }
        }

    }

    private void mainMenu() {
        System.out.println("""
                --------------------------------------------
                Main menu                                   |
                --------------------------------------------
                Supported requests:                         |
                                                            |\s
                - get list of all developers: "getAllDev"   |\s
                - add new developer: "addDev"               |\s
                - update developer : "updateDev"            |\s
                - delete developer: "deleteDev"             |\s
                                                            |\s
                - get list of all specialties: "getAllSpec" |\s
                - add new specialty: "addSpec"              |\s
                - update specialty : "updateSpec"           |\s
                - delete specialty: "deleteSpec"            |\s
                                                            |\s
                - get list of all skills: "getAllSkill"     |\s
                - add new skill: "addSkill"                 |\s
                - update skill : "updateSkill"              |\s
                - delete skill: "deleteSkill"               |\s
                                                            |\s
                - to exit the application: "exit"           |\s
                --------------------------------------------
                """);
    }
}
