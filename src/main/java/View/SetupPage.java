package View;

import Controller.CommandProcessors.CommandProcessor;

import java.util.Scanner;

public class SetupPage {
    public static void main(String[] args) {
        System.out.println("welcome");
        System.out.println("press enter to continue");
        Scanner scanner = InputUtility.getInstance();
        scanner.nextLine();
        if (!CommandProcessor.managerExists()) {
            System.out.println("no administrator is set yet\npress enter to setup");
            SetupPage.run();
        }
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
        mainMenu.run();
    }

    public static void run() {
        try {
            System.out.println("what do you want to do?\n1. setup\n2. exit");
            Scanner scanner = InputUtility.getInstance();
            String command = scanner.nextLine();
            if (command.equals("1") | command.equals("setup")) {
                //TODO:Register first manager, it need a method which has not been created yet

            } else if (command.equals("2") | command.equals("exit")) {
                System.exit(0);
            } else {
                throw new Exception("invalid command");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            run();
        }
    }
}
