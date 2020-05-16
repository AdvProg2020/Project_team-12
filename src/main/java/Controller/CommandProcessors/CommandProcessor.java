package Controller.CommandProcessors;

import Controller.DataBase.Config;
import Controller.DataBase.DataCenter;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Account.*;
import Model.Discount.Auction;
import Model.Discount.Discount;
import Model.Discount.DiscountCode;
import Model.Log.PurchaseLog;
import Model.Log.SellLog;
import Model.ProductsOrganization.Cart;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.ProductInfo;
import Model.ProductsOrganization.Score;
import View.Exceptions.CustomerExceptions;
import View.Exceptions.InvalidCommandException;
import View.Exceptions.ProductExceptions;
import View.Exceptions.RegisterPanelException;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

public class CommandProcessor {
    protected static CommandProcessor Instance;
    private static CommandProcessor Primitive;
    private static CommandProcessor Parent;
    private static Account loggedInAccount = null;
    private static DataCenter dataCenter = DataCenter.getInstance();
    private static Cart cart;

    public CommandProcessor(CommandProcessor parent) {
        Parent = parent;
        this.loggedInAccount = null;
        this.dataCenter = DataCenter.getInstance();
    }

    public static CommandProcessor getInstance() {
        if (Instance == null) {
            Instance = MainMenuCP.getInstance();
            Primitive = Instance;
        }
        return Instance;
    }

    public static void setInstance(CommandProcessor instance) {
        Instance = instance;
    }

    public static boolean managerExists() {
        File file = new File(Config.getInstance().getAccountsPath()[Config.AccountsPath.MANAGER.getNum()]);
        if (!file.exists() || file.listFiles().length == 0)
            return false;
        return true;
    }

    public static CommandProcessor getPrimitive() {
        getInstance();
        return Primitive;
    }

    public static void setPrimitive(CommandProcessor primitive) {
        Primitive = primitive;
    }

    public static void back() {
        Instance = Instance.getParent();
    }

