package Controller.CommandProcessors;

import Controller.DataBase.Config;
import Controller.DataBase.DataCenter;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Discount.DiscountCode;
import Model.ProductsOrganization.Cart;
import View.AuctionsPage;
import View.Exceptions.InvalidCommandException;
import View.Exceptions.ProductExceptions;
import View.Exceptions.RegisterPanelException;
import View.MainMenu;
import View.ProductsPage;
import View.Profiles.Profile;
import View.Profiles.RegisterPanel;
import View.PurchasePage;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

public class CommandProcessor {
    protected static CommandProcessor Instance;
    private static CommandProcessor Primitive;
    private static Account loggedInAccount;
    private static Cart cart = Cart.getInstance();
    private CommandProcessor Parent;
    protected DataCenter dataCenter;

    protected CommandProcessor(CommandProcessor parent) {
        Parent = parent;
        this.dataCenter = DataCenter.getInstance();
    }

    public static CommandProcessor getInstance() {
        if (Instance == null) {
            Instance = new CommandProcessor(null);
            Primitive = Instance;
        }
        return Instance;
    }

    public static void setInstance(CommandProcessor instance) {
        Instance = instance;
    }

    public static boolean managerExists() {
        File file = new File(Config.getInstance().getAccountsPath()[Config.AccountsPath.MANAGER.getNum()]);
        return file.exists() && file.listFiles().length != 0;
    }

    public static CommandProcessor getPrimitive() {
        getInstance();
        return Primitive;
    }

    public static void setPrimitive(CommandProcessor primitive) {
        Primitive = primitive;
    }

    public static void back() {
        /*if (Instance == null)
            Instance = MainMenuCP.getInstance();*/
        Instance = Instance.getParent();
    }

    public static void goToSubCommandProcessor(int ID) throws Exception {
        switch (ID) {
            case 1:
                AuctionsPageCP.getInstance().setParent(Instance);
                Instance = AuctionsPageCP.getInstance();
                AuctionsPage.setCommandProcessor((AuctionsPageCP) Instance);
                break;
            case 2:
                MainMenuCP.getInstance().setParent(Instance);
                Instance = MainMenuCP.getInstance();
                MainMenu.setCommandProcessor((MainMenuCP) Instance);
                break;

            case 4:
                ProductsPageCP.getInstance().setParent(Instance);
                Instance = ProductsPageCP.getInstance();
                ProductsPage.setCommandProcessor((ProductsPageCP) Instance);
                break;
            case 5:
                ProfileCP.getInstance().setParent(Instance);
                Instance = ProfileCP.getInstance();
                Profile.setCommandProcessor((ProfileCP) Instance);
                break;
            case 6:
                PurchasePageCP.getInstance().setParent(Instance);
                Instance = PurchasePageCP.getInstance();
                PurchasePage.setCommandProcessor((PurchasePageCP) Instance);
                break;
            case 7:
                RegisterPanelCP.getInstance().setParent(Instance);
                Instance = RegisterPanelCP.getInstance();
                RegisterPanel.setCommandProcessor((RegisterPanelCP) Instance);
                break;
        }
    }

    public static Cart getCart() {
        return cart;
    }

    public static void setCart(Cart cart) {
        CommandProcessor.cart = cart;
    }

    public static Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public String getProfileType() {
        if (loggedInAccount instanceof Customer)
            return "customer";
        else if (loggedInAccount instanceof Seller)
            return "seller";
        else if (loggedInAccount instanceof Manager)
            return "manager";
        else
            return "not logged in";
    }

    public static void setLoggedInAccount(Account loggedInAccount) {
        CommandProcessor.loggedInAccount = loggedInAccount;
    }

    public String getPersonalInfo() {
        return loggedInAccount.getPersonalInfo();
    }

    public String getPersonalInfo(String username) throws Exception {
        Account account = dataCenter.getAccountByName(username);
        return account.getPersonalInfo();
    }

