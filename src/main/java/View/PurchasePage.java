package View;

import java.util.HashMap;
import java.util.regex.Matcher;

public class PurchasePage extends Menu{
    public PurchasePage(Menu parentMenu) {
        super("Cart", parentMenu);
        submenus = new HashMap<Integer, Menu>();
        // add submenus
        //purchase
        //receiver information page ***
        //discount code page ***
        //payment page ***
    }

    @Override
    public Menu getCommand() throws Exception {
        return null;
    }

    public void run(){}
    private Matcher getMatcher (String regex){
        return null;
    }
}
