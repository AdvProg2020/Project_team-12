package Model.Log;

import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.ProductOnLog;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;

public class PurchaseLog extends Log {
    @Expose
    private int payedCredit;
    @Expose
    private int finalPrice;
    @Expose
    private ArrayList<ProductOnLog> allPurchasedProducts;

    public PurchaseLog(int id, Date date) {
        super(id, date);
    }

    private enum Status {DELIVERED, TO_BE_DELIVERED}
    private Status status;


    public boolean hasBoughtProduct(int productId){return true;}

    @Override
    public String toString() {
        return "PurchaseLog{}";
    }
}
