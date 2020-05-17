package View.Profiles;

import Controller.CommandProcessors.CommandProcessor;
import Controller.CommandProcessors.ProfileCP;
import Controller.CommandProcessors.PurchasePageCP;
import View.Exceptions.InvalidCommandException;
import View.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerProfile extends Profile {
    Profile profile;


    public SellerProfile(Profile profile, Menu parentMenu) {
        super(parentMenu);
        this.profile = profile;
        submenus.put(4, getPersonalInfoMenu());
        submenus.put(5, getCompanyHistoryMenu());
        submenus.put(6, getSalesHistoryMenu());
        submenus.put(7, getProductsMenu());
        submenus.put(8, getAddProductMenu());
        submenus.put(9, getCategoriesMenu());
        submenus.put(10, getOffsMenu());
        this.setSubmenus(submenus);
        this.commands = new ArrayList<String>();
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
        commands.add("products");
        commands.add("offs");
    }

    public HashMap<String, String> getSpecifications(HashMap<String, String> specifications) {
        System.out.println("add specifications to your product (at least one)");
        String specificationTitle = getField("new specification title", "\\S+");
        String specificationValue = getField("specification value", "\\S+");
        specifications.put(specificationTitle, specificationValue);
        System.out.println("type <back> to continue or <next> to add more specifications");
        String command = getField("<next> or <back>", "(next|back)$");
        if (command.equals("next"))
            getSpecifications(specifications);
        return specifications;
    }

    private Menu getCompanyHistoryMenu() {
        return new Menu("Company History", this) {
            @Override
            public void show() {
                System.out.println(this.getName());
                System.out.println("commands\n1. back\n\n");
                System.out.println(commandProcessor.getCompanyInfo());
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
                for (int i = 1; i <= commandProcessor.getSalesHistory().size(); i++)
                    System.out.println(i + ". " + commandProcessor.getSalesHistory().get(i - 1));
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
                for (int i = 1; i <= commandProcessor.getAllSellerProducts().size(); i++)
                    System.out.println(i + ". " + commandProcessor.getAllSellerProducts().get(i - 1));
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
                    System.out.println(commandProcessor.getProductById(commandDetails[1]).toString());
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    for (int i = 1; i <= commandProcessor.getProductById(commandDetails[1]).getBuyers().size(); i++)
                        System.out.println(i + ". " + commandProcessor.getProductById(commandDetails[1]).getBuyers().get(i - 1));
                    return this;
                } else if (command.matches(commands.get(2))) {
                    String[] commandDetails = command.split("\\s");
                    String name = getField("name", "(\\w+)$");
                    String brand = getField("brand", "(\\w+)$");
                    String price = getField("price", "(\\d+)\\.(\\d+)$");
                    String remainingItems = getField("remaining items", "(\\d+)$");
                    String description = getField("description", "\\S+");
                    HashMap<String, String> specifications = new HashMap<String, String>();
                    getSpecifications(specifications);
                    commandProcessor.addProduct(name, brand, price, remainingItems, description, specifications);
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
                String remainingItems = getField("remaining items", "(\\d+)$");
                String description = getField("description", "\\S+");
                HashMap<String, String> specifications = new HashMap<String, String>();
                getSpecifications(specifications);
                commandProcessor.addProduct(name, brand, price, remainingItems, description, specifications);
                return this.parentMenu;
            }
        };
    }

    private Menu getCategoriesMenu() {
        return new Menu("categories", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + "\ncommands\n1. back");
                for (int i = 1; i <= commandProcessor.getCategories().size(); i++) {
                    System.out.println(i + ". " + commandProcessor.getCategories().get(i - 1).toString());
                }
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
                for (int i = 1; i <= commandProcessor.getAllSellerAuctions().size(); i++)
                    System.out.println(i + ". " + commandProcessor.getAllSellerAuctions().get(i - 1).toString());
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
                    System.out.println(commandProcessor.getAuctionWithId(commandDetails[1]));
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    String startingDate = getField("last date", "(\\d\\d)/(\\d\\d)/(\\d\\d)$");
                    String lastDate = getField("last date", "(\\d\\d)/(\\d\\d)/(\\d\\d)$");
                    String percent = getField("percent", "(\\d+)$");
                    String id = getField("auction id", "(\\d+)$");
                    //String listOfUsers = getField("products' id and separate them by comma", "(\\w+,)+");
                    ArrayList<String> products = getAuctionProducts();
                    commandProcessor.addAuction(startingDate, lastDate, percent, id, products);
                    return this;
                } else if (command.matches(commands.get(2))) {
                    String[] commandDetails = command.split("\\s");
                    String startingDate = getField("last date", "(\\d\\d)/(\\d\\d)/(\\d\\d)$");
                    String lastDate = getField("last date", "(\\d\\d)/(\\d\\d)/(\\d\\d)$");
                    String percent = getField("percent", "(\\d+)$");
                    String id = getField("auction id", "(\\d+)$");
                    //String listOfProducts = getField("products' id and separate them by comma", "(\\w+,)+");
                    ArrayList<String> products = getAuctionProducts();
                    commandProcessor.addAuction(startingDate, lastDate, percent, id, products);
                    return this;
                } else if (command.equals(commands.get(3))) {
                    return getGrandFatherMenu();
                } else if (command.equals(commands.get(4))) {
                    showCommands();
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }

            public ArrayList<String> getAuctionProducts() {
                ArrayList<String> products = new ArrayList<String>();
                System.out.println("enter the id of products that you wanna add to auction (at least one)");
                String id = getField("id", "\\S+");
                //TODO: give me the regex of id generator
                products.add(id);
                System.out.println("type <back> to continue or <next> to add more products");
                String command = getField("<next> or <back>", "(next|back)$");
                if (command.equals("next"))
                    getAuctionProducts();
                return products;
            }
        };
    }

    @Override
    public void show() {
        System.out.println(this.getName() + "\ncommands\n");
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
            return submenus.get(4);
        } else if (command.equals(commands.get(1))) {
            return submenus.get(5);
        } else if (command.equals(commands.get(2))) {
            return submenus.get(6);
        } else if (command.equals(commands.get(3))) {
            return submenus.get(7);
        } else if (command.equals(commands.get(4))) {
            return submenus.get(8);
        } else if (command.matches(commands.get(5))) {
            String[] commandDetails = command.split("\\s");
            commandProcessor.removeProductWithId(commandDetails[2]);
            return this;
        } else if (command.equals(commands.get(6))) {
            return submenus.get(9);
        } else if (command.equals(commands.get(7))) {
            return submenus.get(10);
        } else if (command.equals(commands.get(8))) {
            commandProcessor.getSellerBalance();
            return this;
        } else if (command.equals(commands.get(9))) {
            CommandProcessor.back();
            return this.parentMenu;
        } else if (command.equals(commands.get(10))) {
            return this;
        } else if (command.equals(commands.get(11))) {
            return submenus.get(1);
        } else if (command.equals(commands.get(12))) {
            return submenus.get(2);
        } else if (command.equals(commands.get(13))) {
            return submenus.get(3);
        }
        throw new InvalidCommandException("invalid command");
    }
}
