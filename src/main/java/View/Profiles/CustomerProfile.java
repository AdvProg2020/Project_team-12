package View.Profiles;

import View.Menu;
import com.sun.org.apache.xpath.internal.operations.String;

public class CustomerProfile extends Profile {
    Profile profile;

    public CustomerProfile(Profile profile, Menu parentMenu) {
        super(parentMenu);
        this.profile = profile;
        //view personal info -> edit
        //view cart -> show products || view || increase || decrease || show total price || purchase
        //view orders -> show order || rate
        //view balance
        //view discount codes
    }
    private void viewCart(){}

}
