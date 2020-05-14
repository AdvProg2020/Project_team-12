package Model.Discount;

import Model.ProductsOrganization.Product;
import Model.Status;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Auction auction = (Auction) o;
        return Objects.equals(allIncludedProducts, auction.allIncludedProducts) &&
                status == auction.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), allIncludedProducts, status);
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

    public void setStatus(Status status) {
        this.status = status;
    }
}
