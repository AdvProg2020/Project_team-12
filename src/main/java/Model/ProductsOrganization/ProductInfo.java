package Model.ProductsOrganization;

import com.google.gson.annotations.Expose;

public class ProductInfo {
    @Expose
    private int quantity;
    @Expose
    private double price;
    @Expose
    private String PName;
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
}