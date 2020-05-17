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
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Product;
import Model.Request.*;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
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

    public static String getNewSellID() {
        return "1";
    }

    public static String getNewPurchaseID() {
        return "1";
    }

    private void initProducts() {
        productsByName = new HashMap<>();
        for (Account value : accountsByUsername.values()) {
            if (value instanceof Seller)
                ((Seller) value).getAllProducts().forEach(this::addProduct);
        }
    }


    public static DataCenter getInstance() {
        if (Instance == null) {
            Instance = new DataCenter();
        }
        return Instance;
    }

    private void initCategories() {
        categories = new HashMap<>();
        JsonFileReader reader = new JsonFileReader();
        File file = new File(Config.getInstance().getCategoriesPath());
        if (!file.exists())
            file.mkdir();
        File[] files = file.listFiles();
        if(files!=null)
        Arrays.stream(files).map((file1) -> {
            try {
                return reader.read(file1, Category.class);
            } catch (FileNotFoundException e) {
                return null;
            }
        }).forEach(this::addCategory);
    }

    private void addCategory(Category category)  {
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

    private void initCategory(String category)  {
        File file = new File(generateCategoryFilePath(category));
        JsonFileReader reader = new JsonFileReader();
        try {
            addCategory(reader.read(file, Category.class));
        } catch (FileNotFoundException e) {
            addCategory(new Category(category,new ArrayList<String>(),null));
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

    public void deleteRequestWithId(int id) {
        for (Request request : requests) {
            if (request.getId() == id) {
                requests.remove(request);
                File file = new File(generateRequestsFilePath(id));
                file.delete();
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

    {/*private void initProducts() {
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
                    String var100 = temp.getCategoryName();
                    if (var100 != null && var100 != "") {
                        temp.setParent(categories.get(var100));
                        categories.get(var100).getAllProductsInside().put(temp.getName(), temp);
                    }
                    return temp;
                } catch (FileNotFoundException var4) {
                    return null;
                }
            }).forEach(this::addProduct);
        }
    }*/}

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
        writer.write(seller, generateUserFilePath(seller.getUsername(), Config.AccountsPath.SELLER.getNum(), "seller"), Account.class);
    }

    public void saveAccount(Manager manager) throws IOException {
        JsonFileWriter writer = new JsonFileWriter(accountRuntimeTypeAdapter);
        writer.write(manager, generateUserFilePath(manager.getUsername(), Config.AccountsPath.MANAGER.getNum(), "manager"), Account.class);
    }


    {  /*public void saveProduct(Product product) throws IOException {
        JsonFileWriter writer = new JsonFileWriter();
        product.setCategoryName(product.getParent().getName());
        writer.write(product, generateProductFilePath(product.getID()));
        if (!productsByName.containsValue(product))
            productsByName.put(product.getName(), product);
    }*/}

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

    {/* private String generateProductFilePath(int id) {
        String var10000 = Config.getInstance().getProductsPath() + "/" + id;
        return var10000 + ".product.json";
    }
*/}
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

    private String generateRequestsFilePath(int id) {
        String var10000 = Config.getInstance().getRequestsPath() + "/" + id;
        return var10000 + ".request.json";
    }

    private String generateCategoryFilePath(String name) {
        String var10000 = Config.getInstance().getCategoriesPath() + "/" + name;
        return var10000 + ".category.json";
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
        discountCode.getAllAllowedAccounts().remove(username);
    }

    private boolean deleteAccount(Customer customer) {
        File file = new File(generateUserFilePath(customer.getUsername(), Config.AccountsPath.CUSTOMER.getNum(), "Customer"));
        customer.getActiveRequestsId().forEach(this::deleteRequestWithId);
        return file.delete() && accountsByUsername.remove(customer.getUsername(), customer);
    }

    private boolean deleteAccount(Seller seller) throws IOException, BadRequestException {
        File file = new File(generateUserFilePath(seller.getUsername(), Config.AccountsPath.MANAGER.getNum(), "Manager"));
        seller.getActiveRequestsId().forEach(this::deleteRequestWithId);
        for (Model.ProductsOrganization.Product product : seller.getAllProducts()) {
            productsByName.remove(product.getName());
        }
        for (String s : seller.getAuctionsId()) {
            deleteAuctionWithId(s);
        }
        seller.getAuctionsId().forEach(this::deleteAuctionWithId);
        return file.delete() && accountsByUsername.remove(seller.getUsername(), seller);
    }

    public void deleteAuctionWithId(String id) {
        try {
            discounts.remove(getAuctionWithId(id));
            File file = new File(generateAuctionFilePath(id));
            file.delete();
            file = new File(generateAuctionProductsFilePath(id));
            file.delete();
        } catch (BadRequestException ignored) {
        }
    }



    private boolean deleteAccount(Manager manager) {
        File file = new File(generateUserFilePath(manager.getUsername(), Config.AccountsPath.SELLER.getNum(), "Seller"));
        return file.delete() && accountsByUsername.remove(manager.getUsername(), manager);
    }

    public boolean deleteProduct(Model.ProductsOrganization.Product product) throws IOException, BadRequestException {
        ((Seller)getAccountByName(product.getSeller())).getAllProducts().remove(product);
        for (String s : ((Seller) getAccountByName(product.getSeller())).getAuctionsId()) {
            getAuctionWithId(s).removeProduct(product);
        }
        return true;
    }


    public boolean deleteDiscountCode(DiscountCode discountCode) throws IOException {
        for (Account account : discountCode.getAllAllowedAccounts()) {
            account.getAllDiscountCodes().remove(discountCode);
            saveAccount(account);
        }
        File file = new File(generateDiscountCodeAccountsFilePath(discountCode.getID()));
        return file.delete() && discounts.remove(discountCode);
    }

    public boolean deleteCategory(Category category) throws IOException {
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
        return (ArrayList<Model.ProductsOrganization.Product>) productsByName.values();
    }
    public Set<String> getAllProducts() {
        return  productsByName.keySet();
    }

    public String getNewDiscountID() {
        //TODO: should be written
        return null;
    }

    public ArrayList<Category> getCategories() {
        return (ArrayList<Category>) categories.values();
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
}