    public void editPersonalInfo(String field, String newValue) throws Exception {
        if ((field.equals("firstName") || field.equals("lastName")) && !newValue.matches("\\w+")) {
            throw new InvalidCommandException("illegal field input");
        } else if (field.equals("phoneNumber") && !newValue.matches("(\\d+)$")) {
            throw new InvalidCommandException("invalid field input");
        } else if (field.equals("emailAddress") && !newValue.matches("(\\w+)@(\\w+)\\.(\\w+)$")) {
            throw new InvalidCommandException("invalid field input");
        } else if (field.equals("password") && !newValue.matches("\\S+")) {
            throw new InvalidCommandException("invalid field input");
        }
        if (field.equals("firstName"))
            loggedInAccount.setFirstName(newValue);
        else if (field.equals("lastName"))
            loggedInAccount.setLastName(newValue);
        else if (field.equals("phoneNumber"))
            loggedInAccount.setPhoneNumber(newValue);
        else if (field.equals("emailAddress"))
            loggedInAccount.setEmailAddress(newValue);
        else if (field.equals("password"))
            loggedInAccount.setPassword(newValue);
        dataCenter.saveAccount(loggedInAccount);
    }

    public Set<String> getAllAccountsInfo() {
        //this method is only used for manager
        return dataCenter.getAllAccountsInfo();
    }

    public void deleteDiscountCode(String code) throws Exception {
        DiscountCode discountCode = dataCenter.getDiscountcodeWithCode(code);
        dataCenter.deleteDiscountCode(discountCode);

        //should i delete it for each customer ??
    }

    public void deleteAccount(String username) throws Exception {
        if (!dataCenter.doesUsernameExist(username))
            throw new RegisterPanelException("username doesn't exist");
        else if (loggedInAccount.getUsername().equals(username))
            throw new RegisterPanelException("you can not delete yourself idiot.");
        else
        if (!dataCenter.deleteAccount(username))
            throw new RegisterPanelException("can't delete this account");
    }

    public void deleteProduct(String productId) throws Exception {
        if (!dataCenter.doesProductExist(productId))
            throw new RegisterPanelException("product doesn't exist");
        if (!dataCenter.deleteProduct(dataCenter.getProductById(productId)))
            throw new ProductExceptions("can't delete this product");
    }

    public ArrayList<String> getAllProducts() {
        return dataCenter.getAllProductsWithNoCondition();
    }

    public void createDiscountCode(String startingDate, String lastDate, String percent, String code, String maximumAmount, String numberOfUsages, String listOfUsers) throws Exception {
        ArrayList<Account> usersList = new ArrayList<Account>();
        String[] users = listOfUsers.split("\\s");
        for (String username : users) {
            usersList.add(dataCenter.getAccountByName(username));
        }
        DateFormat format = new SimpleDateFormat("yy/mm/dd", Locale.ENGLISH);
        DiscountCode discountCode = new DiscountCode(format.parse(startingDate), format.parse(lastDate), Double.parseDouble(percent),
                dataCenter.getNewDiscountID(), code, Integer.parseInt(maximumAmount), Integer.parseInt(numberOfUsages), usersList);
        dataCenter.saveDiscount(discountCode);
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() {
        return dataCenter.getAllDiscountCodes();
    }

    public DiscountCode getDiscountCode(String code) throws Exception {
        return dataCenter.getDiscountcodeWithCode(code);
    }

    public void editDiscountCode(String code, String startingDate, String lastDate, String percent, String maximumAmount, String numberOfUsages, String listOfUsers) throws Exception {
        DiscountCode discountCode = dataCenter.getDiscountcodeWithCode(code);
        ArrayList<Account> usersList = new ArrayList<Account>();
        String[] users = listOfUsers.split("\\s");
        for (String username : users) {
            usersList.add(dataCenter.getAccountByName(username));
        }
        DateFormat format = new SimpleDateFormat("yy/mm/dd", Locale.ENGLISH);
        discountCode.setStart(format.parse(startingDate));
        discountCode.setEnd(format.parse(lastDate));
        discountCode.setPercent(Double.parseDouble(percent));
        discountCode.setMaximumDiscount(Integer.parseInt(maximumAmount));
        discountCode.setMaxUsageNumber(Integer.parseInt(numberOfUsages));
        discountCode.setAllAllowedAccounts(usersList);
        dataCenter.saveDiscount(discountCode);
    }

    //cart

    public boolean checkDiscountCode(String code) throws Exception {
        DiscountCode discountCode = dataCenter.getDiscountcodeWithCode(code);
        for (Account account : discountCode.getAllAllowedAccounts()) {
            if (account.equals(loggedInAccount))
                return true;
        }
        return false;
    }

    public void setReceiverInfo(String receiverInfo) {
        cart.setReceiverInfo(receiverInfo);
    }

    public Double getPaymentAmount() {
        return cart.getPayAmount();
    }

    public void buy() throws Exception {

    }


    public CommandProcessor getParent() {
        return Parent;
    }

    public void setParent(CommandProcessor parent) {
        Parent = parent;
    }

}
