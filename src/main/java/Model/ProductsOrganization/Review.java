package Model.ProductsOrganization;

import com.google.gson.annotations.Expose;

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

    private enum Status {TO_BE_CONFIRMED, CONFIRMED, UNCONFIRMED}
}