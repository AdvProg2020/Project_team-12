package Controller.CommandProcessors;

import Controller.DataBase.DataCenter;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Discount.Auction;
import Model.Discount.DiscountCode;
import Model.Log.PurchaseLog;
import Model.Log.SellLog;
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.Score;
import Model.Request.*;
import View.Exceptions.CustomerExceptions;
import View.Exceptions.ProductExceptions;
import org.apache.commons.net.telnet.EchoOptionHandler;

import javax.crypto.spec.DESedeKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ProfileCP extends CommandProcessor {
    private static CommandProcessor Instance;

    protected ProfileCP() {
        super(MainMenuCP.getInstance());
    }

    public static CommandProcessor getInstance() {
        if (Instance == null)
            Instance = new ProfileCP();
        return Instance;
    }

    public String getCompanyInfo() {
        return ((Seller) getLoggedInAccount()).getCompanyInformation();
    }

    public ArrayList<SellLog> getSalesHistory() {
        return ((Seller) getLoggedInAccount()).getSellLogs();
    }

    public ArrayList<Product> getAllSellerProducts() {
        return ((Seller) getLoggedInAccount()).getAllProducts();
    }

    public Product getProductById(String id) throws Exception {
        for (Product product : ((Seller) getLoggedInAccount()).getAllProducts()) {
            if (product.getID().equals(id))
                return product;
        }
        throw new ProductExceptions("there is no product with this id");
    }

    public ArrayList<Auction> getAllSellerAuctions() {
        ArrayList<Auction> allAuctions = new ArrayList<Auction>();
        for (String auctionId : ((Seller) getLoggedInAccount()).getAuctionsId()) {
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
        return dataCenter.getAuctionWithId(id);
    }

    public void removeProductWithId(String id) throws Exception {
        if (!dataCenter.doesProductExist(id))
            throw new ProductExceptions("there is no product with this id");
        Product product = getProductById(id);
        if (product.getBuyers().size() == 1) {
            if (!dataCenter.deleteProduct(dataCenter.getProductById(id)))
                throw new ProductExceptions("can't delete product");
        }

    }

    public String getSellerBalance() {
        return Double.toString(((Seller) getLoggedInAccount()).getCredit());
    }

    public ArrayList<PurchaseLog> getCustomerOrdersHistory() {
        return ((Customer) getLoggedInAccount()).getBuyLogs();
    }

    public PurchaseLog getOrderById(String orderId) throws Exception {
        for (PurchaseLog order : getCustomerOrdersHistory()) {
            if (order.getId().equals(orderId))
                return order;
        }
        throw new CustomerExceptions("there is no order with this id");
    }

    public String getCustomerBalance() {
        return Double.toString(((Customer) getLoggedInAccount()).getCredit());
    }

    public ArrayList<DiscountCode> getCustomerDiscountCodes() {
        return ((Customer) getLoggedInAccount()).getAllDiscountCodes();
    }

    public void rate(String productId, String score) throws Exception {
        if (!dataCenter.doesProductExist(productId))
            throw new ProductExceptions("there is no product with this id");
        Product product = dataCenter.getProductById(productId);
        Score newScore = new Score(Double.parseDouble(score));
        product.submitScore(newScore);
        dataCenter.saveAccount(dataCenter.getAccountByName(product.getSeller()));
    }

    public void addProduct(String name, String brand, String price, String remainingItems, String description, HashMap<String, String> specifications) throws Exception {
        Seller seller = (Seller) getLoggedInAccount();
        Product product = new Product(name, seller.getUsername(), Integer.parseInt(remainingItems),
                Double.parseDouble(price), brand, specifications, description, dataCenter.getNewProductID(), dataCenter.getDate());
        Request request = new ProductRequest(seller.getUsername(),
                dataCenter.requestIDGenerator(seller), false, product.getID());
        seller.addProduct(product);
        seller.addRequest(request);
        dataCenter.addProduct(product);
        dataCenter.addRequest(request);
        dataCenter.saveAccount(seller);
        dataCenter.saveRequest(request);
    }

    public ArrayList<Category> getCategories() {
        return dataCenter.getCategories();
    }

    public void editProduct(String id, String name, String brand, String price, String remainingItems, String description, HashMap<String, String> specifications) throws Exception {
        Seller seller = (Seller) getLoggedInAccount();
        Product product = dataCenter.getProductById(id);
        product.setName(name);
        product.setBrand(brand);
        product.setPrice(Double.parseDouble(price));
        product.setRemainingItems(Integer.parseInt(remainingItems));
        product.setDescription(description);
        product.setSpecs(specifications);
        Request request = new ProductRequest(seller.getUsername(),
                dataCenter.requestIDGenerator(seller), false, product.getID());
        seller.addProduct(product);
        seller.addRequest(request);
        dataCenter.addProduct(product);
        dataCenter.addRequest(request);
        dataCenter.saveAccount(seller);
        dataCenter.saveRequest(request);
    }

    public void addAuction(String startingDate, String lastDate, String percent, ArrayList<String> products) throws Exception {
        Seller seller = (Seller) getLoggedInAccount();
        DateFormat format = new SimpleDateFormat("yy/mm/dd", Locale.ENGLISH);
        ArrayList<Product> auctionProducts = new ArrayList<Product>();
        for (String productId : products) {
            auctionProducts.add(dataCenter.getProductById(productId));
        }
        Auction auction = new Auction(format.parse(startingDate), format.parse(lastDate), Double.parseDouble(percent), dataCenter.getNewDiscountID(), auctionProducts, seller.getUsername());
        Request request = new AuctionRequest(seller.getUsername(), dataCenter.requestIDGenerator(seller), false, auction.getID());
        seller.addAuctionId(auction.getID());
        seller.addRequest(request);
        dataCenter.addRequest(request);
        dataCenter.addDiscount(auction);
        dataCenter.saveDiscount(auction);
        dataCenter.saveAccount(seller);
        dataCenter.saveRequest(request);
    }

    public void editAuction(String id, String startingDate, String lastDate, String percent, ArrayList<String> products) throws Exception {
        Seller seller = (Seller) getLoggedInAccount();
        DateFormat format = new SimpleDateFormat("yy/mm/dd", Locale.ENGLISH);
        ArrayList<Product> auctionProducts = new ArrayList<Product>();
        for (String productId : products) {
            auctionProducts.add(dataCenter.getProductById(productId));
        }
        Auction auction = dataCenter.getAuctionWithId(id);
        auction.setStart(format.parse(startingDate));
        auction.setEnd(format.parse(lastDate));
        auction.setPercent(Double.parseDouble(percent));
        auction.setAllProducts(auctionProducts);
        Request request = new AuctionRequest(seller.getUsername(), dataCenter.requestIDGenerator(seller), false, auction.getID());
        seller.addAuctionId(auction.getID());
        seller.addRequest(request);
        dataCenter.addRequest(request);
        dataCenter.addDiscount(auction);
        dataCenter.saveDiscount(auction);
        dataCenter.saveAccount(seller);
        dataCenter.saveRequest(request);
    }

    public void createManagerAccount(String username, String password, String name, String lastName, String phoneNumber, String emailAddress) throws Exception {
        Account manager = new Manager(username, name, lastName, emailAddress, phoneNumber, password);
        dataCenter.saveAccount(manager);
    }

    public ArrayList<Request> getRequests() {
        return dataCenter.getAllUnsolvedRequests();
    }

    public String showRequestDetail(String commandDetail) throws Exception {
        return dataCenter.getRequestWithId(commandDetail).showDetails();
    }

    public void acceptRequest(String commandDetail) throws Exception {
        dataCenter.getRequestWithId(commandDetail).acceptRequest();
    }

    public void declineRequest(String commandDetail) throws Exception {
        ((NoCauseDecline) dataCenter.getRequestWithId(commandDetail)).declineRequest();
    }

    public void declineRequest(String commandDetail, String cause) throws Exception {
        ((DeclineHasCause) dataCenter.getRequestWithId(commandDetail)).declineRequest(cause);
    }

    public boolean checkRequestType(String commandDetail) throws Exception {
        return dataCenter.getRequestWithId(commandDetail) instanceof DeclineHasCause;
    }

    public void addCategory(String categoryName, String parentCategoryName, ArrayList<String> specifications) throws Exception {
        Category parentCategory = null;
        for (Category category : dataCenter.getCategories()) {
            if (category.getName().equals(parentCategoryName))
                parentCategory = category;
        }
        if (parentCategory == null)
            throw new CustomerExceptions("parent category doesn't exist");
        Category newCategory = new Category(categoryName, specifications, parentCategory);
        dataCenter.saveCategory(newCategory);
    }

    public boolean doesCategoryExistsWithThisName(String name) {
        for (Category category : dataCenter.getCategories()) {
            if (category.getName().equals(name))
                return true;
        }
        return false;
    }

    public void removeCategory(String categoryName) throws Exception {
        if (!doesCategoryExistsWithThisName(categoryName))
            throw new CustomerExceptions("category with this name doesn't exist");
        Category categoryToBeDeleted = null;
        for (Category category : dataCenter.getCategories()) {
            if (category.getName().equals(categoryName))
                categoryToBeDeleted = category;
        }
        dataCenter.deleteCategory(categoryToBeDeleted);
    }
}
