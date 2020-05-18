package Model.Account;

import Model.Log.SellLog;
import Model.ProductsOrganization.Product;
import Model.Request.Request;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Seller extends Account implements CanRequest{
    @Expose
    private boolean accountTypeAccepted = false;
    @Expose
    private String companyInformation;
    @Expose
    private ArrayList<SellLog> sellLogs = new ArrayList<>();
    @Expose
    private ArrayList<Product> allProducts = new ArrayList<>();
    @Expose
    private ArrayList<Integer> activeRequestsId = new ArrayList<>();
    @Expose
    private ArrayList<String> solvedRequests = new ArrayList<>();
    @Expose
    private ArrayList<String> auctionsId = new ArrayList<>();

    public Seller(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password, String companyInformation) {
        super(username, firstName, lastName, emailAddress, phoneNumber, password);
        this.companyInformation = companyInformation;
    }

    @Override
    public void writeInfoInFile() {

    }


    @Override
    public String toString() {
        return super.toString() + "Seller{" +
                "companyInformation='" + companyInformation + '\'' +
                ", sellLogs=" + sellLogs +
                ", allProducts=" + allProducts +
                '}';
    }

    public String getCompanyInformation() {
        return companyInformation;
    }

    public void deleteRequestWithId(Integer id){
        activeRequestsId.remove(id);
    }

    public ArrayList<String> getSolvedRequests() {
        return solvedRequests;
    }

    public void setSolvedRequests(ArrayList<String> solvedRequests) {
        this.solvedRequests = solvedRequests;
    }

    public ArrayList<Integer> getActiveRequestsId() {
        return activeRequestsId;
    }

    public ArrayList<SellLog> getSellLogs() {
        return sellLogs;
    }

    public void setCompanyInformation(String companyInformation) {
        this.companyInformation = companyInformation;
    }

    public void setSellLogs(ArrayList<SellLog> sellLogs) {
        this.sellLogs = sellLogs;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public void setActiveRequestsId(ArrayList<Integer> activeRequestsId) {
        this.activeRequestsId = activeRequestsId;
    }

    public ArrayList<String> getAuctionsId() {
        return auctionsId;
    }

    public void setAuctionsId(ArrayList<String> auctionsId) {
        this.auctionsId = auctionsId;
    }

    public boolean isAccountTypeAccepted() {
        return accountTypeAccepted;
    }

    public void setAccountTypeAccepted(boolean accountTypeAccepted) {
        this.accountTypeAccepted = accountTypeAccepted;
    }

    public void addProduct(Product product){
        this.allProducts.add(product);
    }

    public void addRequest(Request request){
        this.activeRequestsId.add(request.getId());
    }

    public void addAuctionId(String id){
        this.auctionsId.add(id);
    }
}

