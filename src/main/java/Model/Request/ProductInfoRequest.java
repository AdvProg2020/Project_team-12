package Model.Request;

import Controller.DataBase.DataCenter;
import Model.Account.CanRequest;
import Model.Account.Seller;
import Model.ProductsOrganization.ProductInfo;
import Model.Status;

import java.io.IOException;
import java.util.Objects;

public class ProductInfoRequest extends Request {
    private ProductInfo productInfo;
    private String senderUsername;
    private String message;

    public ProductInfoRequest(String sender, int id, String text, boolean active, ProductInfo productInfo, String senderUsername) {
        super(sender, id, text, active);
        this.productInfo = productInfo;
        this.senderUsername = senderUsername;

    }

    @Override
    public void AcceptRequest() throws Exception {
        for (ProductInfo productInfo : ((Seller) DataCenter.getInstance().getAccountByName(senderUsername)).getAllProducts()) {
            if (productInfo == this.productInfo){
                productInfo.setStatus(Status.ACCEPTED);
                deleteRequest();
                return;
            }
        }
        throw new Exception("Request could not been accepted",new Throwable("couldn't find product info"));
    }

    @Override
    public void deleteRequest() throws IOException {
        ((Seller)DataCenter.getInstance().getAccountByName(senderUsername)).deleteRequestWithId(this.getId());
        ((Seller)DataCenter.getInstance().getAccountByName(senderUsername)).getSolvedRequests().add(this.toString());
        DataCenter.getInstance().saveAccount(DataCenter.getInstance().getAccountByName(senderUsername));
        DataCenter.getInstance().deleteRequestWithId(id);
    }


    public void declineRequest(String cause) throws Exception {
        for (ProductInfo productInfo : ((Seller) DataCenter.getInstance().getAccountByName(senderUsername)).getAllProducts()) {
            if (productInfo == this.productInfo){
                productInfo.setStatus(Status.EDITING);
                this.message = cause;
                return;
            }
        }
        throw new Exception("Request could not been accepted",new Throwable("couldn't find product info"));
    }

    @Override
    public String toString() {
        return "Request with id" + id+
                "related to product" + productInfo.getPName() +
                "has been accepted";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductInfoRequest that = (ProductInfoRequest) o;
        return Objects.equals(productInfo, that.productInfo) &&
                Objects.equals(senderUsername, that.senderUsername) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), productInfo, senderUsername, message);
    }
}
