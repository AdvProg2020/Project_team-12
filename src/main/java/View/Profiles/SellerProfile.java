package View.Profiles;

import View.Menu;

public class SellerProfile extends Profile {
    Profile profile;

    public SellerProfile(Profile profile, Menu parentMenu) {
        super(parentMenu);
        this.profile = profile;
        //view personal info -> edit
        //view company history
        //view sales history
        //manage products -> view || view buyers || edit
        //add product
        //remove product
        //show categories
        //view offs -> view || edit || add off
        //view balance
    }
}
