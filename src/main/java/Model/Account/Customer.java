package Model.Account;

import Model.Log.PurchaseLog;

import Model.ProductsOrganization.Cart;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Objects;

public class Customer extends Account implements CanRequest{
    @Expose(serialize = false,deserialize = false)
    private Cart cart;
    @Expose
    private ArrayList<PurchaseLog> buyLogs = new ArrayList<>();
    @Expose
    private ArrayList<Integer> activeRequestsId = new ArrayList<>();
    @Expose
    private ArrayList<String> solvedRequests = new ArrayList<>();
    public Customer(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {
        super(username, firstName, lastName, emailAddress, phoneNumber, password);
    }

    public boolean hasBoughtProduct(int productId){return true;}

    @Override
    public void writeInfoInFile() {

    }



    public void deleteRequestWithId(int id){
        activeRequestsId.remove(id);
    }
    public ArrayList<String> getSolvedRequests() {
        return solvedRequests;
    }

    public void setSolvedRequests(ArrayList<String> solvedRequests) {
        this.solvedRequests = solvedRequests;
    }
}