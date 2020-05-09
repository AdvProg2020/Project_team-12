package View;

import Controller.DataBase.DataCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    private String name;
    protected Menu parentMenu;
    protected HashMap<Integer, Menu> submenus;
    protected ArrayList<String> commands;
    protected Scanner scanner = InputUtility.getInstance();
    protected DataCenter dataCenter = new DataCenter();

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
        commands = new ArrayList<String>();
    }

    protected String getName() {
        return name;
    }

    public abstract void show();

    public void setSubmenus(HashMap<Integer, Menu> submenus) {
        this.submenus = submenus;
    }

    public abstract Menu getCommand() throws Exception;

    public String getField(String fieldName,String regex) {
        System.out.println("Enter "+fieldName);
        String fieldValue = scanner.nextLine();
        if (!fieldName.matches(regex)){
            System.err.println("wrong patten");
            getField(fieldName, regex);
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