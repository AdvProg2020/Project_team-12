package Controller.DataBase;

import Controller.DataBase.Json.JsonFileReader;
import Controller.DataBase.Json.JsonFileWriter;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Discount.Auction;
import Model.Discount.Discount;
import Model.Discount.DiscountCode;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.ProductInfo;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class DataCenter {
    private final RuntimeTypeAdapterFactory<Account> accountRuntimeTypeAdapter = RuntimeTypeAdapterFactory.of(Account.class, "type")
            .registerSubtype(Customer.class, Customer.class.getName())
            .registerSubtype(Seller.class, Seller.class.getName())
            .registerSubtype(Manager.class, Manager.class.getName());
    private final RuntimeTypeAdapterFactory<Discount> discountsRuntimeTypeAdaptor = RuntimeTypeAdapterFactory.of(Discount.class, "type")
            .registerSubtype(Auction.class, Auction.class.getName())
            .registerSubtype(DiscountCode.class, DiscountCode.class.getName());
    private HashMap<String, Account> accountsByUsername = new HashMap();
    private HashMap<String, Product> productsByName = new HashMap();
    private ArrayList<Discount> discounts = new ArrayList<>();


    public DataCenter() {
        initProducts();
        initAccounts();
        initDiscounts();
    }

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
                    /*this is commented because i have made the category path a string in the product itself which is just used for storing the path in the product itself 
                    File categoryFileAddr = new File(Config.getInstance().getProductsPath() + "/CategoryPath" + temp.getName());
                    String categoryString = reader.read(categoryFileAddr, String.class);*/
                    /*temp.setParent(Category.categoryCreatorByTreeAddress(temp.getCategoryPath()));
                    temp.setCategoryPath(null);*/
                    //TODO: a method which creates the categories from its field should be called
                    return temp;
                } catch (FileNotFoundException var4) {
                    return null;
                }
            }).forEach(this::addProduct);
        }
    }

    private void initDiscounts() {
        JsonFileReader jsonFileReader = new JsonFileReader(discountsRuntimeTypeAdaptor);
        for (String s : Config.getInstance().getDiscountsPath()) {
            File directory = new File(s);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            initDiscountsEach(directory, jsonFileReader);
        }
    }

    private void initDiscountsEach(File discountsDir, JsonFileReader reader) {
        File[] discountsFileArr = discountsDir.listFiles();
        if (discountsFileArr != null) {
            Arrays.stream(discountsFileArr).map((file) -> {
                try {
                    return  reader.read(file, Discount.class);
                } catch (FileNotFoundException var4) {
                    return null;
                }
            }).forEach(this::addDiscount);
        }
    }

    private void addDiscount(Discount discount) {
        try {
            if (discount instanceof Auction) {
                initDiscount((Auction) discount);
            } else
                initDiscount((DiscountCode) discount);
        } catch (Exception exception) {
            return;
            //Logger.log(exception.getMessage())
        }
    }

    private void initDiscount(DiscountCode discount) throws Exception {
        JsonFileReader reader = new JsonFileReader();
        try {
            File file = new File(Config.getInstance().getDiscountsPath()[Config.DiscountsPath.DISCOUNTCODE.getNum()] + "DiscountCode" + discount.getId() + "Accounts");
            ArrayList<String> strings = reader.read(file, ArrayList.class);
            ArrayList<Account> accounts = new ArrayList<>();
            for (String s : strings) {
                accounts.add(accountsByUsername.get(s));
                accountsByUsername.get(s).addDiscountCode(discount);
            }
            discount.setAllAllowedAccounts(accounts);
            discounts.add(discount);
        } catch (FileNotFoundException var1) {
            throw new Exception("not enough info for auction with id" + discount.getId());
        }
    }

    private void initDiscount(Auction discount) throws Exception {
        JsonFileReader reader = new JsonFileReader();
        try {
            File file = new File(Config.getInstance().getDiscountsPath()[Config.DiscountsPath.AUCTION.getNum()] + "Auction" + discount.getId() + "Products");
            ArrayList<Integer> strings = reader.read(file, ArrayList.class);
            ArrayList<Product> products = new ArrayList<>();
            for (int id : strings) {
                products.add(getProductById(id));
            }
            ((Auction) discount).setAllIncludedProducts(products);
            discounts.add(discount);
        } catch (FileNotFoundException var1) {
            throw new Exception("not enough info for auction with id:" +  discount.getId());
        }
    }

    private void addProduct(Product product) {
        productsByName.put(product.getName(), product);
    }

    private void addAccount(Account account) {
        if (account instanceof Seller)
            for (ProductInfo productInfo : ((Seller) account).getAllProducts()) {
                productInfo.setProduct(productsByName.get(productInfo.getPName()));
            }
        accountsByUsername.put(account.getUsername(), account);
    }

    public void saveAccount(Customer customer) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(accountRuntimeTypeAdapter);
        writer.write(customer, Config.getInstance().getAccountsPath()[Config.AccountsPath.CUSTOMER.getNum()] + customer.getUsername());
    }

    public void saveAccount(Seller seller) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(accountRuntimeTypeAdapter);
        writer.write(seller, Config.getInstance().getAccountsPath()[Config.AccountsPath.SELLER.getNum()] + seller.getUsername());
    }

    public void saveAccount(Manager manager) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(accountRuntimeTypeAdapter);
        writer.write(manager, Config.getInstance().getAccountsPath()[Config.AccountsPath.MANAGER.getNum()] + manager.getUsername());
    }

    public void saveProduct(Product product) throws IOException {
        JsonFileWriter writer = new JsonFileWriter();
        //TODO:a method which updates field categoryPath should be called on product
        writer.write(product, Config.getInstance().getProductsPath() + product.getId());
    }

    public void saveDiscount(Auction auction) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(discountsRuntimeTypeAdaptor);
        writer.write(auction, Config.getInstance().getDiscountsPath()[Config.DiscountsPath.AUCTION.getNum()] + "Auction" + auction.getId());
        ArrayList<Integer> products = new ArrayList<>();
        for (Product product : auction.getAllIncludedProducts()) {
            products.add(product.getId());
        }
        new JsonFileWriter().write(products, Config.getInstance().getDiscountsPath()[Config.DiscountsPath.AUCTION.getNum()] + "Auction" + auction.getId() + "Products");
    }

    public void saveDiscount(DiscountCode discountCode) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(discountsRuntimeTypeAdaptor);
        writer.write(discountCode, Config.getInstance().getDiscountsPath()[Config.DiscountsPath.DISCOUNTCODE.getNum()] + "DiscountCode" + discountCode.getId());
        ArrayList<String> accounts = new ArrayList<>();
        for (Account product : discountCode.getAllAllowedAccounts()) {
            accounts.add(product.getUsername());
        }
        new JsonFileWriter().write(accounts, Config.getInstance().getDiscountsPath()[Config.DiscountsPath.DISCOUNTCODE.getNum()] + "DiscountCode" + discountCode.getId() + "Accounts");

    }

    public Account getAccountByName(String name) {
        return accountsByUsername.get(name);
    }

    public Product getProductById(int id) {
        AtomicReference<Product> temp = null;
        productsByName.forEach((k, v) -> {
            if (v.getId() == id)
                temp.set(v);
        });
        return temp.get();
    }

    public Product getProductByName(String name) {
        return productsByName.get(name);
    }
}
