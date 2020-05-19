package View.Profiles;

import Controller.CommandProcessors.CPS;
import Controller.CommandProcessors.CommandProcessor;
import Model.Discount.DiscountCode;
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Product;
import Model.Request.Request;
import View.Exceptions.CustomerExceptions;
import View.Exceptions.InvalidCommandException;
import View.InputUtility;
import View.Menu;

import java.util.ArrayList;

public class ManagerProfile extends Profile {
    Profile profile;

    public ManagerProfile(Profile profile, Menu parentMenu) {
        super(parentMenu);
        this.profile = profile;
        submenus.put(4, getPersonalInfoMenu());
        submenus.put(5, getManageUsersMenu());
        submenus.put(6, getManageProductsMenu());
        submenus.put(7, getCreateDiscountMenu());
        submenus.put(8, getDiscountCodesMenu());
        submenus.put(9, getRequestsMenu());
        submenus.put(10, getCategoriesMenu());
        this.setSubmenus(submenus);
        this.commands = new ArrayList<String>();
        setCommands();
    }

    private void setCommands() {
        commands.add("view personal info");
        commands.add("manage users");
        commands.add("manage all products");
        commands.add("create discount code");
        commands.add("view discount codes");
        commands.add("manage requests");
        commands.add("manage categories");
        commands.add("back");
        commands.add("help");
        commands.add("go to register panel");
        commands.add("products");
        commands.add("offs");
    }

