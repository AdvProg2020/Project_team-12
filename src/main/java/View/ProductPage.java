package View;

import View.Exceptions.InvalidCommandException;
import View.Profiles.RegisterPanel;

import java.util.HashMap;

public class ProductPage extends Menu {
    String productId;
    public ProductPage(Menu parentMenu, String productId) {
        super("Product Page", parentMenu);
        this.productId = productId;
        submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getDigestMenu());
        //attributes
        //compare
        submenus.put(2, getCommentsMenu());
        submenus.put(3, new RegisterPanel(this));
        setCommands();
    }

    private void setCommands() {
        commands.add("digest");
        commands.add("attributes");
        commands.add("compare (\\d+)$");
        commands.add("comments");
        commands.add("back");
        commands.add("help");
        commands.add("go to register panel");
    }

    private Menu getDigestMenu() {
        return new Menu("Digest", this) {
            private void setCommands() {
                commands.add("add to cart");
                commands.add("select seller (\\w+)$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                //get product's info and show
                showCommands();
            }

            public void showCommands() {
                System.out.println("1. add to cart\n2. select seller [seller_username]\n3. back\n4. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                String command = scanner.nextLine();
                if (command.equals(commands.get(0))) {
                    //add this product to cart
                    //if not logged in return register panel
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    //select seller by commandDetails[2]
                    return this;
                } else if (command.equals(commands.get(2))) {
                    return this.parentMenu;
                } else if (command.equals(commands.get(3))) {
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }
        };
    }

    private Menu getCommentsMenu() {
        return new Menu("Comments", this) {
            private void setCommands() {
                commands.add("add comment");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                //get comments info and show
                showCommands();
            }

            public void showCommands() {
                System.out.println("1. add comment\n2. back\n3. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                String command = scanner.nextLine();
                if (command.equals(commands.get(0))) {
                    getField("title", ".+");
                    getField("content", ".+");
                    //add comment
                    return this;
                } else if (command.equals(commands.get(2))) {
                    return this.parentMenu;
                } else if (command.equals(commands.get(3))) {
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
            if (i == 3)
                System.out.println("5. compare [productId]");
            else
                System.out.println(i + ". " + commands.get(i - 1));
        }
    }

    @Override
    public Menu getCommand() throws Exception {
        String command = scanner.nextLine();
        if (command.equals(commands.get(0))) {
            return submenus.get(1);
        } else if (command.equals(commands.get(1))) {
            //show attributes
            return this;
        } else if (command.matches(commands.get(2))) {
            String[] commandDetails = command.split("\\s");
            //compare this product with commandDetails[1]
            return this;
        } else if (command.equals(commands.get(3))) {
            return submenus.get(2);
        } else if (command.equals(commands.get(4))) {
            return this.parentMenu;
        } else if (command.equals(commands.get(5))) {
            return this;
        } else if (command.equals(commands.get(6))) {
            return submenus.get(3);
        }
        throw new InvalidCommandException("invalid command");
    }
}
