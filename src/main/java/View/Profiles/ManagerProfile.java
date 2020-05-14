package View.Profiles;

import View.Exceptions.InvalidCommandException;
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
                //get accounts info and show
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
                    //calling view account method by commandDetails[1]
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    //calling delete account method by commandDetails[2]
                    return this;
                } else if (command.equals(commands.get(2))) {
                    String password = getField("password", "\\S+");
                    String firstName = getField("first name", "\\w+");
                    String lastName = getField("last name", "\\w+");
                    String emailAddress = getField("email address", "(\\w+)@(\\w+)\\.(\\w+)$");
                    String phoneNumber = getField("phone number", "(\\d+)$");
                    //adding other fields
                    //calling create manager profile method with these inputs : role username password ...
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

    public Menu getManageProductsMenu() {
        return new Menu("Managing Products", this) {
            public void setCommands() {
                commands.add("remove (\\d+)$");
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

            public void showCommands() {
                System.out.println("commands\n1. remove [product id]\n2. back\n3. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.matches(commands.get(0))) {
                    String[] commandDetails = command.split("\\s");
                    //calling remove product method by commandDetails[1]
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

    public Menu getCreateDiscountMenu() {
        return new Menu("Create Discount", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + "\n");
            }

            @Override
            public Menu getCommand() throws Exception {
                String lastDate = getField("last date", "(\\d\\d)/(\\d\\d)/(\\d\\d)$");
                String percent = getField("percent", "(\\d+)$");
                String code = getField("code", "\\S+");
                String maximumAmount = getField("maximum discount amount", "(\\d+)$");
                String numberOfUsages = getField("maximum number of usages", "(\\d+)$");
                String listOfUsers = getField("accounts' username and separate them by comma", "(\\w+,)+");
                //calling create discount method
                return this.parentMenu;
            }
        };
    }

    public Menu getDiscountCodesMenu() {
        return new Menu("Discount Codes", this) {
            public void setCommands() {
                commands.add("view discount code (\\S+)$");
                commands.add("edit discount code (\\S+)$");
                commands.add("remove discount code (\\S+)$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                //get discounts info and show
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
                    //calling view discount method by commandDetails[3]
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    String lastDate = getField("last date", "(\\d\\d)/(\\d\\d)/(\\d\\d)$");
                    String percent = getField("percent", "(\\d+)$");
                    String code = getField("code", "\\S+");
                    String maximumAmount = getField("maximum discount amount", "(\\d+)$");
                    String numberOfUsages = getField("maximum number of usages", "(\\d+)$");
                    String listOfUsers = getField("accounts' username and separate them by comma", "(\\w+,)+");
                    //calling edit discount method by commandDetails[3] and new fields
                    return this;
                } else if (command.matches(commands.get(2))) {
                    String[] commandDetails = command.split("\\s");
                    //calling remove discount method by commandDetails[3]
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

    public Menu getRequestsMenu() {
        return new Menu("Requests", this) {
            public void setCommands() {
                commands.add("details (\\d+)$");
                commands.add("accept (\\d+)$");
                commands.add("decline (\\d+)$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                //get requests info and show
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
                    //calling show request method by commandDetails[1]
                    return this;
                } else if (command.equals(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    //calling accept request method by commandDetails[1]
                    return this;
                } else if (command.equals(commands.get(2))) {
                    String[] commandDetails = command.split("\\s");
                    //calling decline request method by commandDetails[1]
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
                //get requests info and show
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
                    //calling show request method by commandDetails[1]
                    return this;
                } else if (command.equals(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    //calling accept request method by commandDetails[1]
                    return this;
                } else if (command.equals(commands.get(2))) {
                    String[] commandDetails = command.split("\\s");
                    //calling decline request method by commandDetails[1]
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
        System.out.println(this.getName()+"\ncommands\n");
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
            return submenus.get(7);
        } else if (command.equals(commands.get(4))) {
            return submenus.get(8);
        } else if (command.equals(commands.get(5))) {
            return submenus.get(9);
        } else if (command.equals(commands.get(6))) {
            return submenus.get(10);
        } else if (command.equals(commands.get(7))) {
            return this.parentMenu;
        } else if (command.equals(commands.get(8))) {
            return this;
        } else if (command.equals(commands.get(9))) {
            return submenus.get(1);
        } else if (command.equals(commands.get(10))) {
            return submenus.get(2);
        } else if (command.equals(commands.get(11))) {
            return submenus.get(3);
        }
        throw new InvalidCommandException("invalid command");
    }
}
