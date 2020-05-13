package Model.ProductsOrganization;

import Model.Status;
import com.google.gson.annotations.Expose;

import java.util.Objects;

public class ProductInfo {
    @Expose
    private int quantity;
    @Expose
    private double price;
    @Expose
    private String PName;
    @Expose
    private Status status;
    @Expose(serialize = false,deserialize = false)
    private Product product;

    public ProductInfo(int quantity, double price, Product product) {
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.PName = product.getName();
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getPName() {
        return PName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInfo that = (ProductInfo) o;
        return quantity == that.quantity &&
                Double.compare(that.price, price) == 0 &&
                PName.equals(that.PName) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, price, PName, product);
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}