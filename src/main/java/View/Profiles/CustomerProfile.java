package View.Profiles;

import View.Exceptions.InvalidCommandException;
import View.Menu;
import View.PurchasePage;

import java.util.ArrayList;

public class CustomerProfile extends Profile {
    Profile profile;

    public CustomerProfile(Profile profile, Menu parentMenu) {
        super(parentMenu);
        this.profile = profile;
        submenus.put(4, getPersonalInfoMenu());
        submenus.put(5, PurchasePage.getInstance(this));
        submenus.put(6, getOrdersMenu());
        this.setSubmenus(submenus);
        this.commands = new ArrayList<String>();
        setCommands();
    }

    private void setCommands() {
        commands.add("view personal info");
        commands.add("view cart");
        commands.add("view orders");
        commands.add("view balance");
        commands.add("view discount codes");
        commands.add("back");
        commands.add("help");
        commands.add("go to register panel");
        commands.add("products");
        commands.add("offs");
    }

    private Menu getOrdersMenu() {
        return new Menu("orders", this) {
            public void setCommands() {
                commands.add("view order (\\d+)$");
                commands.add("rate(\\d+) ([1-5])$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                for (int i = 0; i <= commandProcessor.getCustomerOrdersHistory().size(); i++)
                    System.out.println(i + ". " + commandProcessor.getCustomerOrdersHistory().get(i - 1).toString());
                showCommands();
            }

            private void showCommands() {
                System.out.println("commands\n1. view order [orderId]\n2. rate [productId] [1-5]\n3. back\n4. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.matches(commands.get(0))) {
                    String[] commandDetails = command.split("\\s");
                    commandProcessor.rate(commandDetails[1], commandDetails[2]);
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    System.out.println(commandProcessor.getOrderById(commandDetails[2]).toString());
                    return this;
                } else if (command.equals(commands.get(2))) {
                    return getGrandFatherMenu();
                } else if (command.equals(commands.get(3))) {
                    showCommands();
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }
        };
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
        if (command.equals(commands.get(0))) {
            return submenus.get(4);
        } else if (command.equals(commands.get(1))) {
            return submenus.get(5);
        } else if (command.equals(commands.get(2))) {
            return submenus.get(6);
        } else if (command.equals(commands.get(3))) {
            System.out.println(commandProcessor.getCustomerBalance());
            return this;
        } else if (command.equals(commands.get(4))) {
            for (int i = 1; i <= commandProcessor.getCustomerDiscountCodes().size(); i++)
                System.out.println(i + ". " + commandProcessor.getCustomerDiscountCodes().get(i - 1).toString());
            return this;
        } else if (command.equals(commands.get(5))) {
            return this.parentMenu;
        } else if (command.equals(commands.get(6))) {
            return this;
        } else if (command.equals(commands.get(7))) {
            return submenus.get(1);
        } else if (command.equals(commands.get(8))) {
            return submenus.get(2);
        } else if (command.equals(commands.get(9))) {
            return submenus.get(3);
        }
        throw new InvalidCommandException("invalid command");
    }
}
