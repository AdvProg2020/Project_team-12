package Controller.DataBase;

import Controller.DataBase.Json.JsonFileReader;
import Controller.DataBase.Json.JsonFileWriter;
import Model.Account.*;
import Model.Discount.Auction;
import Model.Discount.Discount;
import Model.Discount.DiscountCode;
import Model.Log.PurchaseLog;
import Model.Log.SellLog;
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Product;
import Model.Request.*;
import Model.Status;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class DataCenter {
    private static DataCenter Instance;
    private final RuntimeTypeAdapterFactory<Discount> discountsRuntimeTypeAdaptor = RuntimeTypeAdapterFactory.of(Discount.class, "type")
            .registerSubtype(Auction.class, Auction.class.getName())
            .registerSubtype(DiscountCode.class, DiscountCode.class.getName());
    private final RuntimeTypeAdapterFactory<Account> accountRuntimeTypeAdapter = RuntimeTypeAdapterFactory.of(Account.class, "type")
            .registerSubtype(Customer.class, Customer.class.getName())
            .registerSubtype(Seller.class, Seller.class.getName())
            .registerSubtype(Manager.class, Manager.class.getName());
    private final RuntimeTypeAdapterFactory<Request> requestRuntimeTypeAdapter = RuntimeTypeAdapterFactory.of(Request.class, "type")
            .registerSubtype(ProductRequest.class, ProductRequest.class.getName())
            .registerSubtype(AuctionRequest.class, AuctionRequest.class.getName())
            .registerSubtype(ReviewRequest.class, ReviewRequest.class.getName())
            .registerSubtype(SellerRequest.class, SellerRequest.class.getName());

    private HashMap<String, Account> accountsByUsername;
    private HashMap<String, Product> productsByName;
    private ArrayList<Discount> discounts;
    private ArrayList<Request> requests;
    private HashMap<String, Category> categories;

    private DataCenter() {
        initCategories();
        initAccounts();
        initProducts();
        initDiscounts();
        initRequests();
    }

    public static String getNewSellID(String sellerUserName) {
        ArrayList<String> args = new ArrayList<>();
        try {
            for (SellLog log : ((Seller) getInstance().getAccountByName(sellerUserName)).getSellLogs()) {
                args.add(log.getId());
            }
        } catch (Exception exception) {
        } finally {
            String[] result = {};
            return RandomIDGenerator.generateSellID((String[]) args.toArray(result));
        }
    }

    public static String getNewPurchaseID(String customerUsername) {
        ArrayList<String> args = new ArrayList<>();
        try {
            for (PurchaseLog log : ((Customer) getInstance().getAccountByName(customerUsername)).getBuyLogs()) {
                args.add(log.getId());
            }
        } catch (Exception exception) {
        } finally {
            String[] result = {};
            return RandomIDGenerator.generateBuyID((String[]) args.toArray(result));
        }
    }

    public static DataCenter getInstance() {
        if (Instance == null) {
            Instance = new DataCenter();
        }
        System.gc();
        return Instance;
    }

    private void initProducts() {
        productsByName = new HashMap<>();
        for (Account value : accountsByUsername.values()) {
            if (value instanceof Seller)
                ((Seller) value).getAllProducts().forEach(this::addProduct);
        }
    }

    private void initCategories() {
        categories = new HashMap<>();
        JsonFileReader reader = new JsonFileReader();
        File file = new File(Config.getInstance().getCategoriesPath());
        if (!file.exists())
            file.mkdir();
        File[] files = file.listFiles();
        if (files != null)
            Arrays.stream(files).map((file1) -> {
                try {
                    return reader.read(file1, Category.class);
                } catch (FileNotFoundException e) {
                    return null;
                }
            }).forEach(this::addCategory);
    }

    private void addCategory(Category category) {
        category.setAllProductsInside(new HashMap<>());
        category.setAllSubCategories(new HashMap<>());
        String[] categories = category.getCategoryPath().split("/");
        Category var100 = null;
        for (int i = 0; i < categories.length - 1; i++) {
            if (!this.categories.containsValue(categories[i])) {
                initCategory(categories[i]);
                var100 = this.categories.get(categories[i]);
            }
        }
        category.setParent(var100);
        if (var100 != null)
            var100.getAllSubCategories().put(category.getName(), category);
        this.categories.put(category.getName(), category);
    }

    private void initCategory(String category) {
        File file = new File(generateCategoryFilePath(category));
        JsonFileReader reader = new JsonFileReader();
        try {
            addCategory(reader.read(file, Category.class));
        } catch (FileNotFoundException e) {
            Category category1 = new Category(category, new ArrayList<String>(), null);

            addCategory(category1);
        }
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
                return jsonFileReader.read(file, Request.class);
            } catch (FileNotFoundException e) {
                return null;
            }
        }).forEach(this::addRequest);
    }

    public void addRequest(Request request) {
        if (!requests.contains(request))
            requests.add(request);
    }

    public void deleteRequestWithId(String id) {
        System.gc();
        for (Request request : requests) {
            if (request.getId().equals(id)) {
                requests.remove(request);
                File file = new File(generateRequestsFilePath(id));
                file.delete();
                return;
            }
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
            File file = new File(generateDiscountCodeAccountsFilePath(discount.getID()));
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
            File file = new File(generateAuctionProductsFilePath(auction.getID()));
            ArrayList<String> strings = reader.read(file, ArrayList.class);
            ArrayList<Model.ProductsOrganization.Product> products = new ArrayList<>();
            for (String id : strings) {
                products.add(getProductById(id));
            }
            ((Auction) auction).setAllProducts(products);
            discounts.add(auction);
        } catch (FileNotFoundException ignored) {
        }
    }

    public void addProduct(Model.ProductsOrganization.Product product) {
        if (!(product.getParentStr()!= null && product.getParentStr()!=""&&product.getParent()!=null))
        product.setParent(categories.get(product.getParentStr()));
        if (!productsByName.containsValue(product.getName()))
            productsByName.put(product.getName(), product);
    }

    public void addAccount(Account account) {
        if (!accountsByUsername.containsValue(account)) {
            account.setAllDiscountCodes(new ArrayList<>());
            accountsByUsername.put(account.getUsername(), account);
        }
    }

    public void addDiscount(Discount discount) {
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

    public void saveAccount(Account account) throws IOException {
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
        for (Product product : seller.getAllProducts()) {
            if (product.getParent() != null)
                product.setParentStr(product.getParent().getName());
        }
        writer.write(seller, generateUserFilePath(seller.getUsername(), Config.AccountsPath.SELLER.getNum(), "seller"), Account.class);
    }

    public void saveAccount(Manager manager) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(accountRuntimeTypeAdapter);
        writer.write(manager, generateUserFilePath(manager.getUsername(), Config.AccountsPath.MANAGER.getNum(), "manager"), Account.class);
    }

    public void saveDiscount(Discount discount) throws IOException {
        if (discount instanceof Auction) {
            saveDiscount((Auction) discount);
        } else {
            saveDiscount((DiscountCode) discount);
        }
        addDiscount(discount);
    }

    public void saveDiscount(Auction auction) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(discountsRuntimeTypeAdaptor);
        Discount tmp = auction;
        writer.write(tmp, generateAuctionFilePath(auction.getID()), Discount.class);
        ArrayList<String> products = new ArrayList<>();
        for (Model.ProductsOrganization.Product product : auction.getAllProducts()) {
            products.add(product.getID());
        }
        writer.write(products, generateAuctionProductsFilePath(auction.getID()));
        if (!discounts.contains(auction))
            discounts.add(auction);
    }

    public void saveDiscount(DiscountCode discountCode) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(discountsRuntimeTypeAdaptor);
        Discount tmp = discountCode;
        writer.write(tmp, generateDiscountCodeFilePath(discountCode.getID()), Discount.class);
        ArrayList<String> accounts = new ArrayList<>();
        for (Account product : discountCode.getAllAllowedAccounts()) {
            accounts.add(product.getUsername());
        }
        new JsonFileWriter().write(accounts, generateDiscountCodeAccountsFilePath(discountCode.getID()));
        if (!discounts.contains(discountCode))
            discounts.add(discountCode);
    }

    public void saveRequest(Request request) throws IOException {
        JsonFileWriter jsonFileWriter = new JsonFileWriter(requestRuntimeTypeAdapter);
        jsonFileWriter.write(request, generateRequestsFilePath(request.getId()), Request.class);
    }

    public void saveCategory(Category category) throws IOException {
        JsonFileWriter writer = new JsonFileWriter();
        category.setCategoryPath(category.createCategoryStringPath(category));
        writer.write(category, generateCategoryFilePath(category.getName()));
        addCategory(category);
    }

    private void addSavedAccount(Account account) {
        if (!accountsByUsername.containsValue(account))
            accountsByUsername.put(account.getUsername(), account);
    }

    private String generateUserFilePath(String username, int state, String type) {
        String var10000 = Config.getInstance().getAccountsPath()[state] + "/" + username;
        return var10000 + "." + type + ".json";
    }

    private String generateAuctionFilePath(String id) {
        String var10000 = Config.getInstance().getDiscountsPath()[Config.DiscountsPath.AUCTION.getNum()] + "/" + id;
        return var10000 + ".auction.json";
    }

    private String generateDiscountCodeFilePath(String id) {
        String var10000 = Config.getInstance().getDiscountsPath()[Config.DiscountsPath.DISCOUNTCODE.getNum()] + "/" + id;
        return var10000 + ".discountcode.json";
    }

    private String generateAuctionProductsFilePath(String id) {
        String var10000 = Config.getInstance().getDiscountsPath()[Config.DiscountsPath.AUCTION.getNum()] + "/" + id;
        return var10000 + ".auction.products.json";
    }

    private String generateDiscountCodeAccountsFilePath(String id) {
        String var10000 = Config.getInstance().getDiscountsPath()[Config.DiscountsPath.DISCOUNTCODE.getNum()] + "/" + id;
        return var10000 + ".discountcode.accounts.json";
    }

    private String generateRequestsFilePath(String id) {
        String var10000 = Config.getInstance().getRequestsPath() + "/" + id;
        return var10000 + ".request.json";
    }

    private String generateCategoryFilePath(String name) {
        String var10000 = Config.getInstance().getCategoriesPath() + "/" + name;
        return var10000 + ".category.json";
    }

    public Account getAccountByName(String name) throws Exception {
        if (accountsByUsername.get(name) != null)
            return accountsByUsername.get(name);
        else
            throw new Exception("this user doesnt exists.");
    }

    public boolean userExistWithUsername(String username) {
        for (String accountUsername : accountsByUsername.keySet()) {
            if (username.equals(accountUsername))
                return true;
        }
        return false;
    }

    public Model.ProductsOrganization.Product getProductById(String id) {
        AtomicReference<Model.ProductsOrganization.Product> temp = new AtomicReference<>();
        productsByName.forEach((k, v) -> {
            if (v != null)
                if (v.getID().equals(id))
                    temp.set(v);
        });
        return temp.get();
    }

    public Model.ProductsOrganization.Product getProductByName(String name) {
        return productsByName.get(name);
    }

    public DiscountCode getDiscountcodeWithId(String id) throws BadRequestException {
        for (Discount discount : discounts) {
            if (discount instanceof DiscountCode && discount.getID().equals(id))
                return (DiscountCode) discount;
        }
        throw new BadRequestException("discount not found");
    }

    public Auction getAuctionWithId(String id) throws BadRequestException {
        for (Discount discount : discounts) {
            if (discount instanceof Auction && discount.getID().equals(id))
                return (Auction) discount;
        }
        throw new BadRequestException("Auction not found");
    }

    public Model.ProductsOrganization.Product getProductWithId(String id) throws BadRequestException {
        for (Model.ProductsOrganization.Product product : productsByName.values()) {
            if (product != null && product.getID().equals(id))
                return product;
        }
        throw new BadRequestException("product with this id hasn't found");
    }

    public boolean productExistWithId(String id) {
        for (Model.ProductsOrganization.Product product : productsByName.values()) {
            if (product != null && product.getID().equals(id))
                return true;
        }
        return false;
    }

    public boolean auctionExistsWithId(int id) {
        for (Discount discount : discounts) {
            if (discount != null && discount instanceof Auction && discount.getID().equals(id))
                return true;
        }
        return false;
    }

    public boolean discountcodeExistsWithId(String id) {
        for (Discount discount : discounts) {
            if (discount instanceof DiscountCode && discount.getID().equals(id))
                return true;
        }
        return false;
    }

    public ArrayList<Request> getAllUnsolvedRequests() {
        return requests;
    }

    public boolean deleteAccount(String username) throws BadRequestException, IOException {
        System.gc();
        Account account = accountsByUsername.get(username);
        for (DiscountCode discountCode : account.getAllDiscountCodes()) {
            deleteAccountFromDiscountCode(discountCode, account.getUsername());
        }
        if (account instanceof Customer)
            return deleteAccount((Customer) account);
        else if (account instanceof Manager)
            return deleteAccount((Manager) account);
        else if (account instanceof Seller)
            return deleteAccount((Seller) account);
        else
            throw new BadRequestException("Could not find the username.", new Throwable("Not such username found in Data Center"));

    }

    private void deleteAccountFromDiscountCode(DiscountCode discountCode, String username) {
        System.gc();
        discountCode.getAllAllowedAccounts().remove(username);
    }

    private boolean deleteAccount(Customer customer) throws IOException {
        System.gc();
        Files.delete(Paths.get(generateUserFilePath(customer.getUsername(), Config.AccountsPath.CUSTOMER.getNum(), "customer")));
        customer.getActiveRequestsId().forEach(this::deleteRequestWithId);
        System.gc();
        return accountsByUsername.remove(customer.getUsername(), customer);
    }

    private boolean deleteAccount(Seller seller) throws IOException, BadRequestException {
        System.gc();
        Files.delete(Paths.get(generateUserFilePath(seller.getUsername(), Config.AccountsPath.SELLER.getNum(), "seller")));
        seller.getActiveRequestsId().forEach(this::deleteRequestWithId);
        for (Model.ProductsOrganization.Product product : seller.getAllProducts()) {
            productsByName.remove(product.getName());
        }
        for (String s : seller.getAuctionsId()) {
            deleteAuctionWithId(s);
        }
        for (String integer : seller.getActiveRequestsId()) {
            try {
                getRequestWithId(integer.toString()).deleteRequest();
            } catch (Exception exception) {
            }
        }
        seller.getAuctionsId().forEach(this::deleteAuctionWithId);
        return accountsByUsername.remove(seller.getUsername(), seller);
    }

    public void deleteAuctionWithId(String id) {
        System.gc();
        try {
            discounts.remove(getAuctionWithId(id));
            File file = new File(generateAuctionFilePath(id));
            file.delete();
            file = new File(generateAuctionProductsFilePath(id));
            file.delete();
        } catch (BadRequestException ignored) {
        }
    }


    private boolean deleteAccount(Manager manager) throws IOException {
        System.gc();
        Files.delete(Paths.get(generateUserFilePath(manager.getUsername(), Config.AccountsPath.MANAGER.getNum(), "manager")));
        return accountsByUsername.remove(manager.getUsername(), manager);
    }

    public boolean deleteProduct(Model.ProductsOrganization.Product product) throws Exception {
        System.gc();
        ((Seller) getAccountByName(product.getSeller())).getAllProducts().remove(product);
        saveAccount(getAccountByName(product.getSeller()));
        productsByName.remove(product.getName(), product);
        for (String s : ((Seller) getAccountByName(product.getSeller())).getAuctionsId()) {
            getAuctionWithId(s).removeProduct(product);
        }
        return true;
    }


    public boolean deleteDiscountCode(DiscountCode discountCode) throws IOException {
        System.gc();
        for (Account account : discountCode.getAllAllowedAccounts()) {
            account.getAllDiscountCodes().remove(discountCode);
            saveAccount(account);
        }
        File file = new File(generateDiscountCodeAccountsFilePath(discountCode.getID()));
        return file.delete() && discounts.remove(discountCode);
    }

    public boolean deleteCategory(Category category) throws IOException {
        System.gc();
        for (Model.ProductsOrganization.Product product : category.getAllProductsInside().values()) {
            product.setParent(category.getParent());
        }
        for (Category value : category.getAllSubCategories().values()) {
            value.setParent(category.getParent());
            saveCategory(value);
        }
        File file = new File(generateCategoryFilePath(category.getName()));
        return file.delete() && this.categories.remove(category.getName(), category);
    }


    public ArrayList<String> getAllAccountsInfo() {
        ArrayList<String> strings = new ArrayList<>();
        for (Account account : accountsByUsername.values()) {
            strings.add(account.getUsername() + "\t" + account.getClass().getName());
        }
        return strings;
    }

    public boolean doesUsernameExist(String username) {
        for (String accountUsername : this.accountsByUsername.keySet()) {
            if (username.equals(accountUsername))
                return true;
        }
        return false;
    }

    public boolean doesProductExist(String productId) {
        for (Model.ProductsOrganization.Product product : productsByName.values()) {
            if (product.getID().equals(productId))
                return true;
        }
        return false;
    }


    public int getLastDiscountId() {
        return discounts.size();
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() {
        ArrayList<DiscountCode> allDiscountCodes = new ArrayList<DiscountCode>();
        for (Discount discount : discounts) {
            if (discount instanceof DiscountCode)
                allDiscountCodes.add((DiscountCode) discount);
        }
        return allDiscountCodes;
    }

    public DiscountCode getDiscountcodeWithCode(String code) throws BadRequestException {
        for (Discount discount : discounts) {
            if (discount != null && discount instanceof DiscountCode)
                if (((DiscountCode) discount).getCode().equals(code))
                    return (DiscountCode) discount;
        }
        throw new BadRequestException("discount not found");
    }

    public ArrayList<Model.ProductsOrganization.Product> getAllProductsObject() {
        ArrayList<Product> var = new ArrayList<>();
        var.addAll(productsByName.values());
        for (int i = 0; i < var.size(); i++) {
            if (var.get(i).getStatus().equals(Status.CONSTRUCTING) || var.get(i).getStatus().equals(Status.EDITING)) {
                var.remove(var.get(i));
                i--;
            }
        }
        return var;
    }

    public ArrayList<Product> getAllProductsWithNoCondition() {
        ArrayList<Product> tmp = new ArrayList<>();
        tmp.addAll(productsByName.values());
        return tmp;
    }

    public String getNewDiscountID() {
        ArrayList<String> args = new ArrayList<>();
        for (Discount discount : discounts) {
            args.add(discount.getID());
        }
        String[] result = {};
        return RandomIDGenerator.discountIdGenerator((String[]) args.toArray(result));
    }

    public String getNewProductID() {
        ArrayList<String> args = new ArrayList<>();
        for (Product product : productsByName.values()) {
            args.add(product.getID());
        }
        String[] result = {};
        return RandomIDGenerator.generateProductID((String[]) args.toArray(result));
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> var = new ArrayList<>();
        var.addAll(categories.values());
        return var;
    }

    public Date getDate() {
        TimeInfo timeInfo = null;
        final String TIME_SERVER = "time-a.nist.gov";
        try {
            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
            timeInfo = timeClient.getTime(inetAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long returnTime = timeInfo.getReturnTime();
        return new Date(returnTime);
    }

    public Request getRequestWithId(String commandDetail) throws Exception {
        for (Request request : getAllUnsolvedRequests()) {
            if (request.getId().equals(commandDetail))
                return request;
        }
        throw new Exception("Request not found");
    }

    public String requestIDGenerator(CanRequest account) {
        return RandomIDGenerator.generateRequestID();
    }


    public Category getCategoryWithName(String categoryName) throws BadRequestException {
        for (String s : categories.keySet()) {
            if (s.equals(categoryName))
                return categories.get(s);
        }
        throw new BadRequestException("there is no category with such name");
    }

    public void saveProduct(Product value) throws IOException {
        saveAccount(accountsByUsername.get(value.getSeller()));
    }
}

