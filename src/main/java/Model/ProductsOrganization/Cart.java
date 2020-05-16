package Model.ProductsOrganization;

import Model.Account.Customer;
import Model.Account.Seller;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    private ArrayList<Product> products;
    private HashMap<Product, Seller> traders;
    private Customer owner;
    private String receiverInfo;

    public Cart(Customer owner) {
        this.owner = owner;
        products = new ArrayList<>();
        traders = new HashMap<Product, Seller>();
    }

    public Double getPayAmount() {
        Double price = Double.valueOf(0);
        for (Product product : this.products) {
            price += product.getPrice();
        }
        return price;
    }

    public void setReceiverInfo(String receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getReceiverInfo() {
        return receiverInfo;
    }

    public void restart() {
        this.products.clear();
        this.owner = null;
        this.receiverInfo = null;
    }

    public HashMap<Product, Seller> getTraders() {
        return traders;
    }

    public void addToTraders(Product product,Seller seller){
        traders.put(product, seller);
    }

    public void addProduct(Product product) {
    }
}