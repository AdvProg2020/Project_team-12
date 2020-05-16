package Model.Request;

import Controller.DataBase.DataCenter;
import Model.Account.Seller;
import Model.Status;

import java.io.IOException;
import java.util.Objects;

public class ProductRequest extends Request {
    private String product;
    private String senderUsername;
    private String message;

    public ProductRequest(String sender, int id, String text, boolean active, String product, String senderUsername) {
        super(sender, id, text, active);
        this.product = product;
        this.senderUsername = senderUsername;
    }

    @Override
    public void acceptRequest() throws IOException {
        DataCenter dataCenter = DataCenter.getInstance();
        dataCenter.getProductById(product).setStatus(Status.ACCEPTED);
        deleteRequest();
    }

    @Override
    public void deleteRequest() throws IOException {
        DataCenter dataCenter = DataCenter.getInstance();
        ((Seller)dataCenter.getAccountByName(senderUsername)).deleteRequestWithId(this.getId());
        ((Seller)dataCenter.getAccountByName(senderUsername)).getSolvedRequests().add(this.toString());
        dataCenter.saveAccount(dataCenter.getAccountByName(senderUsername));
        dataCenter.deleteRequestWithId(id);
    }


    public void declineRequest(String cause) throws Exception {
        DataCenter dataCenter = DataCenter.getInstance();
        dataCenter.getProductById(product).setStatus(Status.EDITING);
        this.message = cause;
        DataCenter.getInstance().saveAccount(dataCenter.getAccountByName(senderUsername));
        }

    @Override
    public String toString() {
        return "Request with id" + id+
                "related to product" + DataCenter.getInstance().getProductById(product) +
                "has been accepted";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductRequest product1 = (ProductRequest) o;
        return Objects.equals(product, product1.product) &&
                Objects.equals(senderUsername, product1.senderUsername) &&
                Objects.equals(message, product1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), product, senderUsername, message);
    }
}
