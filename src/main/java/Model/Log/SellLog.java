package Model.Log;

import Model.ProductsOrganization.ProductOnLog;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;

public class SellLog extends Log {
    @Expose
    private int receivedCredit;
    @Expose
    private int decreasedPriceAtAuction;
    @Expose
    private ArrayList<ProductOnLog> allSoldProducts;
    /*@Expose(serialize = true)
    private Status status;*///I dont know what you want this for but if its necessary, uncomment it.

    public SellLog(int id, Date date) {
        super(id, date);
    }

    @Override
    public String toString() {
        return "SellLog{}";
    }

    public void setAllSoldProducts(ArrayList<ProductOnLog> allSoldProducts) {
        this.allSoldProducts = allSoldProducts;
    }

    public void setReceivedCredit(int receivedCredit) {
        this.receivedCredit = receivedCredit;
    }

    public void setDecreasedPriceAtAuction(int decreasedPriceAtAuction) {
        this.decreasedPriceAtAuction = decreasedPriceAtAuction;
    }

    public int getReceivedCredit() {
        return receivedCredit;
    }

    public int getDecreasedPriceAtAuction() {
        return decreasedPriceAtAuction;
    }

    public ArrayList<ProductOnLog> getAllSoldProducts() {
        return allSoldProducts;
    }

    private enum Status {SENT, TO_BE_SENT}
}
