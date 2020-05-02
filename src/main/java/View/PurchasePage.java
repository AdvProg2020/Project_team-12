package View;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class PurchasePage extends Menu {
    private static PurchasePage instance;

    public PurchasePage(Menu parentMenu) {
        super("Cart", parentMenu);
        submenus = new HashMap<Integer, Menu>();
        // add submenus
        //purchase
        //receiver information page ***
        //discount code page ***
        //payment page ***
        //view cart -> show products || view || increase || decrease || show total price || purchase
    }

    public static PurchasePage getInstance(Menu parentMenu) {
        if (instance == null) {
            instance = new PurchasePage(parentMenu);
        }
        return instance;
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
