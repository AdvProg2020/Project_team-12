package View;

import View.Exceptions.InvalidCommandException;
import View.Profiles.*;
import View.Profiles.RegisterPanel;
import Controller.CommandProcessors.*;

import java.util.HashMap;

public class MainMenu extends Menu {
    private Profile profile;
    private Profile defaultProfile = new Profile(this);

    public MainMenu() {
        super("Main Menu", null);
        submenus = new HashMap();
        submenus.put(1, this.profile);
        submenus.put(2, new ProductsPage(this));
        submenus.put(3, new AuctionsPage(this));
        submenus.put(4, PurchasePage.getInstance(this));
        submenus.put(5, new RegisterPanel(this));
        this.setSubmenus(submenus);
        setCommands();
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    private void setCommands() {
        commands.add("go to profile");
        commands.add("products");
        commands.add("offs");
        commands.add("go to cart");
        commands.add("go to register panel");
        commands.add("help");
        commands.add("exit");
    }


    @Override
    public void show() {
        System.out.println(this.getName() + "\ncommands\n");
        for (int i = 1; i <= commands.size(); i++) {
            System.out.println(i + ". " + commands.get(i - 1));
        }
    }

    @Override
    public Menu getCommand() throws Exception {
        System.out.println("what do you want to do?\n");
        String command = scanner.nextLine();
        if (command.equals(commands.get(0))) {
            String profileType = CommandProcessor.getInstance().getProfileType();
            if (profileType.equals("customer"))
                setProfile(new CustomerProfile(defaultProfile, this));
            else if (profileType.equals("seller"))
                setProfile(new SellerProfile(defaultProfile, this));
            else if (profileType.equals("manager"))
                setProfile(new ManagerProfile(defaultProfile, this));
            else
                setProfile(defaultProfile);
            CommandProcessor.goToSubCommandProcessor(CPS.ProfileCP.getId());
            return submenus.get(1);
        } else if (command.equals(commands.get(1))) {
            CommandProcessor.goToSubCommandProcessor(CPS.ProductsPageCP.getId());
            return submenus.get(2);
        } else if (command.equals(commands.get(2))) {
            CommandProcessor.goToSubCommandProcessor(CPS.AuctionPageCP.getId());
            return submenus.get(3);
        } else if (command.equals(commands.get(3))) {
            CommandProcessor.goToSubCommandProcessor(CPS.PurchasePageCP.getId());
            return submenus.get(4);
        } else if (command.equals(commands.get(4))) {
            CommandProcessor.goToSubCommandProcessor(CPS.RegisterPanelCP.getId());
            return submenus.get(5);
        } else if (command.equals(commands.get(5))) {
            return this;
        } else if (command.equals(commands.get(6))) {
            return null;
        }
        throw new InvalidCommandException("invalid command");
    }
}
