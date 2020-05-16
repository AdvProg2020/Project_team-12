package Model.Log;

import Model.ProductsOrganization.ProductOnLog;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;

public class PurchaseLog extends Log {
    @Expose
    private double payedCredit;
    @Expose
    private double finalPrice;
    @Expose
    private ArrayList<ProductOnLog> allPurchasedProducts;
    @Expose
    private PurchaseStatus status = PurchaseStatus.TO_BE_DELIVERED;

    public PurchaseLog(Date date, double payedCredit, double finalPrice, ArrayList<ProductOnLog> allPurchasedProducts) {
        super(date);
        this.payedCredit = payedCredit;
        this.finalPrice = finalPrice;
        this.allPurchasedProducts = allPurchasedProducts;

    }

    public double getPayedCredit() {
        return payedCredit;
    }

    public void setPayedCredit(double payedCredit) {
        this.payedCredit = payedCredit;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public ArrayList<ProductOnLog> getAllPurchasedProducts() {
        return allPurchasedProducts;
    }

    public void setAllPurchasedProducts(ArrayList<ProductOnLog> allPurchasedProducts) {
        this.allPurchasedProducts = allPurchasedProducts;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PurchaseLog{}";
    }
}