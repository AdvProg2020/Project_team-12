package Model.Request;


import Controller.DataBase.DataCenter;
import Model.Account.CanRequest;
import Model.ProductsOrganization.Review;
import Model.ProductsOrganization.ReviewStatus;

import java.io.IOException;
import java.util.Objects;

public class ReviewRequest extends Request {
    private Review review;
    private String senderUsername;
    private String  pId;
    private boolean accepted;

    public ReviewRequest(String sender, int id, String text, boolean active, Review review, String senderUsername, String pId) {
        super(sender, id, text, active);
        this.review = review;
        this.senderUsername = senderUsername;
        this.pId = pId;
    }

    public void acceptRequest() throws Exception {
        accepted = true;
        Review review = DataCenter.getInstance().getProductById(pId).getReview(this.review);
        if(review!= null){
            review.setStatus(ReviewStatus.CONFIRMED);
            deleteRequest();
        }
        else
            throw new Exception("Review not found in product");
    }

    public void deleteRequest() throws IOException {
        ((CanRequest)DataCenter.getInstance().getAccountByName(senderUsername)).deleteRequestWithId(this.getId());
        ((CanRequest)DataCenter.getInstance().getAccountByName(senderUsername)).getSolvedRequests().add(this.toString());
        DataCenter.getInstance().saveAccount(DataCenter.getInstance().getAccountByName(senderUsername));
        DataCenter.getInstance().deleteRequestWithId(id);
    }

    public void declineRequest() throws Exception {
        accepted = false;
        Review review = DataCenter.getInstance().getProductById(pId).getReview(this.review);
        if(review!= null){
            review.setStatus(ReviewStatus.UNCONFIRMED);
            deleteRequest();
        }
        else
            throw new Exception("Review not found in product");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ReviewRequest that = (ReviewRequest) o;
        return pId == that.pId &&
                Objects.equals(review, that.review) &&
                Objects.equals(senderUsername, that.senderUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), review, senderUsername, pId);
    }

    @Override
    public String toString() {
        return String.format("Request with Code" + id+
                "and related to review " +review.toString() +
                "has been %s",accepted?"accepted":"notAccepted");
    }
}
