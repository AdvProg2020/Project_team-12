package View;

import View.Exceptions.InvalidCommandException;
import View.Profiles.Profile;
import View.Profiles.RegisterPanel;

import java.util.HashMap;
import java.util.regex.Matcher;

public class MainMenu extends Menu {

    public MainMenu() {
        super("Main Menu", null);
        submenus = new HashMap<Integer, Menu>();
        submenus.put(1, new Profile(this));
        submenus.put(2, new ProductsPage(this));
        submenus.put(3, new AuctionsPage(this));
        submenus.put(4, new PurchasePage(this));
        submenus.put(5, new RegisterPanel(this));
        this.setSubmenus(submenus);
    }

    private Matcher getMatcher(String regex) {
        return null;
    }


    @Override
    public void show() {
        for (Integer index : submenus.keySet()) {
            System.out.println(index + ". " + "go to " + submenus.get(index).getName());
        }
        System.out.println("6. exit");
    }

    @Override
    public Menu getCommand() throws Exception {
        String command = scanner.nextLine();
        for (Integer index : submenus.keySet()) {
            if (command.equals("go to " + submenus.get(index).getName()))
                return submenus.get(index);
        }
        if (command.equals("exit"))
            return null;
        throw new InvalidCommandException("invalid command");
    }
}
