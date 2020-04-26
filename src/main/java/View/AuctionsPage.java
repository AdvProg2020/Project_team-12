package View;

import java.util.HashMap;
import java.util.regex.Matcher;

public class AuctionsPage extends Menu {

    public AuctionsPage(Menu parentMenu) {
        super("Auction Page", parentMenu);
        submenus = new HashMap<Integer, Menu>();
        // add submenus
        //show product
        //filtering -> show available filters || filter || current filters || disable filter
        //sorting -> show available sorts || sort || current sort || disable sort
    }

    @Override
    public void show() {

    }

    @Override
    public Menu getCommand() throws Exception {
        return null;
    }

    public void run() {
    }

    private Matcher getMatcher(String regex) {
        return null;
    }
}
