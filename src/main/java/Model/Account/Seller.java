package Model.Account;

import Model.Log.Log;
import Model.Log.SellLog;
import Model.ProductsOrganization.Product;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Seller extends Account {
    @Expose(serialize = true)
    private String companyInformation;
    @Expose(serialize = true)
    private ArrayList<SellLog> sellLogs = new ArrayList<>();
    @Expose(serialize = false,deserialize = false)
    private ArrayList<Product> allProducts = new ArrayList<>();
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
