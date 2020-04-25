package View.Profiles;

import View.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPanel extends Menu {
    public RegisterPanel(Menu parentMenu) {
        super("Register Panel", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        // login page
        // register page
        //logout


        //create account type username
        //login username
    }

    private Menu getRegisterMenu(String type, String username) {
        return new Menu("Register Page", this) {
            @Override
            public void show() {
                System.out.println(this.getName());
            }

            @Override
            public Menu getCommand() throws Exception {
                String password = getField("password");
                String firstName = getField("first name");
                String lastName = getField("last name");
                String emailAddress = getField("email address");
                String phoneNumber = getField("phone number");
            }

            @Override
            public void run() {
                super.run();
            }
        };
    }

    private Matcher getMatcher (String regex, String input){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    @Override
    public Menu getCommand() throws Exception {
        return null;
    }
}
