package View;

import View.Profiles.Profile;
import View.Profiles.RegisterPanel;

import java.util.HashMap;
import java.util.regex.Matcher;

public class MainMenu extends Menu {

    public MainMenu() {
        super("Main Menu", null);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
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

    private void products() {
    }

    @Override
    public Menu getCommand() throws Exception {
        return null;
    }
}
