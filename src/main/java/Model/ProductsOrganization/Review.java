package Model.ProductsOrganization;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Review {
    @Expose
    private String description;
    @Expose
    private Status status;
    @Expose
    private boolean isBuyer;
    public Review(String description, Status status, boolean isBuyer) {
        this.description = description;
        this.status = status;
        this.isBuyer = isBuyer;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return isBuyer == review.isBuyer &&
                Objects.equals(description, review.description) &&
                status == review.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, status, isBuyer);
    }

    public enum Status {TO_BE_CONFIRMED, CONFIRMED, UNCONFIRMED}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isBuyer() {
        return isBuyer;
    }

    public void setBuyer(boolean buyer) {
        isBuyer = buyer;
    }
}
