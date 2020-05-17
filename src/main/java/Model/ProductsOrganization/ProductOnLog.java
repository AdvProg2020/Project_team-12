package Model.ProductsOrganization;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class ProductOnLog {
    @Expose
    private final String name;
    @Expose
    private final double price;
    @Expose
    private final String sellerOrBuyer;
    @Expose
    private final int quantity;
    public ProductOnLog(String name, Double price, String seller, int quantity) {
        this.name = name;
        this.price = price;
        this.sellerOrBuyer = seller;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOnLog that = (ProductOnLog) o;
        return price == that.price &&
                name.equals(that.name) &&
                sellerOrBuyer.equals(that.sellerOrBuyer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, sellerOrBuyer);
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
