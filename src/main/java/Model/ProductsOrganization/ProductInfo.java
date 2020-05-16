package Model.ProductsOrganization;

import Model.Status;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Objects;

public class ProductInfo {

    @Expose
    private String SellerUserName;
    @Expose
    private int quantity;
    @Expose
    private double price;
    @Expose
    private String PName;
    @Expose
    private Status status;
    @Expose
    private ArrayList<String> buyers = new ArrayList<>();
    @Expose(serialize = false, deserialize = false)
    private Product product;
    @Expose
    private Double averageScore;

    public ProductInfo(String sellerUserName, int quantity, double price, Product product) {
        SellerUserName = sellerUserName;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.PName = product.getName();
        this.averageScore = Double.valueOf(0);
        for (Score score : product.getAllSubmittedScores()) {
            averageScore += score.getScore();
        }
        averageScore /= product.getAllSubmittedScores().size();
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

    public int getId() {
        return this.product.getId();
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ArrayList<String> getBuyers() {
        return buyers;
    }

    public void setBuyers(ArrayList<String> buyers) {
        this.buyers = buyers;
    }

    @Override
    public String toString() {
        return "Product name:" + PName
                + "  Price:" + price
                +"  Description: " + product.getDescription();
    }

    public String getSellerUserName() {
        return SellerUserName;
    }

    public void setSellerUserName(String sellerUserName) {
        SellerUserName = sellerUserName;
    }

    public String  getQuantityString() {
        return "Seller:"+getSellerUserName()+"  Quantity:"+quantity;
    }
}