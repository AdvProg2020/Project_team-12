package Model.Log;

import Controller.DataBase.DataCenter;
import Model.Account.Seller;
import Model.ProductsOrganization.ProductOnLog;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;

public class SellLog extends Log {
    @Expose
    private double receivedCredit;
    @Expose
    private double decreasedPriceAtAuction;
    @Expose
    private ArrayList<ProductOnLog> allSoldProducts;
    @Expose
    private SellStatus status = SellStatus.TO_BE_SENT;

    public SellLog(Date date, Seller seller, double receivedCredit, double decreasedPriceAtAuction, ArrayList<ProductOnLog> allSoldProducts) {
        super(date, DataCenter.getNewSellID(seller.getUsername()));
        this.receivedCredit = receivedCredit;
        this.decreasedPriceAtAuction = decreasedPriceAtAuction;
        this.allSoldProducts = allSoldProducts;
    }

    public double getReceivedCredit() {
        return receivedCredit;
    }

    public void setReceivedCredit(double receivedCredit) {
        this.receivedCredit = receivedCredit;
    }

    public double getDecreasedPriceAtAuction() {
        return decreasedPriceAtAuction;
    }

    public void setDecreasedPriceAtAuction(double decreasedPriceAtAuction) {
        this.decreasedPriceAtAuction = decreasedPriceAtAuction;
    }

    public ArrayList<ProductOnLog> getAllSoldProducts() {
        return allSoldProducts;
    }

    public void setAllSoldProducts(ArrayList<ProductOnLog> allSoldProducts) {
        this.allSoldProducts = allSoldProducts;
    }

    public SellStatus getStatus() {
        return status;
    }

    public void setStatus(SellStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SellLog{" +
                "receivedCredit=" + receivedCredit +
                ", decreasedPriceAtAuction=" + decreasedPriceAtAuction +
                ", allSoldProducts=" + allSoldProducts +
                ", status=" + status +
                '}';
    }
}
