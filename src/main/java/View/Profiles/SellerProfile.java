package View.Profiles;

import View.Exceptions.InvalidCommandException;
import View.Menu;

public class SellerProfile extends Profile {
    Profile profile;

    public SellerProfile(Profile profile, Menu parentMenu) {
        super(parentMenu);
        this.profile = profile;
        submenus.put(2, getPersonalInfoMenu());
        submenus.put(3, getCompanyHistoryMenu());
        submenus.put(4, getSalesHistoryMenu());
        submenus.put(5, getProductsMenu());
        submenus.put(6, getAddProductMenu());
        submenus.put(7, getCategoriesMenu());
        submenus.put(8, getOffsMenu());
        setCommands();
    }

    private void setCommands() {
        commands.add("view personal info");
        commands.add("view company information");
        commands.add("view sales history");
        commands.add("manage products");
        commands.add("add product");
        commands.add("remove product (\\d+)$");
        commands.add("show categories");
        commands.add("view offs");
        commands.add("view balance");
        commands.add("back");
        commands.add("help");
        commands.add("go to register panel");
    }

    private Menu getCompanyHistoryMenu() {
        return new Menu("Company History", this) {
            @Override
            public void show() {
                System.out.println(this.getName());
                System.out.println("commands\n1. back\n\n");
                //get company info and show
            }

            @Override
            public Menu getCommand() throws Exception {
                String command = scanner.nextLine();
                if (command.equals("back"))
                    return this.parentMenu;
                throw new InvalidCommandException("invalid command");
            }
        };
    }

    private Menu getSalesHistoryMenu() {
        return new Menu("Sales History", this) {
            @Override
            public void show() {
                System.out.println(this.getName());
                System.out.println("commands\n1. back\n\n");
                //get sales info and show
            }

            @Override
            public Menu getCommand() throws Exception {
                String command = scanner.nextLine();
                if (command.equals("back"))
                    return this.parentMenu;
                throw new InvalidCommandException("invalid command");
            }
        };
    }

    private Menu getProductsMenu() {
        return new Menu("Products", this) {
            public void setCommands() {
                commands.add("view (\\d+)$");
                commands.add("view buyers (\\d+)$");
                commands.add("edit (\\d+)$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                //get products info and show
                showCommands();
            }

            private void showCommands() {
                System.out.println("commands\n1. view [productId]\n2. view buyers [productId]\n3. edit [productId]\n4. back\n5. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.matches(commands.get(0))) {
                    String[] commandDetails = command.split("\\s");
                    //calling view product method by commandDetails[1]
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    //calling view buyers method by commandDetails[1] and new fields
                    return this;
                } else if (command.matches(commands.get(2))) {
                    String[] commandDetails = command.split("\\s");
                    String name = getField("name", "(\\w+)$");
                    String brand = getField("brand", "(\\w+)$");
                    String price = getField("price", "(\\d+)\\.(\\d+)$");
                    String category = getField("category", "(\\w+)$");
                    String remainingItems = getField("remaining items", "(\\d+)$");
                    String description = getField("description", "\\S+");
                    return this;
                } else if (command.equals(commands.get(3))) {
                    return getGrandFatherMenu();
                } else if (command.equals(commands.get(4))) {
                    showCommands();
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }
        };
    }

    private Menu getAddProductMenu() {
        return new Menu("add product", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + "\n");
            }

            @Override
            public Menu getCommand() throws Exception {
                String id = getField("id", "(\\d+)$");
                String name = getField("name", "(\\w+)$");
                String brand = getField("brand", "(\\w+)$");
                String price = getField("price", "(\\d+)\\.(\\d+)$");
                String category = getField("category", "(\\w+)$");
                String remainingItems = getField("remaining items", "(\\d+)$");
                String description = getField("description", "\\S+");
                //call add product method
                return this.parentMenu;
            }
        };
    }

    private Menu getCategoriesMenu() {
        return new Menu("categories", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + "\ncommands\n1. back");
                //get categories and show
            }

            @Override
            public Menu getCommand() throws Exception {
                String command = scanner.nextLine();
                if (command.equals("back"))
                    return this.parentMenu;
                throw new InvalidCommandException("invalid command");
            }
        };
    }

    private Menu getOffsMenu() {
        return new Menu("offs", this) {
            public void setCommands() {
                commands.add("view (\\d+)$");
                commands.add("edit (\\d+)$");
                commands.add("add off");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                //show all auctions
                showCommands();
            }

            private void showCommands() {
                System.out.println("commands\n1. view [offId]\n2. edit [offId]\n3. add off\n4. back\n5. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.matches(commands.get(0))) {
                    String[] commandDetails = command.split("\\s");
                    //calling view off method by commandDetails[1]
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    String lastDate = getField("last date", "(\\d\\d)/(\\d\\d)/(\\d\\d)$");
                    String percent = getField("percent", "(\\d+)$");
                    String id = getField("auction id", "(\\d+)$");
                    String listOfUsers = getField("products' id and separate them by comma", "(\\w+,)+");
                    //request to manager
                    return this;
                } else if (command.matches(commands.get(2))) {
                    String[] commandDetails = command.split("\\s");
                    String lastDate = getField("last date", "(\\d\\d)/(\\d\\d)/(\\d\\d)$");
                    String percent = getField("percent", "(\\d+)$");
                    String id = getField("auction id", "(\\d+)$");
                    String listOfProducts = getField("products' id and separate them by comma", "(\\w+,)+");
                    //request to manager
                    return this;
                } else if (command.equals(commands.get(3))) {
                    return getGrandFatherMenu();
                } else if (command.equals(commands.get(4))) {
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
            if (i == 6)
                System.out.println("6. remove product [productId]");
            else
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
            return submenus.get(5);
        } else if (command.equals(commands.get(4))) {
            return submenus.get(6);
        } else if (command.matches(commands.get(5))) {
            String[] commandDetails = command.split("\\s");
            //remove product with id commandDetails[2]
            return this;
        } else if (command.equals(commands.get(6))) {
            return submenus.get(7);
        } else if (command.equals(commands.get(7))) {
            return submenus.get(8);
        } else if (command.equals(commands.get(8))) {
            //show balance
            return this;
        } else if (command.equals(commands.get(9))) {
            return this.parentMenu;
        } else if (command.equals(commands.get(10))) {
            return this;
        } else if (command.equals(commands.get(11))) {
            return submenus.get(1);
        }
        throw new InvalidCommandException("invalid command");
    }
}
