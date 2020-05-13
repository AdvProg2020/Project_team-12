package Model.Request;

import Controller.DataBase.DataCenter;
import Model.Account.Seller;
import Model.Status;

import java.io.IOException;
import java.util.Objects;

public class AuctionRequest extends Request {
    private int auctionId;
    private String senderUsername;
    private String message;

    public AuctionRequest(String sender, int id, String text, boolean active, int auctionId, String senderUsername) {
        super(sender, id, text, active);
        this.auctionId = auctionId;
        this.senderUsername = senderUsername;
    }

    @Override
    public void AcceptRequest() throws Exception {
        try {
            DataCenter.getInstance().getAuctionWithId(auctionId).setStatus(Status.ACCEPTED);
            deleteRequest();
        } catch (Exception var1) {
            throw new Exception("Request could not been accepted", new Throwable("couldn't find auction "));
        }
    }

    @Override
    public void deleteRequest() throws IOException {
        ((Seller) DataCenter.getInstance().getAccountByName(senderUsername)).deleteRequestWithId(this.getId());
        ((Seller) DataCenter.getInstance().getAccountByName(senderUsername)).getSolvedRequests().add(this.toString());
        DataCenter.getInstance().saveAccount(DataCenter.getInstance().getAccountByName(senderUsername));
        DataCenter.getInstance().deleteRequestWithId(id);
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
                Objects.equals(senderUsername, that.senderUsername) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), auctionId, senderUsername, message);
    }
}
