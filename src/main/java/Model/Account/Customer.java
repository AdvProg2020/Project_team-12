package Model.Account;

import Model.Discount.DiscountCode;
import Model.Log.PurchaseLog;

import Model.ProductsOrganization.Cart;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Customer extends Account implements CanRequest{
    @Expose(serialize = false,deserialize = false)
    private Cart cart;
    @Expose
    private ArrayList<PurchaseLog> buyLogs = new ArrayList<>();
    @Expose
    private ArrayList<String> activeRequestsId = new ArrayList<String>();
    @Expose
    private ArrayList<String> solvedRequests = new ArrayList<>();
    public Customer(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {
        super(username, firstName, lastName, emailAddress, phoneNumber, password);
    }

    public boolean hasBoughtProduct(int productId){return true;}

    @Override
    public void writeInfoInFile() {

    }



    public void deleteRequestWithId(String id){
        activeRequestsId.remove(id);
    }
    public ArrayList<String> getSolvedRequests() {
        return solvedRequests;
    }

    public void setSolvedRequests(ArrayList<String> solvedRequests) {
        this.solvedRequests = solvedRequests;
    }

    public ArrayList<String> getActiveRequestsId() {
        return activeRequestsId;
    }

    public void setActiveRequestsId(ArrayList<String> activeRequestsId) {
        this.activeRequestsId = activeRequestsId;
    }

    public ArrayList<PurchaseLog> getBuyLogs() {
        return buyLogs;
    }

    public double getPaymentAmountWithDiscountCode(Double amount,String discountCodeID) throws Exception {
        for (DiscountCode discountCode : this.getAllDiscountCodes()) {
            if (discountCode.getID().equals(discountCodeID))
                return discountCode.calculatePrice(amount);
        }
        throw new Exception("DiscountCode has not found.");
    }

    public boolean hasDiscountCode(String discountCodeId) {
        for (DiscountCode discountCode : getAllDiscountCodes()) {
            if (discountCode.getID().equals(discountCodeId))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cart=" + cart +
                ", buyLogs=" + buyLogs +
                ", activeRequestsId=" + activeRequestsId +
                ", solvedRequests=" + solvedRequests +
                '}';
    }
}