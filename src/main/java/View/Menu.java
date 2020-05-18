package View;

import Controller.DataBase.DataCenter;
import View.Exceptions.CustomerExceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    protected Menu parentMenu;
    protected HashMap<Integer, Menu> submenus;
    protected ArrayList<String> commands;
    protected Scanner scanner = InputUtility.getInstance();
    protected DataCenter dataCenter = DataCenter.getInstance();
    private String name;

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
        commands = new ArrayList<>();
    }

    protected String getName() {
        return name;
    }

    public abstract void show();

    public void setSubmenus(HashMap<Integer, Menu> submenus) {
        this.submenus = submenus;
    }

    public abstract Menu getCommand() throws Exception;

    public String getField(String fieldName, String regex) throws Exception {
        System.out.println("Enter " + fieldName);
        String fieldValue = scanner.nextLine();
        if (!fieldValue.matches(regex)) {
            System.err.println("wrong patten");
            System.out.println("enter <back> to return or <next> to reenter");
            String command = getAdditionalCommand();
            /*String command = getField("<next> or <back>", ("(next|back)"));
            //String command = scanner.nextLine();*/
            if (command.equals("back"))
                throw new CustomerExceptions("exited successfully");
            /*else if (!command.equals("next"))
                System.err.println("invalid command");*/
            fieldValue = getField(fieldName, regex);
        }
        return fieldValue;
    }

    public String getAdditionalCommand() {
        System.out.println("Enter <next> or <back>");
        String fieldValue = scanner.nextLine();
        if (fieldValue.equals("next"))
            return fieldValue;
        else if (fieldValue.equals("back"))
            return fieldValue;
        else {
            System.err.println("wrong patten");
            fieldValue = getAdditionalCommand();
        }
        return fieldValue;
    }

    public void run() {
        try {
            Menu nextMenu = getCommand();
            if (nextMenu == null) {
                System.out.println("thank you for using our market");
                System.exit(1);
            } else {
                nextMenu.show();
                nextMenu.run();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            show();
            run();
        }
    }
}