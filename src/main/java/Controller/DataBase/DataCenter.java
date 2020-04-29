package Controller.DataBase;

import Controller.DataBase.Json.JsonFileReader;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Discount.Auction;
import Model.Discount.Discount;
import Model.Discount.DiscountCode;
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Product;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;

public class DataCenter {
    private final RuntimeTypeAdapterFactory<Account> accountRuntimeTypeAdapter = RuntimeTypeAdapterFactory.of(Account.class, "type")
            .registerSubtype(Customer.class, Customer.class.getName())
            .registerSubtype(Seller.class, Seller.class.getName())
            .registerSubtype(Manager.class, Manager.class.getName());
    private final RuntimeTypeAdapterFactory<Discount> AuctionsRuntimeTypeAdaptor = RuntimeTypeAdapterFactory.of(Discount.class, "type")
            .registerSubtype(Auction.class, Auction.class.getName())
            .registerSubtype(DiscountCode.class, DiscountCode.class.getName());
    private HashMap<String, Account> accountsByUsername = new HashMap();
    private HashMap<String, Product> productsByName = new HashMap();
    //private HashMap<String, Category> categoriesByName = new HashMap<>();

    private void initAccounts() {
        JsonFileReader jsonFileReader = new JsonFileReader(accountRuntimeTypeAdapter);
        for (String s : Config.getInstance().getAccountsPath()) {
            File accountsDirectory = new File(s);
            if (!accountsDirectory.exists()) {
                accountsDirectory.mkdirs();
            }
            initAccountsEach(accountsDirectory, jsonFileReader);
        }

    }

    private void initAccountsEach(File accountsDirectory, JsonFileReader reader) {
        File[] usersFiles = accountsDirectory.listFiles();
        if (usersFiles != null) {
            Arrays.stream(usersFiles).map((file) -> {
                try {
                    return (Account) reader.read(file, Account.class);
                } catch (FileNotFoundException var4) {
                    return null;
                }
            }).forEach(this::addAccount);
        }
    }

    private void initProducts() {
        JsonFileReader reader = new JsonFileReader();
        File productsDirectory = new File(Config.getInstance().getProductsPath() + "/Raw");
        if (!productsDirectory.exists())
            productsDirectory.mkdir();
        File[] productsFiles = productsDirectory.listFiles();
        if (productsFiles != null) {
            Arrays.stream(productsFiles).map((file) -> {
                try {
                    Product temp = reader.read(file, Product.class);
                    File categoryFileAddr = new File(Config.getInstance().getProductsPath() + "/CategoryPath" + temp.getName());
                    String categoryString = reader.read(categoryFileAddr, String.class);
                    temp.setParent(Category.categoryCreatorByTreeAddress(categoryString));
                    return temp;
                } catch (FileNotFoundException var4) {
                    return null;
                }
            }).forEach(this::addProduct);
        }
    }

    private void initDiscounts(){

    }

    private void addProduct(Product product) {
        productsByName.put(product.getName(),product);
    }

    private void addAccount(Account account) {
        accountsByUsername.put(account.getUsername(), account);
    }

    private void initProducts(JsonFileReader reader) {

    }


    public void registerAccount(Account account) {
        if (this.accountsByUsername.containsKey(account.getUsername())) {
            throw new RuntimeException("this username is not available.");
        } else {
            this.accountsByUsername.put(account.getUsername(), account);
        }
    }

    public void registerProduct(Product product) {
        if (this.productsByName.containsKey(product.getName())) {
            throw new RuntimeException("this username is not available.");
        } else {
            this.productsByName.put(product.getName(), product);
        }
    }

    public Account getAccountByName(String name) {
        return null;
    }

    public Product getProductByName(String name) {
        return null;
    }

    public Product getProductById(int id) {
        return null;
    }
}
