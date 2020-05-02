package View.Profiles;

import View.Exceptions.InvalidCommandException;
import View.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class Profile extends Menu {
    public Profile(Menu parentMenu) {
        super("Profile", parentMenu);
        submenus = new HashMap<Integer, Menu>();
        submenus.put(1, new RegisterPanel(this));
    }

    @Override
    public void show() {

    }

    @Override
    public Menu getCommand() throws Exception {
        return null;
    }

    public void run() {
    }

    private Matcher getMatcher(String regex) {
        return null;
    }

    public Menu getGrandFatherMenu() {
        return this.parentMenu;
    }

    protected  Menu getPersonalInfoMenu() {
        return new Menu("Personal Info", this) {
            public void setCommands() {
                commands.add("edit (password|first name|last name|email address|phone number)$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                //get account info and show
                showCommands();
            }

            public void showCommands() {
                System.out.println("commands\n1. edit [field]\n2. back\n3. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.matches(commands.get(0))) {
                    String[] commandDetails = command.split("\\s");
                    //calling edit method by commandDetails[1]
                    return this;
                } else if (command.equals(commands.get(1))) {
                    return getGrandFatherMenu();
                } else if (command.equals(commands.get(2))) {
                    showCommands();
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }
        };
    }
}
