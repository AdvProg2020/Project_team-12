package View.Profiles;

import View.Exceptions.InvalidCommandException;
import View.Menu;

import java.util.ArrayList;
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
        commands = new ArrayList<String>();
        setCommands();
    }

    public void setCommands() {
        commands.add("create account (manager|seller|customer) (\\S+)$");
        commands.add("login (\\S+)$");
        commands.add("logout");
        commands.add("help");
    }

    public void show(){
        System.out.println("1. create account [type] [username]");
        System.out.println("2. login [username]");
        System.out.println("3. logout");
        System.out.println("4. help");
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
                String password = getField("password");
                String firstName = getField("first name");
                String lastName = getField("last name");
                String emailAddress = getEmailAddress();
                String phoneNumber = getPhoneNumber();
                //calling register method in controller with these inputs : role username password ...
                return getGrandFatherMenu();
            }

            private String getEmailAddress() {
                String emailAddress = getField("email address");
                if (!emailAddress.matches("(\\S+)@(\\w+)\\.(\\w+)$")) {
                    System.err.println("invalid email pattern");
                    emailAddress = getEmailAddress();
                }
                return emailAddress;
            }

            private String getPhoneNumber() {
                String phoneNumber = getField("phone number");
                if (phoneNumber.length() != 13 || !phoneNumber.matches("(\\d+)$")) {
                    System.err.println("invalid phone number pattern");
                    phoneNumber = getPhoneNumber();
                }
                return phoneNumber;
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
                String password = getField("password");
                //calling login method in controller with these inputs : role username password ...
                return getGrandFatherMenu();
            }

            @Override
            public void run() {
                try {
                    this.getCommand();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    this.show();
                    this.run();
                }
            }
        };
    }

    private Matcher getMatcher(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    @Override
    public Menu getCommand() throws Exception {
        String command = scanner.nextLine();
        if (command.matches(this.commands.get(0))) {
            String[] commandDetails = command.split("\\s");
            //we have to try catch checkUsername method in controller : commandDetails[4]
            return submenus.get(1);
        } else if (command.matches(this.commands.get(1))) {
            String[] commandDetails = command.split("\\s");
            //we have to try catch checkUsername method in controller : commandDetails[2]
            return submenus.get(2);
        }
        else if (command.equals(this.commands.get(2))) {
           return this.parentMenu;
        }
        else if (command.equals(this.commands.get(3))) {
            return this;
        }
        throw new InvalidCommandException("invalid command");
    }
}
