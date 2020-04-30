package Model.Account;

import Model.Log.SellLog;
import Model.ProductsOrganization.Product;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends Account {
    @Expose(serialize = true)
    private String companyInformation;
    @Expose(serialize = true)
    private ArrayList<SellLog> sellLogs = new ArrayList<>();
    @Expose(serialize = false,deserialize = false)
    private HashMap<Product, ProductInfo> allProducts = new HashMap();
    public Seller(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String  password, String companyInformation) {
        super(username, firstName, lastName, emailAddress, phoneNumber, password);
        this.companyInformation = companyInformation;
    }

    @Override
    public void writeInfoInFile() {

    }

    @Override
    public String toString() {
        return null;
    }
}
class ProductInfo {
    int quantity;
    double price;

    public ProductInfo(int quantity, double price) {
        this.quantity = quantity;
        this.price = price;
    }
}
