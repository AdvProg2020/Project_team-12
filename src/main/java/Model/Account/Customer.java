package Model.Account;

import Model.Log.PurchaseLog;

import Model.ProductsOrganization.Cart;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Objects;

public class Customer extends Account {
    @Expose(serialize = false,deserialize = false)
    private Cart cart;
    @Expose
    private ArrayList<PurchaseLog> buyLogs = new ArrayList<>();

    public Customer(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {
        super(username, firstName, lastName, emailAddress, phoneNumber, password);
    }

    public boolean hasBoughtProduct(int productId){return true;}

    @Override
    public void writeInfoInFile() {

    }


}