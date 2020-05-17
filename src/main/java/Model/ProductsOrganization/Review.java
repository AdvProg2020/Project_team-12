package Model.ProductsOrganization;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Review {
    @Expose
    private String title;
    @Expose
    private String contents;
    @Expose
    private ReviewStatus status = ReviewStatus.TO_BE_CONFIRMED;
    @Expose
    private boolean isBuyer;

    public Review(String title, String contents, boolean isBuyer) {
        this.title = title;
        this.contents = contents;
        this.isBuyer = isBuyer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public boolean isBuyer() {
        return isBuyer;
    }

    public void setBuyer(boolean buyer) {
        isBuyer = buyer;
    }

    @Override
    public String toString() {
        return "Review{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", isBuyer=" + isBuyer +
                '}';
    }
}