    public Menu getManageUsersMenu() {
        return new Menu("Managing Users", this) {
            public void setCommands() {
                commands.add("view (\\w+)$");
                commands.add("delete user (\\w+)$");
                commands.add("create manager profile");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                ArrayList<String> allAccountsInfo = new ArrayList<>();
                allAccountsInfo.addAll(commandProcessor.getAllAccountsInfo());
                for (int i = 0; i < allAccountsInfo.size(); i++) {
                    System.out.println((i + 1) + ". " + allAccountsInfo.get(i));
                }
                System.out.println("\n");
                showCommands();
            }

            public void showCommands() {
                System.out.println("commands\n1. view [username]\n2. delete user [username]\n3. create manager profile\n4. back\n5. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.matches(commands.get(0))) {
                    String[] commandDetails = command.split("\\s");
                    System.out.println(commandProcessor.getPersonalInfo(commandDetails[1]));
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    commandProcessor.deleteAccount(commandDetails[2]);
                    return this;
                } else if (command.equals(commands.get(2)) || command.equals("3")) {
                    String username = getField("username", "\\S+");
                    String password = getField("password", "\\S+");
                    String firstName = getField("first name", "\\w+");
                    String lastName = getField("last name", "\\w+");
                    String emailAddress = getField("email address", "(\\w+)@(\\w+)\\.(\\w+)$");
                    String phoneNumber = getField("phone number", "(\\d+)$");
                    //adding other fields
                    commandProcessor.createManagerAccount(username, password, firstName, lastName, phoneNumber, emailAddress);
                    return this;
                } else if (command.equals(commands.get(3)) || command.equals("4")) {
                    return this.parentMenu;
                } else if (command.equals(commands.get(4)) || command.equals("5")) {
                    showCommands();
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }
        };
    }

    public Menu getManageProductsMenu() {
        return new Menu("Managing Products", this) {
            public void setCommands() {
                commands.add("remove PR_(\\S+)$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                ArrayList<Product> allProductsInfo =  commandProcessor.getAllProducts();
                for (int i = 1; i <= allProductsInfo.size(); i++) {
                    System.out.println(i + ". " + allProductsInfo.get(i - 1).getShortInfo());
                }
                showCommands();
            }

            public void showCommands() {
                System.out.println("commands\n1. remove [product id]\n2. back\n3. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.matches(commands.get(0))) {
                    String[] commandDetails = command.split("\\s");
                    commandProcessor.deleteProduct(commandDetails[1]);
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

    public Menu getCreateDiscountMenu() {
        return new Menu("Create Discount", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + "\n");
            }

            @Override
            public Menu getCommand() throws Exception {
                String startingDate = getField("starting date", "(\\d\\d)/(\\d\\d)/(\\d\\d)$");
                String lastDate = getField("last date", "(\\d\\d)/(\\d\\d)/(\\d\\d)$");
                String percent = getField("percent", "(\\d+)$");
                String code = getField("code", "\\S+");
                String maximumAmount = getField("maximum discount amount", "(\\d+)$");
                String numberOfUsages = getField("maximum number of usages", "(\\d+)$");
                String listOfUsers = getField("accounts' username and separate them by comma", "(\\w+,)+");
                commandProcessor.createDiscountCode(startingDate, lastDate, percent, code, maximumAmount, numberOfUsages, listOfUsers);
                return this.parentMenu;
            }
        };
    }

    public Menu getDiscountCodesMenu() {
        return new Menu("Discount Codes", this) {
            public void setCommands() {
                commands.add("view discount code AU_(\\S+)$");
                commands.add("edit discount code AU_(\\S+)$");
                commands.add("remove discount code AU_(\\S+)$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                for (DiscountCode discountCode : commandProcessor.getAllDiscountCodes()) {
                    System.out.println(discountCode.toString());
                }
                showCommands();
            }

            public void showCommands() {
                System.out.println("commands\n1. view discount code [code]\n2. edit discount code [code]\n3. remove discount code [code]\n4. back\n5. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.matches(commands.get(0))) {
                    String[] commandDetails = command.split("\\s");
                    System.out.println(commandProcessor.getDiscountCode(commandDetails[3]).toString());
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    String startingDate = getField("starting date", "(\\d\\d)/(\\d\\d)/(\\d\\d)$");
                    String lastDate = getField("last date", "(\\d\\d)/(\\d\\d)/(\\d\\d)$");
                    String percent = getField("percent", "(\\d+)$");
                    String maximumAmount = getField("maximum discount amount", "(\\d+)$");
                    String numberOfUsages = getField("maximum number of usages", "(\\d+)$");
                    String listOfUsers = getField("accounts' username and separate them by comma", "(\\w+,)+");
                    commandProcessor.editDiscountCode(commandDetails[3], startingDate, lastDate, percent, maximumAmount, numberOfUsages, listOfUsers);
                    return this;
                } else if (command.matches(commands.get(2))) {
                    String[] commandDetails = command.split("\\s");
                    commandProcessor.deleteDiscountCode(commandDetails[3]);
                    return this;
                } else if (command.equals(commands.get(3)) || command.equals("4")) {
                    return this.parentMenu;
                } else if (command.equals(commands.get(4)) || command.equals("5")) {
                    showCommands();
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }
        };
    }

    public Menu getRequestsMenu() {
        return new Menu("Requests", this) {
            public void setCommands() {
                commands.add("details REQ_(\\S+)$");
                commands.add("accept REQ_(\\S+)$");
                commands.add("decline REQ_(\\S+)$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                ArrayList<Request> requests = commandProcessor.getRequests();
                for (Request request : requests) {
                    System.out.println(request.getId() + "\n");
                }
                showCommands();
            }

            public void showCommands() {
                System.out.println("commands\n1. details [request Id]\n2. accept [request Id]\n3. decline [request Id]\n4. back\n5. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.matches(commands.get(0))) {
                    String[] commandDetails = command.split("\\s");
                    System.out.println(commandProcessor.showRequestDetail(commandDetails[1]));
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    commandProcessor.acceptRequest(commandDetails[1]);
                    return this;
                } else if (command.matches(commands.get(2))) {
                    String[] commandDetails = command.split("\\s");
                    String cause;
                    if (commandProcessor.checkRequestType(commandDetails[1])) {
                        cause = InputUtility.getInstance().nextLine();
                        commandProcessor.declineRequest(commandDetails[1], cause);
                    } else
                        commandProcessor.declineRequest(commandDetails[1]);
                    return this;
                } else if (command.equals(commands.get(3)) || command.equals("4")) {
                    return this.parentMenu;
                } else if (command.equals(commands.get(4)) || command.equals("5")) {
                    showCommands();
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }
        };
    }

    public Menu getCategoriesMenu() {
        return new Menu("Categories", this) {
            public void setCommands() {
                commands.add("edit (\\w+)$");
                commands.add("add (\\w+)$");
                commands.add("remove (\\w+)$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                ArrayList<Category> categories = commandProcessor.getCategories();
                for (Category category : categories) {
                    System.out.println(category.toString());
                }
                showCommands();
            }

            public void showCommands() {
                System.out.println("commands\n1. edit [category]\n2. add [category]\n3. remove [category]\n4. back\n5. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.matches(commands.get(0))) {
                    String[] commandDetails = command.split("\\s");
                    String categoryName = getField("category name", "\\w+");
                    String parentCategoryName = getField("parent category name", "\\w+");
                    ArrayList<String> specifications = new ArrayList<String>();
                    specifications = getSpecifications(specifications);
                    if (!commandProcessor.doesCategoryExistsWithThisName(commandDetails[1]))
                        throw new CustomerExceptions("category with this name doesn't exist");
                    commandProcessor.addCategory(categoryName, parentCategoryName, specifications);
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    String categoryName = getField("category name", "\\w+");
                    String parentCategoryName = getField("parent category name", "\\w+");
                    ArrayList<String> specifications = new ArrayList<String>();
                    specifications = getSpecifications(specifications);
                    if (commandProcessor.doesCategoryExistsWithThisName(commandDetails[1]))
                        throw new CustomerExceptions("category with this name exists");
                    commandProcessor.addCategory(categoryName, parentCategoryName, specifications);
                    return this;
                } else if (command.matches(commands.get(2)) ) {
                    String[] commandDetails = command.split("\\s");
                    commandProcessor.removeCategory(commandDetails[1]);
                    return this;
                } else if (command.equals(commands.get(3)) || command.equals("4")) {
                    return this.parentMenu;
                } else if (command.equals(commands.get(4)) || command.equals("5")) {
                    showCommands();
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }

            public ArrayList<String> getSpecifications(ArrayList<String> specifications) throws Exception {
                System.out.println("add specifications to your category (at least one)");
                String specificationTitle = getField("new specification title", "\\S+");
                specifications.add(specificationTitle);
                System.out.println("type <back> to continue or <next> to add more specifications");
                String command = getField("<next> or <back>", "(next|back)$");
                if (command.equals("next"))
                    getSpecifications(specifications);
                return specifications;
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
        if (command.equals(commands.get(0)) || command.equals("1")) {
            return submenus.get(4);
        } else if (command.equals(commands.get(1)) || command.equals("2")) {
            return submenus.get(5);
        } else if (command.equals(commands.get(2)) || command.equals("3")) {
            return submenus.get(6);
        } else if (command.equals(commands.get(3)) || command.equals("4")) {
            return submenus.get(7);
        } else if (command.equals(commands.get(4)) || command.equals("5")) {
            return submenus.get(8);
        } else if (command.equals(commands.get(5)) || command.equals("6")) {
            return submenus.get(9);
        } else if (command.equals(commands.get(6)) || command.equals("7")) {
            return submenus.get(10);
        } else if (command.equals(commands.get(7)) || command.equals("8")) {
            CommandProcessor.back();
            return this.parentMenu;
        } else if (command.equals(commands.get(8)) || command.equals("9")) {
            return this;
        } else if (command.equals(commands.get(9)) || command.equals("10")) {
            CommandProcessor.goToSubCommandProcessor(CPS.RegisterPanelCP.getId());
            return submenus.get(1);
        } else if (command.equals(commands.get(10)) || command.equals("11")) {
            CommandProcessor.goToSubCommandProcessor(CPS.ProductsPageCP.getId());
            return submenus.get(2);
        } else if (command.equals(commands.get(11)) || command.equals("12")) {
            CommandProcessor.goToSubCommandProcessor(CPS.AuctionPageCP.getId());
            return submenus.get(3);
        }
        throw new InvalidCommandException("invalid command");
    }
}
