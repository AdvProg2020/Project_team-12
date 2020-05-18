package Model.Request;

import Controller.DataBase.BadRequestException;
import Controller.DataBase.DataCenter;
import Model.Account.Seller;
import Model.Status;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public class ProductRequest extends Request implements DeclineHasCause{
    private String product;
    private String message;

    public ProductRequest(String sender, Integer id, boolean active, String product) {
        super(sender, id, active);
        this.product = product;
    }

    @Override
    public void acceptRequest() throws Exception {
        DataCenter dataCenter = DataCenter.getInstance();
        dataCenter.getProductById(product).setStatus(Status.ACCEPTED);
        deleteRequest();
    }

    @Override
    public void deleteRequest() throws Exception {
        DataCenter dataCenter = DataCenter.getInstance();
        ((Seller) dataCenter.getAccountByName(senderUserName)).deleteRequestWithId(this.getId());
        ((Seller) dataCenter.getAccountByName(senderUserName)).getSolvedRequests().add(this.toString());
        dataCenter.saveAccount(dataCenter.getAccountByName(senderUserName));
        dataCenter.deleteRequestWithId(id);
    }


    public void declineRequest(String cause) throws Exception {
        DataCenter dataCenter = DataCenter.getInstance();
        dataCenter.getProductById(product).setStatus(Status.EDITING);
        this.message = cause;
        DataCenter.getInstance().saveAccount(dataCenter.getAccountByName(senderUserName));
    }

    @Override
    public String toString() {
        return "Request with id" + id +
                "related to product" + DataCenter.getInstance().getProductById(product).toString() +
                "has been accepted";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductRequest product1 = (ProductRequest) o;
        return Objects.equals(product, product1.product) &&
                Objects.equals(senderUserName, product1.senderUserName) &&
                Objects.equals(message, product1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), product, senderUserName, message);
    }

    @Override
    public String showDetails() throws BadRequestException {
        return "Request with id" + id +
                "related to product" + DataCenter.getInstance().getProductById(product).toString() ;
    }


}
