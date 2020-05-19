package Model.Discount;

import Model.ProductsOrganization.Product;
import Model.Status;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Auction extends Discount {
    @Expose(serialize = false,deserialize = false)
    private ArrayList<Product> allProducts = new ArrayList<>();
    @Expose
    private Status status = Status.CONSTRUCTING;
    @Expose
    private String seller;

    public Auction(Date start, Date end, double percent, String ID, ArrayList<Product> allProducts, String seller) {
        super(start, end, percent, ID);
        this.allProducts = allProducts;
        this.seller = seller;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void removeProduct(Product product) {
        allProducts.remove(product);
    }

    @Override
    public double calculatePrice(Double amount) {
        return amount - (amount*percent/100);
    }

    @Override
    public String toString() {
        return "Auction{" +
                "allProducts=" + allProducts +
                ", status=" + status +
                ", seller='" + seller + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", percent=" + percent +
                ", ID='" + ID + '\'' +
                '}';
    }
}
