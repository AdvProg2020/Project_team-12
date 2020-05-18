package View;

import Controller.CommandProcessors.CommandProcessor;
import Controller.CommandProcessors.PurchasePageCP;
import View.Exceptions.CustomerExceptions;
import View.Exceptions.InvalidCommandException;
import View.Profiles.RegisterPanel;

import java.util.HashMap;

public class PurchasePage extends Menu {
    private static PurchasePage instance;
    static PurchasePageCP commandProcessor;

    public PurchasePage(Menu parentMenu) {
        super("Cart", parentMenu);
        submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getPurchaseMenu());
        submenus.put(2, new RegisterPanel(this));
        setCommands();
    }

    public static PurchasePage getInstance(Menu parentMenu) {
        if (instance == null) {
            instance = new PurchasePage(parentMenu);
        }
        return instance;
    }

    public static void setCommandProcessor(PurchasePageCP cp) {
        commandProcessor = cp;

    }

    private void setCommands() {
        commands.add("show products");
        commands.add("view PR_(\\S+)$");
        commands.add("increase PR_(\\S+)$");
        commands.add("decrease PR_(\\S+)$");
        commands.add("show total price");
        commands.add("purchase");
        commands.add("back");
        commands.add("help");
        commands.add("go to register panel");
    }

    private Menu getPurchaseMenu() {
        return new Menu("Purchase Page", this) {
            @Override
            public void show() {
                System.out.println(this.getName());
            }

            private void getReceiverInformation() throws Exception {
                System.out.println("Receiver Information");
                String receiverInfo = "";
                String receiverName = getField("receiver name", "(\\w)+");
                String receiverLastName = getField("receiver last name", "(\\w)+");
                String receiverPhoneNumber = getField("phone number", "(\\d)+");
                String receiverAddress = getField("address", ".+");
                receiverInfo += "name : " + receiverName + "\nlast name : " + receiverLastName + "\nphone number : " + receiverPhoneNumber + "\naddress : " + receiverAddress;
                String command = getField("<<next>> or <<back>>", "(next|back)");
                if (command.equals("back")) {
                    commandProcessor.setReceiverInfo(receiverInfo);
                    return;
                } else if (command.equals("next")) {
                    commandProcessor.setReceiverInfo(receiverInfo);
                    getDiscountCode();
                }
            }

            private void getDiscountCode() throws Exception {
                System.out.println("enter discount code or write <<nothing>> instead");
                String code = getField("discount code", "\\S+");
                if (code.equals("nothing")) {
                    getPaymentInformation(code);
                } else {
                    if (!commandProcessor.checkDiscountCode(code)) {
                        System.err.println("invalid discount code for this account");
                        getDiscountCode();
                    } else
                        getPaymentInformation(code);
                    return;
                }
            }

            private void getPaymentInformation(String discountCode) throws Exception {
                System.out.println("payment");
                System.out.println("total price :" + commandProcessor.getPaymentAmount());
                String command = getField("<<finish>> or <<back>>", "(finish|back)");
                if (command.equals("finish")) {
                    commandProcessor.buy(discountCode);
                    return;
                } else {
                    getDiscountCode();
                }
            }

            @Override
            public Menu getCommand() throws Exception {
                getReceiverInformation();
                return this.parentMenu;
            }
        };
    }

    @Override
    public void show() {
        System.out.println(this.getName() + "\ncommands\n");
        for (int i = 1; i <= commands.size(); i++) {
            if (i == 2)
                System.out.println("2. view [productId]");
            else if (i == 3)
                System.out.println("3. increase [productId]");
            else if (i == 4)
                System.out.println("4. decrease [productId]");
            else
                System.out.println(i + ". " + commands.get(i - 1));
        }
    }

    @Override
    public Menu getCommand() throws Exception {
        String command = scanner.nextLine();
        if (command.equals(commands.get(0)) || command.equals("1")) {
            System.out.println(commandProcessor.showProductsInCart());
            return this;
        } else if (command.matches(commands.get(1))) {
            String[] commandDetails = command.split("\\s");
            return new ProductPage(this, commandDetails[1]);
        } else if (command.matches(commands.get(2))) {
            String[] commandDetails = command.split("\\s");
            commandProcessor.increaseProduct(commandDetails[1]);
            return this;
        } else if (command.matches(commands.get(3))) {
            String[] commandDetails = command.split("\\s");
            commandProcessor.decreaseProductWithID(commandDetails[1]);
            return this;
        } else if (command.equals(commands.get(4)) || command.equals("5")) {
            System.out.println(commandProcessor.showTotalPrice());
            return this;
        } else if (command.equals(commands.get(5)) || command.equals("6")) {
            return submenus.get(1);
        } else if (command.equals(commands.get(6)) || command.equals("7")) {
            return this.parentMenu;
        } else if (command.equals(commands.get(7)) || command.equals("8")) {
            return this;
        } else if (command.equals(commands.get(8)) || command.equals("9")) {
            return submenus.get(2);
        }
        throw new InvalidCommandException("invalid command");
    }
}
