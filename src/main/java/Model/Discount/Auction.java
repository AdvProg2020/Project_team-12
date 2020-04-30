package Model.Discount;

import Model.ProductsOrganization.Product;
import Model.Status;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;

public class Auction extends Discount {
    @Expose
    private int id;
    @Expose(serialize = false,deserialize = false)
    private ArrayList<Product> allIncludedProducts;
    @Expose
    private Status status;

    public Auction(int id, ArrayList<Product> allIncludedProducts, Status status,Date start, Date end, double percent) {
        super(start, end, percent);
        this.id = id;
        this.allIncludedProducts = allIncludedProducts;
        this.status = status;
    }

}
