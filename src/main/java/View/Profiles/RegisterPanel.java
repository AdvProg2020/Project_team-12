package View.Profiles;

import Controller.CommandProcessors.TestCommandProcessor;
import View.Exceptions.InvalidCommandException;
import View.Exceptions.RegisterPanelException;
import View.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPanel extends Menu {
    private String username = null;
    private String AccountType = null;
    private TestCommandProcessor testCommandProcessor;
    public RegisterPanel(Menu parentMenu) {
        super("Register Panel", parentMenu);
        this.testCommandProcessor = new TestCommandProcessor();
        submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getRegisterMenu());
        submenus.put(2, getLoginMenu());
        this.setSubmenus(submenus);
        setCommands();
    }

    public void setCommands() {
        //I have deleted the manager option in create account bcz only a manager can create manager account

        commands.add("create account (seller|customer) (\\S+)$");
        commands.add("login (\\S+)$");
        commands.add("logout");
        commands.add("back");
        commands.add("help");
    }

    public void show() {
        for (int i = 1; i <= commands.size(); i++) {
            System.out.println(i + ". " + commands.get(i - 1));
        }
        System.out.println("commands\n1. create account [type] [username]\n2. login [username]\n3. logout\n4. back\n5. help");
    }

    public String getUsername() {
        return username;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public void setUsername(String username) throws Exception {
        this.username = username;
    }

    public Menu getGrandFatherMenu() {
        return this.parentMenu;
    }

    private Menu getRegisterMenu() {
        return new Menu("Register Page", this) {
            @Override
            public void show() {
                System.out.println(this.getName());
            }

            @Override
            public Menu getCommand() throws Exception {
                String password = getField("password", "\\S+");
                String firstName = getField("first name", "\\w+");
                String lastName = getField("last name", "\\w+");
                String emailAddress = getField("email address", "(\\w+)@(\\w+)\\.(\\w+)$");
                String phoneNumber = getField("phone number", "(\\d+)$");
                String username = getUsername();
                String role = getAccountType();
                testCommandProcessor.createAccount(username, role, password, firstName, lastName, phoneNumber, emailAddress);
                return getGrandFatherMenu();
            }
        };
    }

    private Menu getLoginMenu() {
        return new Menu("Login Page", this) {
            @Override
            public void show() {
                System.out.println(this.getName());
            }

            @Override
            public Menu getCommand() throws Exception {
                String password = getField("password", "\\S+");
                String username = getUsername();
                testCommandProcessor.login(username, password);
                return getGrandFatherMenu();
            }
        };
    }

    @Override
    public Menu getCommand() throws Exception {
        System.out.println("what do you want to do?\n");
        String command = scanner.nextLine();
        if (command.matches(this.commands.get(0))) {
            String[] commandDetails = command.split("\\s");
            if (testCommandProcessor.doesUsernameExists(commandDetails[3]))
                throw new RegisterPanelException("this username is in use");
            setUsername(commandDetails[3]);
            setAccountType(commandDetails[2]);

            return submenus.get(1);
        } else if (command.matches(this.commands.get(1))) {
            String[] commandDetails = command.split("\\s");
            if (!testCommandProcessor.doesUsernameExists(commandDetails[3]))
                throw new RegisterPanelException("username doesn't exist");
            setUsername(commandDetails[3]);
            return submenus.get(2);
        } else if (command.equals(this.commands.get(2))) {
            return this.parentMenu;
        } else if (command.equals(this.commands.get(3))) {
            return this.parentMenu;
        } else if (command.equals(this.commands.get(4))) {
            return this;
        }
        throw new InvalidCommandException("invalid command");
    }
}
