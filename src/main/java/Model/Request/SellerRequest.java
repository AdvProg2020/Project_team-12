package Model.Request;

import Controller.DataBase.BadRequestException;
import Controller.DataBase.DataCenter;
import Model.Account.Seller;

import java.io.IOException;
import java.util.Objects;

public class SellerRequest extends Request implements NoCauseDecline{

    public SellerRequest(int id, boolean active, String sellerUserName) {
        super(sellerUserName, id, active);
    }

    @Override
    public void acceptRequest() throws Exception {
        Seller seller = ((Seller) DataCenter.getInstance().getAccountByName(senderUserName));
        seller.setAccountTypeAccepted(true);
        deleteRequest();
    }

    public void declineRequest() throws Exception {
        ((Seller) DataCenter.getInstance().getAccountByName(senderUserName)).setAccountTypeAccepted(false);
        deleteRequest();
    }

    @Override
    public void deleteRequest() throws Exception {
        ((Seller) DataCenter.getInstance().getAccountByName(senderUserName)).deleteRequestWithId(this.getId());
        ((Seller) DataCenter.getInstance().getAccountByName(senderUserName)).getSolvedRequests().add(this.toString());
        DataCenter.getInstance().saveAccount(DataCenter.getInstance().getAccountByName(senderUserName));
        DataCenter.getInstance().deleteRequestWithId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SellerRequest that = (SellerRequest) o;
        return Objects.equals(senderUserName, that.senderUserName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), senderUserName);
    }

    @Override
    public String showDetails() throws Exception {
        return String.format("Request with id:" + id
                + " related to acceptance of seller" +
                " profile for further actions."+ ((Seller) DataCenter
                .getInstance().getAccountByName(senderUserName))
                .toString());
    }

    @Override
    public String toString() {
        try {
            return String.format("Request with id:" + id
                    + "related to acceptance of seller" +
                    " profile for further actions has been %s", ((Seller) DataCenter
                    .getInstance().getAccountByName(senderUserName))
                    .isAccountTypeAccepted() ? "accepted" : "notAccepted");
        } catch (Exception exception) {
            return "";
        }
    }
}
