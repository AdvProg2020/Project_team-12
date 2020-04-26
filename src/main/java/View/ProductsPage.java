package View;

import java.util.HashMap;
import java.util.regex.Matcher;

public class ProductsPage extends Menu{
    public ProductsPage(Menu parentMenu) {
        super("Product Page", parentMenu);
        submenus = new HashMap<Integer, Menu>();
        // add submenus
        //view categories
        //filtering -> show available filters || filter || current filters || disable filter
        //sorting -> show available sorts || sort || current sort || disable sort
        //show products
        //show product
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
