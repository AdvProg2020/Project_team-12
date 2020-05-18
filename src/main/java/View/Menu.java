package View;

import Controller.CommandProcessors.CommandProcessor;
import Controller.DataBase.DataCenter;
import View.Exceptions.CustomerExceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    private String name;
    protected Menu parentMenu;
    protected HashMap<Integer, Menu> submenus;
    protected ArrayList<String> commands;
    protected Scanner scanner = InputUtility.getInstance();
    protected DataCenter dataCenter = DataCenter.getInstance();

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

    public String getField(String fieldName,String regex) throws Exception{
        System.out.println("Enter "+fieldName);
        String fieldValue = scanner.nextLine();
        if (!fieldValue.matches(regex)){
            System.err.println("wrong patten");
            System.out.println("enter <back> to return or <next> to reenter");
            String command = getField("<next> or <back>",("(next|back)"));
            if (command.equals("back"))
                throw new CustomerExceptions("exited successfully");
            fieldValue = getField(fieldName, regex);
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
            e.printStackTrace();
            show();
            run();
        }
    }
}