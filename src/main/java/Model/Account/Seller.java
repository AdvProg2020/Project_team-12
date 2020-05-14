package Model.Account;

import Model.Log.SellLog;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.ProductInfo;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Seller extends Account implements CanRequest{
    @Expose
    private String companyInformation;
    @Expose
    private ArrayList<SellLog> sellLogs = new ArrayList<>();
    @Expose
    private ArrayList<ProductInfo> allProducts = new ArrayList<>();
    @Expose
    private ArrayList<Integer> activeRequestsId = new ArrayList<>();
    @Expose
    private ArrayList<String> solvedRequests = new ArrayList<>();
    @Expose
    private ArrayList<Integer> auctionsId = new ArrayList<>();

    public Seller(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password, String companyInformation) {
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
        return super.toString() + "Seller{" +
                "companyInformation='" + companyInformation + '\'' +
                ", sellLogs=" + sellLogs +
                ", allProducts=" + allProducts +
                '}';
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

    public ArrayList<Integer> getActiveRequestsId() {
        return activeRequestsId;
    }

    public ArrayList<Integer> getAuctionsId() {
        return auctionsId;
    }

    public void setAuctionsId(ArrayList<Integer> auctionsId) {
        this.auctionsId = auctionsId;
    }

    public void deleteProductInfo(Product product){
        for (ProductInfo productInfo : allProducts) {
            if (productInfo.getPName().equals(product.getName()))
                allProducts.remove(productInfo);
        }
    }
}

