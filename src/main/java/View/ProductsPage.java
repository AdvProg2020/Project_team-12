package View;

import View.Exceptions.InvalidCommandException;
import View.Profiles.RegisterPanel;

import java.util.HashMap;

public class ProductsPage extends Menu {
    public ProductsPage(Menu parentMenu) {
        super("Product Page", parentMenu);
        submenus = new HashMap<Integer, Menu>();
        //view categories
        submenus.put(1, getFilteringMenu());
        submenus.put(2, getSortingMenu());
        submenus.put(3, new RegisterPanel(this));
        setCommands();
    }

    protected Menu getGrandFatherMenu() {
        return this.parentMenu;
    }

    private void setCommands() {
        commands.add("view categories");
        commands.add("filtering");
        commands.add("sorting");
        commands.add("show products");
        commands.add("show product (\\d+)$");
        commands.add("back");
        commands.add("help");
        commands.add("go to register panel");
    }

    protected Menu getFilteringMenu() {
        return new Menu("filtering", this) {
            public void setCommands() {
                commands.add("show available filters");
                commands.add("filter (\\w+)$");
                commands.add("current filters");
                commands.add("disable filter (\\w+)$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                showCommands();
            }

            public void showCommands() {
                System.out.println("commands\n1. show available filters\n2. filter [an available filter]\n3. current filters\n4. disable filter [ a selected filter] \n4. back\n5. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.equals(commands.get(0))) {
                    //show all filters
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    //calling set filter method using commandDetails[2]
                    return this;
                } else if (command.equals(commands.get(2))) {
                    //show current filters
                    return this;
                } else if (command.matches(commands.get(3))) {
                    String[] commandDetails = command.split("\\s");
                    //calling disable filter method using commandDetails[2]
                    return this;
                } else if (command.equals(commands.get(4))) {
                    return getGrandFatherMenu();
                } else if (command.equals(commands.get(5))) {
                    showCommands();
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }
        };
    }

    protected Menu getSortingMenu() {
        return new Menu("sorting", this) {
            public void setCommands() {
                commands.add("show available sorts");
                commands.add("sort (\\w+)$");
                commands.add("current sort");
                commands.add("disable sort");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                showCommands();
            }

            public void showCommands() {
                System.out.println("commands\n1. show available sorts\n2. sort [an available sort]\n3. current sort\n4. disable sort\n4. back\n5. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.equals(commands.get(0))) {
                    //show all sorts
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    //calling set sort method using commandDetails[2]
                    return this;
                } else if (command.equals(commands.get(2))) {
                    //show current sort
                    return this;
                } else if (command.equals(commands.get(3))) {
                    //calling disable sort method
                    return this;
                } else if (command.equals(commands.get(4))) {
                    return getGrandFatherMenu();
                } else if (command.equals(commands.get(5))) {
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
            if (i == 5)
                System.out.println("5. show product [productId]");
            else
                System.out.println(i + ". " + commands.get(i - 1));
        }
    }

    @Override
    public Menu getCommand() throws Exception {
        String command = scanner.nextLine();
        if (command.equals(commands.get(0))) {
            //show categories
            return this;
        } else if (command.equals(commands.get(1))) {
            return submenus.get(1);
        } else if (command.equals(commands.get(2))) {
            return submenus.get(2);
        } else if (command.equals(commands.get(3))) {
            //show products using current filters and sort
            return this;
        } else if (command.matches(commands.get(4))) {
            String[] commandDetails = command.split("\\s");
            //check if product with this id exists
            return new ProductPage(this,commandDetails[2]);
        } else if (command.equals(commands.get(5))) {
            return this.parentMenu;
        } else if (command.equals(commands.get(6))) {
            return this;
        } else if (command.equals(commands.get(7))) {
            return submenus.get(3);
        }
        throw new InvalidCommandException("invalid command");
    }
}
