package View;

import Controller.CommandProcessors.CommandProcessor;
import Controller.CommandProcessors.ProductPageCP;
import Controller.CommandProcessors.ProductsPageCP;
import Controller.CommandProcessors.PurchasePageCP;
import View.Exceptions.InvalidCommandException;
import View.Profiles.RegisterPanel;

import java.util.HashMap;

public class ProductPage extends Menu {
    static ProductPageCP commandProcessor;
    String productId;

    public ProductPage(Menu parentMenu, String productId) {
        super("Product Page", parentMenu);
        this.productId = productId;
        submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getDigestMenu());
        submenus.put(2, getCommentsMenu());
        submenus.put(3, new RegisterPanel(this));
        setCommands();
    }

    public static void setCommandProcessor(ProductPageCP cp) {
        commandProcessor = cp;

    }

    public String getProductId() {
        return productId;
    }

    private void setCommands() {
        commands.add("digest");
        commands.add("attributes");
        commands.add("compare PR_(\\S+)$");
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
                System.out.println(commandProcessor.getSelectedProduct().toString());
                showCommands();
            }

            public void showCommands() {
                System.out.println("1. add to cart\n2. select seller [seller_username]\n3. back\n4. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                String command = scanner.nextLine();
                if (command.equals(commands.get(0)) || command.equals("1")) {
                    commandProcessor.addToCart();
                    return this;
                } else if (command.matches(commands.get(1)) || command.equals("2")) {
                    String[] commandDetails = command.split("\\s");
                    //select seller by commandDetails[2]
                    return this;
                } else if (command.equals(commands.get(2)) || command.equals("3")) {
                    return this.parentMenu;
                } else if (command.equals(commands.get(3)) || command.equals("4")) {
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
                for (int i = 1; i <= commandProcessor.getComments().size(); i++) {
                    System.out.println(i + ". " + commandProcessor.getComments().get(i - 1).toString());
                }
                showCommands();
            }

            public void showCommands() {
                System.out.println("1. add comment\n2. back\n3. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                String command = scanner.nextLine();
                if (command.equals(commands.get(0)) || command.equals("1")) {
                    String title = getField("title", ".+");
                    String content = getField("content", ".+");
                    commandProcessor.addReview(title, content);
                    return this;
                } else if (command.equals(commands.get(1)) || command.equals("3")) {
                    return this.parentMenu;
                } else if (command.equals(commands.get(2)) || command.equals("4")) {
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
        if (command.equals(commands.get(0)) || command.equals("1")) {
            return submenus.get(1);
        } else if (command.equals(commands.get(1)) || command.equals("2")) {
            System.out.println(commandProcessor.getSelectedProduct().toString());
            return this;
        } else if (command.matches(commands.get(2))) {
            String[] commandDetails = command.split("\\s");
            System.out.println("<<product 1>>\n" + commandProcessor.getSelectedProduct().toString());
            System.out.println("<<product 2>>\n" + commandProcessor.getProductById(commandDetails[1]).toString());
            return this;
        } else if (command.equals(commands.get(3)) || command.equals("4")) {
            return submenus.get(2);
        } else if (command.equals(commands.get(4)) || command.equals("5")) {
            CommandProcessor.back();
            return this.parentMenu;
        } else if (command.equals(commands.get(5)) || command.equals("6")) {
            return this;
        } else if (command.equals(commands.get(6)) || command.equals("7")) {
            return submenus.get(3);
        }
        throw new InvalidCommandException("invalid command");
    }
}
