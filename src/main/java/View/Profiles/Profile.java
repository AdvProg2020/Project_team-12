package View.Profiles;

import View.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class Profile extends Menu {
    public Profile(Menu parentMenu) {
        super("profile", parentMenu);
        submenus = new HashMap<Integer, Menu>();
        submenus.put(1,new RegisterPanel(this));
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
