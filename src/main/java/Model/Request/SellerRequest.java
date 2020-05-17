package Model.Request;

import Controller.DataBase.DataCenter;
import Model.Account.Seller;

import java.io.IOException;
import java.util.Objects;

public class SellerRequest extends Request {
    private String sellerUserName;

    public SellerRequest(int id, String text, boolean active, String sellerUserName) {
        super(sellerUserName, id, text, active);
        this.sellerUserName = sellerUserName;
    }

    @Override
    public void acceptRequest() throws Exception {
        Seller seller = ((Seller) DataCenter.getInstance().getAccountByName(sellerUserName));
        seller.setAccountTypeAccepted(true);
        deleteRequest();
    }

    public void declineRequest() throws Exception {
        ((Seller) DataCenter.getInstance().getAccountByName(sellerUserName)).setAccountTypeAccepted(false);
        deleteRequest();
    }

    @Override
    public void deleteRequest() throws IOException {
        ((Seller) DataCenter.getInstance().getAccountByName(sellerUserName)).deleteRequestWithId(this.getId());
        ((Seller) DataCenter.getInstance().getAccountByName(sellerUserName)).getSolvedRequests().add(this.toString());
        DataCenter.getInstance().saveAccount(DataCenter.getInstance().getAccountByName(sellerUserName));
        DataCenter.getInstance().deleteRequestWithId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SellerRequest that = (SellerRequest) o;
        return Objects.equals(sellerUserName, that.sellerUserName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sellerUserName);
    }

    @Override
    public String toString() {
        return String.format("Request with id:" + id
                + "related to acceptance of seller" +
                " profile for further actions has been %s", ((Seller) DataCenter
                .getInstance().getAccountByName(sellerUserName))
                .isAccountTypeAccepted() ? "accepted" : "notAccepted");
    }
}
