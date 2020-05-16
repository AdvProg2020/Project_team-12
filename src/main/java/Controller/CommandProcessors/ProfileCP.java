package Controller.CommandProcessors;

import Controller.DataBase.DataCenter;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Seller;
import Model.Discount.Auction;
import Model.Discount.DiscountCode;
import Model.Log.PurchaseLog;
import Model.Log.SellLog;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.ProductInfo;
import Model.ProductsOrganization.Score;
import View.Exceptions.CustomerExceptions;
import View.Exceptions.ProductExceptions;

import java.util.ArrayList;

public class ProfileCP extends CommandProcessor {
    private static CommandProcessor Instance;
    private static Account loggedInAccount = CommandProcessor.getLoggedInAccount();
    private DataCenter dataCenter = DataCenter.getInstance();

    protected ProfileCP() {
        super(MainMenuCP.getInstance());
    }

    public static CommandProcessor getInstance() {
        if (Instance == null)
            Instance = new ProfileCP();
        return Instance;
    }

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
}