    public static void goToSubCommandProcessor(int ID) throws Exception {
        switch (ID) {
            case 1:
                AuctionsPageCP.getInstance().setParent(Instance);
                Instance = AuctionsPageCP.getInstance();
                break;
            case 2:
                MainMenuCP.getInstance().setParent(Instance);
                Instance = MainMenuCP.getInstance();
                break;
            case 3:
                ProductPageCP.getInstance().setParent(Instance);
                Instance = ProductPageCP.getInstance();
                break;
            case 4:
                ProductsPageCP.getInstance().setParent(Instance);
                Instance = ProductsPageCP.getInstance();
                break;
            case 5:
                ProfileCP.getInstance().setParent(Instance);
                Instance = ProfileCP.getInstance();
                break;
            case 6:
                PurchasePageCP.getInstance().setParent(Instance);
                Instance = PurchasePageCP.getInstance();
                break;
            case 7:
                RegisterPanelCP.getInstance().setParent(Instance);
                Instance = RegisterPanelCP.getInstance();
                break;
        }
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

    public void setLoggedInAccount(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public static void createAccount(String username, String role, String password, String name, String lastName, String phoneNumber, String emailAddress, String companyInfo) throws Exception {
        Account newAccount;
        if (role.equals("customer")) {
            newAccount = new Customer(username, name, lastName, emailAddress, phoneNumber, password);
            dataCenter.saveAccount((Customer) newAccount);
        } else if (role.equals("seller")) {
            newAccount = new Seller(username, name, lastName, emailAddress, phoneNumber, password, companyInfo);
            //send a request to manager
        }
    }

    public void createManagerAccount(String username, String password, String name, String lastName, String phoneNumber, String emailAddress) throws Exception {
        Manager manager = new Manager(username, name, lastName, emailAddress, phoneNumber, password);
        dataCenter.saveAccount(manager);
    }

    public void login(String username, String password) throws Exception {
        setLoggedInAccount(dataCenter.getAccountByName(username));
        if (!checkPassword(password)) {
            setLoggedInAccount(null);
            throw new RegisterPanelException("incorrect password");
        }
    }

    public boolean doesUsernameExists(String username) {
        return dataCenter.userExistWithUsername(username);
    }


    public boolean checkPassword(String password) {
        if (this.loggedInAccount.getPassword().equals(password))
            return true;
        else
            return false;
    }

    public String getPersonalInfo() {
        return loggedInAccount.getPersonalInfo();
    }

    public String getPersonalInfo(String username) {
        Account account = dataCenter.getAccountByName(username);
        return account.getPersonalInfo();
    }

    public void editPersonalInfo(String field, String newValue) throws Exception {
        if ((field.equals("first name") || field.equals("last name")) && !newValue.matches("\\w+")) {
            throw new InvalidCommandException("illegal field input");
        } else if (field.equals("phone number") && !newValue.matches("(\\d+)$")) {
            throw new InvalidCommandException("invalid field input");
        } else if (field.equals("email address") && !newValue.matches("(\\w+)@(\\w+)\\.(\\w+)$")) {
            throw new InvalidCommandException("invalid field input");
        } else if (field.equals("password") && !newValue.matches("\\S+")) {
            throw new InvalidCommandException("invalid field input");
        }
        if (field.equals("first name"))
            this.loggedInAccount.setFirstName(newValue);
        else if (field.equals("last name"))
            this.loggedInAccount.setLastName(newValue);
        else if (field.equals("phone number"))
            this.loggedInAccount.setPhoneNumber(newValue);
        else if (field.equals("email address"))
            this.loggedInAccount.setEmailAddress(newValue);
        else if (field.equals("password"))
            this.loggedInAccount.setPassword(newValue);
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
        if (!dataCenter.deleteAccount(username))
            throw new RegisterPanelException("can't delete this account");
    }

    public void deleteProduct(String productId) throws Exception {
        if (!dataCenter.doesProductExist(productId))
            throw new RegisterPanelException("product doesn't exist");
        if (!dataCenter.deleteProduct(dataCenter.getProductById(Integer.parseInt(productId))))
            throw new ProductExceptions("can't delete this product");
    }

    public Set<String> getAllProducts() {
        return dataCenter.getAllProducts();
    }

    public void createDiscountCode(String startingDate, String lastDate, String percent, String code, String maximumAmount, String numberOfUsages, String listOfUsers) throws Exception {
        ArrayList<Account> usersList = new ArrayList<Account>();
        String[] users = listOfUsers.split("\\s");
        for (String username : users) {
            usersList.add(dataCenter.getAccountByName(username));
        }
        DateFormat format = new SimpleDateFormat("yy/mm/dd", Locale.ENGLISH);
        DiscountCode discountCode = new DiscountCode(format.parse(startingDate), format.parse(lastDate), Double.parseDouble(percent),
                dataCenter.getLastDiscountId() + 1, code, Integer.parseInt(maximumAmount), Integer.parseInt(numberOfUsages), usersList);
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
        discountCode.setMaximumDiscountAmount(Integer.parseInt(maximumAmount));
        discountCode.setMaximumNumberOfUsages(Integer.parseInt(numberOfUsages));
        discountCode.setAllAllowedAccounts(usersList);
        dataCenter.saveDiscount(discountCode);
    }

    //seller
    public static String getCompanyInfo() {
        return ((Seller) loggedInAccount).getCompanyInformation();
    }

    public static ArrayList<SellLog> getSalesHistory() {
        return ((Seller) loggedInAccount).getSellLogs();
    }

    public ArrayList<ProductInfo> getAllSellerProducts() {
        return ((Seller) this.loggedInAccount).getAllProducts();
    }

    public ProductInfo getProductById(String id) throws Exception {
        for (ProductInfo product : ((Seller) this.loggedInAccount).getAllProducts()) {
            if (Integer.toString(product.getId()).equals(id))
                return product;
        }
        throw new ProductExceptions("there is no product with this id");
    }

    public ArrayList<Auction> getAllSellerAuctions() {
        ArrayList<Auction> allAuctions = new ArrayList<Auction>();
        for (Integer auctionId : ((Seller) this.loggedInAccount).getAuctionsId()) {
            Auction auction = null;
            try {
                auction = dataCenter.getAuctionWithId(auctionId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            allAuctions.add(auction);
        }
        return allAuctions;
    }

    public Auction getAuctionWithId(String id) throws Exception {
        return dataCenter.getAuctionWithId(Integer.parseInt(id));
    }

    public void removeProductWithId(String id) throws Exception {
        if (!dataCenter.doesProductExist(id))
            throw new ProductExceptions("there is no product with this id");
        ProductInfo productInfo = getProductById(id);
        if (productInfo.getBuyers().size() == 1) {
            if (!dataCenter.deleteProduct(dataCenter.getProductById(Integer.parseInt(id))))
                throw new ProductExceptions("can't delete product");
            dataCenter.deleteProductInfo(productInfo, this.loggedInAccount.getUsername());
        } else
            dataCenter.deleteProductInfo(productInfo, this.loggedInAccount.getUsername());
    }

    public String getSellerBalance() {
        return Double.toString(((Seller) this.loggedInAccount).getCredit());
    }

    //seller end
    //customer
    public ArrayList<PurchaseLog> getCustomerOrdersHistory() {
        return ((Customer) this.loggedInAccount).getBuyLogs();
    }

    public PurchaseLog getOrderById(String orderId) throws Exception {
        for (PurchaseLog order : getCustomerOrdersHistory()) {
            if (Integer.toString(order.getId()).equals(orderId))
                return order;
        }
        throw new CustomerExceptions("there is no order with this id");
    }

    public String getCustomerBalance() {
        return Double.toString(((Customer) this.loggedInAccount).getCredit());
    }

    public ArrayList<DiscountCode> getCustomerDiscountCodes() {
        return ((Customer) this.loggedInAccount).getAllDiscountCodes();
    }

    public void rate(String productId, String score) throws Exception {
        if (!dataCenter.doesProductExist(productId))
            throw new ProductExceptions("there is no product with this id");
        Product product = dataCenter.getProductById(Integer.parseInt(productId));
        Score newScore = new Score(Double.parseDouble(score));
        product.addScore(newScore);
        dataCenter.saveProduct(product);
    }

    //customer end

    //cart

    public boolean checkDiscountCode(String code) throws Exception {
        DiscountCode discountCode = dataCenter.getDiscountcodeWithCode(code);
        for (Account account : discountCode.getAllAllowedAccounts()) {
            if (account.equals(this.loggedInAccount))
                return true;
        }
        return false;
    }

    public void setReceiverInfo(String receiverInfo){
        cart.setReceiverInfo(receiverInfo);
    }

    public Double getPaymentAmount(){
        return cart.getPayAmount();
    }

    public void buy() throws Exception{
        if (loggedInAccount.getCredit() < getPaymentAmount())
            throw new CustomerExceptions("you don't have enough credit");
        else {

            Seller[] sellers = (Seller[]) cart.getTraders().values().toArray();
            SellLog sellLog = new SellLog()
            //add trade logs
            //reduce customer credit
            //increase seller credit
            cart.restart();
        }
    }


    public CommandProcessor getParent() {
        return Parent;
    }

    public void setParent(CommandProcessor parent) {
        Parent = parent;
    }
}
