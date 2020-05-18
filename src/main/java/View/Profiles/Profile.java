package View.Profiles;

import Controller.CommandProcessors.CPS;
import Controller.CommandProcessors.CommandProcessor;
import Controller.CommandProcessors.ProfileCP;
import Model.Account.Customer;
import View.AuctionsPage;
import View.Exceptions.InvalidCommandException;
import View.Menu;
import View.ProductsPage;

import java.util.HashMap;

public class Profile extends Menu {
    static ProfileCP commandProcessor;

    public static void setCommandProcessor(ProfileCP cp) {
        commandProcessor = cp;

    }

    public Profile(Menu parentMenu) {
        super("Profile", parentMenu);
        submenus = new HashMap<Integer, Menu>();
        submenus.put(1, new RegisterPanel(this));
        submenus.put(2, new ProductsPage(this));
        submenus.put(3, new AuctionsPage(this));
        setCommands();
    }

    private void setCommands() {
        commands.add("products");
        commands.add("offs");
        commands.add("go to register panel");
        commands.add("back");
        commands.add("help");
    }

    @Override
    public void show() {
        System.out.println(this.getName() + "\ncommands\n");
        for (int i = 1; i <= commands.size(); i++) {
            System.out.println(i + ". " + commands.get(i - 1));
        }
    }

    @Override
    public Menu getCommand() throws Exception {
        String command = scanner.nextLine();
        if (command.equals(commands.get(0)) || command.equals("1")) {
            CommandProcessor.goToSubCommandProcessor(CPS.ProductsPageCP.getId());
            return submenus.get(2);
        } else if (command.equals(commands.get(1)) || command.equals("2")) {
            CommandProcessor.goToSubCommandProcessor(CPS.AuctionPageCP.getId());
            return submenus.get(3);
        } else if (command.equals(commands.get(2)) || command.equals("3")) {
            CommandProcessor.goToSubCommandProcessor(CPS.RegisterPanelCP.getId());
            return submenus.get(1);
        } else if (command.equals(commands.get(3)) || command.equals("4")) {
            CommandProcessor.back();
            return this.parentMenu;
        } else if (command.equals(commands.get(4)) || command.equals("5")) {
            return this;
        }
        throw new InvalidCommandException("invalid command");
    }

    public Menu getGrandFatherMenu() {
        return this.parentMenu;
    }

    protected Menu getPersonalInfoMenu() {
        return new Menu("Personal Info", this) {
            public void setCommands() {
                commands.add("edit (password|firstName|lastName|emailAddress|phoneNumber)$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                System.out.println(CommandProcessor.getInstance().getPersonalInfo());
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
                    String newFieldValue = getField(commandDetails[1], "\\w+");
                    CommandProcessor.getInstance().editPersonalInfo(commandDetails[1], newFieldValue);
                    return this;
                } else if (command.equals(commands.get(1)) || command.equals("2")) {
                    return this.parentMenu;
                } else if (command.equals(commands.get(2)) || command.equals("3")) {
                    showCommands();
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }
        };
    }
}
