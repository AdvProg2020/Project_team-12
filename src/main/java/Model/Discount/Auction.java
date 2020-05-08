package Model.Discount;

import Model.ProductsOrganization.Product;
import Model.Status;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;

public class Auction extends Discount {
    @Expose(serialize = false,deserialize = false)
    private ArrayList<Product> allIncludedProducts;
    @Expose
    private Status status;

    public Auction(Date start, Date end, double percent, int id, ArrayList<Product> allIncludedProducts, Status status) {
        super(start, end, percent, id);
        this.allIncludedProducts = allIncludedProducts;
        this.status = status;
    }



    public ArrayList<Product> getAllIncludedProducts() {
        return allIncludedProducts;
    }

    public Status getStatus() {
        return status;
    }

    public void setAllIncludedProducts(ArrayList<Product> allIncludedProducts) {
        this.allIncludedProducts = allIncludedProducts;
    }
}
