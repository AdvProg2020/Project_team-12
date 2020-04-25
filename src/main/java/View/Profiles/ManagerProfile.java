package View.Profiles;

import View.Menu;

public class ManagerProfile extends Profile{
    Profile profile;

    public ManagerProfile(Profile profile, Menu parentMenu) {
        super(parentMenu);
        this.profile = profile;
        // view personal info -> edit
        // manage users -> view || delete || create manager profile
        // manage all products -> remove
        // create discount code
        // view discount codes -> view || edit || remove
        // manage requests -> details || accept || decline
        // manage categories -> edit || add || remove
    }
}
