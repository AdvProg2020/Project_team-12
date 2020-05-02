package View.Profiles;

import View.Exceptions.InvalidCommandException;
import View.Menu;
import View.PurchasePage;

public class CustomerProfile extends Profile {
    Profile profile;

    public CustomerProfile(Profile profile, Menu parentMenu) {
        super(parentMenu);
        this.profile = profile;
        submenus.put(2, getPersonalInfoMenu());
        submenus.put(3, PurchasePage.getInstance(this));
        submenus.put(4, getOrdersMenu());
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
                //get orders info and show
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
                    //calling rate method by commandDetails[1] and [2]
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    //calling view order method by commandDetails[2]
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
        for (int i = 1; i <= commands.size(); i++) {
            System.out.println(i + ". " + commands.get(i - 1));
        }
    }

    @Override
    public Menu getCommand() throws Exception {
        String command = scanner.nextLine();
        if (command.equals(commands.get(0))) {
            return submenus.get(2);
        } else if (command.equals(commands.get(1))) {
            return submenus.get(3);
        } else if (command.equals(commands.get(2))) {
            return submenus.get(4);
        } else if (command.equals(commands.get(3))) {
            //show balance
            return this;
        } else if (command.equals(commands.get(4))) {
            //show discount codes
            return this;
        } else if (command.equals(commands.get(5))) {
            return this.parentMenu;
        } else if (command.equals(commands.get(6))) {
            return this;
        } else if (command.equals(commands.get(7))) {
            return submenus.get(1);
        }
        throw new InvalidCommandException("invalid command");
    }
}
