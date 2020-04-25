package View;

import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    private String name;
    private Menu parentMenu;
    private HashMap<Integer, Menu> submenus;
    protected Scanner scanner = InputUtility.getInstance();

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
    }

    public String getName() {
        return name;
    }

    public void show() {
        for (Integer index : submenus.keySet()) {
            System.out.println(index + ". " + submenus.get(index).getName());
        }
        if (this.name.equals("Main Menu"))
            System.out.println(submenus.size() + 1 + ". " + "exit");
        else if (this.name.equals("Register Panel"))
            System.out.println(submenus.size() + 1 + ". " + "logout");
        else
            System.out.println(submenus.size() + 1 + ". " + "back");
    }

    public void setSubmenus(HashMap<Integer, Menu> submenus) {
        this.submenus = submenus;
    }

    public abstract Menu getCommand() throws Exception;

    public String getField(String fieldName) {
        System.out.println("Enter "+fieldName);
        return scanner.nextLine();
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

class InvalidCommandException extends Exception {
    public InvalidCommandException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
