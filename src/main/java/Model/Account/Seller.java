package Model.Account;

import Controller.DataBase.DataCenter;
import Model.Log.SellLog;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.ProductInfo;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends Account {
    @Expose
    private String companyInformation;
    @Expose
    private ArrayList<SellLog> sellLogs = new ArrayList<>();
    @Expose
    private ArrayList<ProductInfo> allProducts = new ArrayList<>();
    public Seller(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String  password, String companyInformation) {
        super(username, firstName, lastName, emailAddress, phoneNumber, password);
        this.companyInformation = companyInformation;
    }

    @Override
    public void writeInfoInFile() {

    }

    public ArrayList<ProductInfo> getAllProducts() {
        return allProducts;
    }

    @Override
    public String toString() {
        return super.toString() +"Seller{" +
                "companyInformation='" + companyInformation + '\'' +
                ", sellLogs=" + sellLogs +
                ", allProducts=" + allProducts +
                '}';
    }
}

