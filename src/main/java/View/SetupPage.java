package View;

import Controller.CommandProcessors.CommandProcessor;
import Controller.DataBase.DataCenter;
import Model.Account.Account;
import Model.Account.Manager;
import Model.ProductsOrganization.Filter.Filter;

import java.io.File;
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
                String username = getField("username", "\\S+");
                String password = getField("password", "\\S+");
                String firstName = getField("first name", "\\w+");
                String lastName = getField("last name", "\\w+");
                String emailAddress = getField("email address", "(\\w+)@(\\w+)\\.(\\w+)$");
                String phoneNumber = getField("phone number", "(\\d+)$");
                Account manager = new Manager(username, firstName, lastName, emailAddress, phoneNumber, password);
                DataCenter.getInstance().saveAccount(manager);
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

    public static String getField(String fieldName, String regex) {
        System.out.println("Enter " + fieldName);
        Scanner scanner = InputUtility.getInstance();
        String fieldValue = scanner.nextLine();
        if (!fieldValue.matches(regex)) {
            System.err.println("wrong patten");
            getField(fieldName, regex);
        }
        return fieldValue;
    }
}
