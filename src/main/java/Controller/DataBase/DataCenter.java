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
import Model.Request.AuctionRequest;
import Model.Request.ProductInfoRequest;
import Model.Request.Request;
import Model.Request.ReviewRequest;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.sun.org.apache.regexp.internal.RE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class DataCenter {
    private static DataCenter Instance;
    private final RuntimeTypeAdapterFactory<Account> accountRuntimeTypeAdapter = RuntimeTypeAdapterFactory.of(Account.class, "type")
            .registerSubtype(Customer.class, Customer.class.getName())
            .registerSubtype(Seller.class, Seller.class.getName())
            .registerSubtype(Manager.class, Manager.class.getName());
    private final RuntimeTypeAdapterFactory<Request> requestRuntimeTypeAdapter = RuntimeTypeAdapterFactory.of(Request.class, "type")
            .registerSubtype(ProductInfoRequest.class, ProductInfoRequest.class.getName())
            .registerSubtype(AuctionRequest.class, AuctionRequest.class.getName())
            .registerSubtype(ReviewRequest.class, ReviewRequest.class.getName());
    private final RuntimeTypeAdapterFactory<Discount> discountsRuntimeTypeAdaptor = RuntimeTypeAdapterFactory.of(Discount.class, "type")
            .registerSubtype(Auction.class, Auction.class.getName())
            .registerSubtype(DiscountCode.class, DiscountCode.class.getName());
    private HashMap<String, Account> accountsByUsername;
    private HashMap<String, Product> productsByName;
    private ArrayList<Discount> discounts;
    private ArrayList<Request> requests;

    private DataCenter() {
        initProducts();
        initAccounts();
        initDiscounts();
        initRequests();
    }

    public static DataCenter getInstance() {
        if (Instance == null) {
           Instance = new DataCenter();
        }
        return Instance;
    }

    private void initRequests() {
        requests = new ArrayList<>();
        JsonFileReader jsonFileReader = new JsonFileReader(requestRuntimeTypeAdapter);
        File requestsFile = new File(Config.getInstance().getRequestsPath());
        if (!requestsFile.exists()) {
            requestsFile.mkdirs();
        }
        File[] files = requestsFile.listFiles();
        Arrays.stream(files).map((file) -> {
            try {
                return  jsonFileReader.read(file, Request.class);
            } catch (FileNotFoundException e) {
                return null;
            }
        }).forEach(this::addRequest);
    }

    private void addRequest(Request request) {
        if (!requests.contains(request))
            requests.add(request);
    }

    public void deleteRequestWithId(int id){
        for (Request request : requests) {
            if (request.getId() == id)
                requests.remove(request);
        }
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
                    if (file.getPath().contains("discountcode.accounts.json") || file.getPath().contains("auction.products.json"))
                        return null;
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
        }
    }

    private void initDiscount(Auction auction) throws Exception {
        JsonFileReader reader = new JsonFileReader();
        try {
            File file = new File(generateAuctionProductsFilePath(auction.getId()));
            ArrayList<Double> strings = reader.read(file, ArrayList.class);
            ArrayList<Product> products = new ArrayList<>();
            for (double id : strings) {
                products.add(getProductById((int) id));
            }
            ((Auction) auction).setAllIncludedProducts(products);
            discounts.add(auction);
        } catch (FileNotFoundException var1) {
        }
    }

    private void addProduct(Product product) {
        if (!productsByName.containsValue(product.getName()))
            productsByName.put(product.getName(), product);
    }

    private void addAccount(Account account) {
        if (!accountsByUsername.containsValue(account)) {
            account.setAllDiscountCodes(new ArrayList<>());
            if (account instanceof Seller)
                for (ProductInfo productInfo : ((Seller) account).getAllProducts()) {
                    productInfo.setProduct(productsByName.get(productInfo.getPName()));
                }
            accountsByUsername.put(account.getUsername(), account);
        }
    }

    private void addDiscount(Discount discount) {
        if (!discounts.contains(discount))
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
    public void saveAccount(Account account) throws IOException{
        if (account instanceof Seller)
            saveAccount((Seller) account);
        if (account instanceof Customer)
            saveAccount((Customer) account);
        if (account instanceof Manager)
            saveAccount((Manager) account);
        addSavedAccount(account);
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

    public void saveDiscount(Discount discount) throws IOException {
        if (discount instanceof Auction) {
            saveDiscount((Auction) discount);
        } else {
            saveDiscount((DiscountCode) discount);
        }
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

    public void saveRequest(Request request) throws IOException {
        JsonFileWriter jsonFileWriter = new JsonFileWriter(requestRuntimeTypeAdapter);
        jsonFileWriter.write(request,generateRequestsFilePath(request.getId()),Request.class);
    }

    private void addSavedAccount(Account account) {
        if (!accountsByUsername.containsValue(account))
            accountsByUsername.put(account.getUsername(), account);
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

    private String generateRequestsFilePath(int id) {
        String var10000 = Config.getInstance().getRequestsPath() + "/" + id;
        return var10000 + ".request.json";
    }

    public Account getAccountByName(String name) {
        return accountsByUsername.get(name);
    }

    public boolean userExistWithUsername(String username) {
        for (String accountUsername : accountsByUsername.keySet()) {
            if (username.equals(accountUsername))
                return true;
        }
        return false;
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

    public DiscountCode getDiscountcodeWithId(int id) throws BadRequestException {
        for (Discount discount : discounts) {
            if (discount != null && discount instanceof DiscountCode && discount.getId() == id)
                return (DiscountCode) discount;
        }
        throw new BadRequestException("discount not found");
    }

    public Auction getAuctionWithId(int id) throws BadRequestException {
        for (Discount discount : discounts) {
            if (discount != null && discount instanceof Auction && discount.getId() == id)
                return (Auction) discount;
        }
        throw new BadRequestException("Auction not found");
    }

    public Product getProductWithId(int id) throws BadRequestException {
        for (Product product : productsByName.values()) {
            if (product != null && product.getId() == id)
                return product;
        }
        throw new BadRequestException("product with this id hasn't found");
    }

    public boolean productExistWithId(int id) {
        for (Product product : productsByName.values()) {
            if (product != null && product.getId() == id)
                return true;
        }
        return false;
    }

    public boolean auctionExistsWithId(int id) {
        for (Discount discount : discounts) {
            if (discount != null && discount instanceof Auction && discount.getId() == id)
                return true;
        }
        return false;
    }

    public boolean discountcodeExistsWithId(int id) {
        for (Discount discount : discounts) {
            if (discount != null && discount instanceof DiscountCode && discount.getId() == id)
                return true;
        }
        return false;
    }
    public ArrayList<Request> getAllUnsolvedRequests(){
        return requests;
    }

    public Set<String> getAllAccountsInfo() {
        return this.accountsByUsername.keySet();
    }

    public boolean doesUsernameExist(String username) {
        for (String accountUsername : this.accountsByUsername.keySet()) {
            if (username.equals(accountUsername))
                return true;
        }
        return false;
    }
}

class BadRequestException extends Exception {
    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

}
