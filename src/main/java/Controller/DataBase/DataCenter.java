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
    private HashMap<String, Account> accountsByUsername;
    private HashMap<String, Product> productsByName;
    private ArrayList<Discount> discounts;


    public DataCenter() {
        initProducts();
        initAccounts();
        initDiscounts();
    }

    private void initAccounts() {
        accountsByUsername = new HashMap<>();
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
        productsByName = new HashMap<>();
        JsonFileReader reader = new JsonFileReader();
        File productsDirectory = new File(Config.getInstance().getProductsPath());
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
        discounts = new ArrayList<>();
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
                    return reader.read(file, Discount.class);
                } catch (FileNotFoundException var4) {
                    return null;
                }
            }).forEach(this::addDiscount);
        }
    }


    private void initDiscount(DiscountCode discount) throws Exception {
        JsonFileReader reader = new JsonFileReader();
        try {
            File file = new File(generateDiscountCodeAccountsFilePath(discount.getId()));
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

    private void initDiscount(Auction auction) throws Exception {
        JsonFileReader reader = new JsonFileReader();
        try {
            File file = new File(generateAuctionProductsFilePath(auction.getId()));
            ArrayList<Integer> strings = reader.read(file, ArrayList.class);
            ArrayList<Product> products = new ArrayList<>();
            for (int id : strings) {
                products.add(getProductById(id));
            }
            ((Auction) auction).setAllIncludedProducts(products);
            discounts.add(auction);
        } catch (FileNotFoundException var1) {
            throw new Exception("not enough info for auction with id:" + auction.getId());
        }
    }

    private void addProduct(Product product) {
        productsByName.put(product.getName(), product);
    }

    private void addAccount(Account account) {
        account.setAllDiscountCodes(new ArrayList<>());
        if (account instanceof Seller)
            for (ProductInfo productInfo : ((Seller) account).getAllProducts()) {
                productInfo.setProduct(productsByName.get(productInfo.getPName()));
            }
        accountsByUsername.put(account.getUsername(), account);
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

    public void saveAccount(Customer customer) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(accountRuntimeTypeAdapter);
        writer.write(customer, generateUserFilePath(customer.getUsername(), Config.AccountsPath.CUSTOMER.getNum(), "customer"), Account.class);

    }

    public void saveAccount(Seller seller) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(accountRuntimeTypeAdapter);
        writer.write(seller, generateUserFilePath(seller.getUsername(), Config.AccountsPath.SELLER.getNum(), "seller"), Account.class);
    }

    public void saveAccount(Manager manager) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(accountRuntimeTypeAdapter);
        writer.write(manager, generateUserFilePath(manager.getUsername(), Config.AccountsPath.MANAGER.getNum(), "manager"), Account.class);
    }

    private void addSavedAccount(Account account) {
        if (!accountsByUsername.containsValue(account))
            accountsByUsername.put(account.getUsername(), account);
    }

    public void saveProduct(Product product) throws IOException {
        JsonFileWriter writer = new JsonFileWriter();
        //TODO:a method which updates field categoryPath should be called on product
        writer.write(product, generateProductFilePath(product.getId()));
        if (!productsByName.containsValue(product))
            productsByName.put(product.getName(), product);
    }


    public void saveDiscount(Auction auction) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(discountsRuntimeTypeAdaptor);
        Discount tmp = auction;
        writer.write(tmp, generateAuctionFilePath(auction.getId()), Discount.class);
        ArrayList<Integer> products = new ArrayList<>();
        for (Product product : auction.getAllIncludedProducts()) {
            products.add(product.getId());
        }
        writer.write(products, generateAuctionProductsFilePath(auction.getId()));
        if (!discounts.contains(auction))
            discounts.add(auction);
    }

    public void saveDiscount(DiscountCode discountCode) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(discountsRuntimeTypeAdaptor);
        Discount tmp = discountCode;
        writer.write(tmp, generateDiscountCodeFilePath(discountCode.getId()), Discount.class);
        ArrayList<String> accounts = new ArrayList<>();
        for (Account product : discountCode.getAllAllowedAccounts()) {
            accounts.add(product.getUsername());
        }
        new JsonFileWriter().write(accounts, generateDiscountCodeAccountsFilePath(discountCode.getId()));
        if (!discounts.contains(discountCode))
            discounts.add(discountCode);
    }

    private String generateUserFilePath(String username, int state, String type) {
        String var10000 = Config.getInstance().getAccountsPath()[state] + "/" + username;
        return var10000 + "." + type + ".json";
    }

    private String generateProductFilePath(int id) {
        String var10000 = Config.getInstance().getProductsPath() + "/" + id;
        return var10000 + ".product.json";
    }

    private String generateAuctionFilePath(int id) {
        String var10000 = Config.getInstance().getDiscountsPath()[Config.DiscountsPath.AUCTION.getNum()] + "/" + id;
        return var10000 + ".auction.json";
    }

    private String generateDiscountCodeFilePath(int id) {
        String var10000 = Config.getInstance().getDiscountsPath()[Config.DiscountsPath.DISCOUNTCODE.getNum()] + "/" + id;
        return var10000 + ".discountcode.json";
    }

    private String generateAuctionProductsFilePath(int id) {
        String var10000 = Config.getInstance().getDiscountsPath()[Config.DiscountsPath.AUCTION.getNum()] + "/" + id;
        return var10000 + ".auction.products.json";
    }

    private String generateDiscountCodeAccountsFilePath(int id) {
        String var10000 = Config.getInstance().getDiscountsPath()[Config.DiscountsPath.DISCOUNTCODE.getNum()] + "/" + id;
        return var10000 + ".discountcode.accounts.json";
    }

    public Account getAccountByName(String name) {
        return accountsByUsername.get(name);
    }

    public Product getProductById(int id) {
        AtomicReference<Product> temp = new AtomicReference<>();
        productsByName.forEach((k, v) -> {
            if (v != null)
                if (v.getId() == id)
                    temp.set(v);
        });
        return temp.get();
    }

    public Product getProductByName(String name) {
        return productsByName.get(name);
    }
}
