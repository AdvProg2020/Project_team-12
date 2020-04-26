package View;

import java.util.HashMap;
import java.util.regex.Matcher;

public class ProductPage extends Menu{
    public ProductPage(Menu parentMenu) {
        super("Product Page", parentMenu);
        submenus = new HashMap<Integer, Menu>();
        // add submenus
        //digest -> add to cart || select seller
        //attributes
        //compare
        //comments -> add comment
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
