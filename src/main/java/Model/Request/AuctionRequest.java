package Model.Request;

import Controller.DataBase.BadRequestException;
import Controller.DataBase.Config;
import Controller.DataBase.DataCenter;
import Model.Account.Seller;
import Model.Status;
import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.util.Objects;

public class AuctionRequest extends Request implements DeclineHasCause{
    @Expose
    private String auctionId;
    @Expose
    private String message;

    public AuctionRequest(String sender, String id, boolean active, String auctionId) {
        super(sender, id, active);
        this.auctionId = auctionId;
    }

    @Override
    public void acceptRequest() throws Exception {
        try {
            DataCenter.getInstance().getAuctionWithId(auctionId).setStatus(Status.ACCEPTED);
            deleteRequest();
        } catch (Exception var1) {
            throw new Exception("Request could not been accepted", new Throwable("couldn't find auction "));
        }
    }

    @Override
    public void deleteRequest() throws Exception {
        ((Seller) DataCenter.getInstance().getAccountByName(senderUserName)).deleteRequestWithId(this.getId());
        ((Seller) DataCenter.getInstance().getAccountByName(senderUserName)).getSolvedRequests().add(this.toString());
        DataCenter.getInstance().saveAccount(DataCenter.getInstance().getAccountByName(senderUserName));
        DataCenter.getInstance().deleteRequestWithId(id);
        Config.getInstance().removeRequestId(getId());
    }


    public void declineRequest(String cause) throws Exception {
        try {
            DataCenter.getInstance().getAuctionWithId(auctionId).setStatus(Status.EDITING);
            message = cause;
        } catch (Exception var1) {
            throw new Exception("Request could not been accepted", new Throwable("couldn't find auction "));
        }
    }

    @Override
    public String toString() {
        return "Request with Id " +id +
                "related to auction with id" + auctionId +
                "has been accepted.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuctionRequest that = (AuctionRequest) o;
        return auctionId == that.auctionId &&
                Objects.equals(senderUserName, that.senderUserName) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), auctionId, senderUserName, message);
    }

    @Override
    public String showDetails() throws BadRequestException {
        return "Request with Id " +id +
                "related to auction with id" + auctionId +
                " \n "+DataCenter.getInstance().getAuctionWithId(auctionId).toString();
    }
}
