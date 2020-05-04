package View.Profiles;

import View.Exceptions.InvalidCommandException;
import View.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPanel extends Menu {
    private String username = null;
    private String profileType = null;

    public RegisterPanel(Menu parentMenu) {
        super("Register Panel", parentMenu);
        submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getRegisterMenu());
        submenus.put(2, getLoginMenu());
        this.setSubmenus(submenus);
        setCommands();
    }

    public void setCommands() {
        commands.add("create account (manager|seller|customer) (\\S+)$");
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

    public String getProfileType() {
        return profileType;
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
                String phoneNumber = getField("phone number","(\\d+)$");
                //calling register method in controller with these inputs : role username password ...
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
                //calling login method in controller with these inputs : role username password ...
                return getGrandFatherMenu();
            }
        };
    }

    private Matcher getMatcher(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    @Override
    public Menu getCommand() throws Exception {
        System.out.println("what do you want to do?\n");
        String command = scanner.nextLine();
        if (command.matches(this.commands.get(0))) {
            String[] commandDetails = command.split("\\s");
            //we have to try catch checkUsername method in controller : commandDetails[4]
            return submenus.get(1);
        } else if (command.matches(this.commands.get(1))) {
            String[] commandDetails = command.split("\\s");
            //we have to try catch checkUsername method in controller : commandDetails[2]
            return submenus.get(2);
        } else if (command.equals(this.commands.get(2))) {
            return this.parentMenu;
        } else if (command.equals(this.commands.get(3))) {
            return this.parentMenu;
        }else if (command.equals(this.commands.get(4))) {
            return this;
        }
        throw new InvalidCommandException("invalid command");
    }
}
