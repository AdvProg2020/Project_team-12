package View;

import View.Exceptions.InvalidCommandException;
import View.Profiles.RegisterPanel;

import java.util.HashMap;

public class AuctionsPage extends ProductsPage {

    public AuctionsPage(Menu parentMenu) {
        super(parentMenu);
        submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getFilteringMenu());
        submenus.put(2, getSortingMenu());
        submenus.put(3, new RegisterPanel(this));
        //show product
    }

    private void setCommands() {
        commands.add("filtering");
        commands.add("sorting");
        commands.add("show product (\\d+)$");
        commands.add("back");
        commands.add("help");
        commands.add("go to register panel");
    }


    @Override
    public void show() {
        System.out.println(this.getName() + "\ncommands\n");
        for (int i = 1; i <= commands.size(); i++) {
            if (i == 3)
                System.out.println("5. show product [productId]");
            else
                System.out.println(i + ". " + commands.get(i - 1));
        }
        //show products in auction with details
    }

    @Override
    public Menu getCommand() throws Exception {
        String command = scanner.nextLine();
        if (command.equals(commands.get(0))) {
            return submenus.get(1);
        } else if (command.equals(commands.get(1))) {
            return submenus.get(2);
        } else if (command.matches(commands.get(4))) {
            String[] commandDetails = command.split("\\s");
            //check if product with this id exists
            return new ProductPage(this, commandDetails[2]);
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
