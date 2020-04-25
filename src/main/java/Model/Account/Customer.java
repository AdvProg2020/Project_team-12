package Model.Account;

import Model.Log.PurchaseLog;

import Model.ProductsOrganization.Cart;

import java.util.ArrayList;

public class Customer extends Account {
    private Cart cart;
    private ArrayList<PurchaseLog> buyLogs = new ArrayList<>();

    public Customer(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {
        super(username, firstName, lastName, emailAddress, phoneNumber, password);
    }

    public boolean hasBoughtProduct(int productId){return true;}

    @Override
    public void writeInfoInFile() {

    }

    @Override
    public String toString() {
        return null;
    }
}