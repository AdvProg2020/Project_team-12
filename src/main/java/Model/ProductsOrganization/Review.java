package Model.ProductsOrganization;

import com.google.gson.annotations.Expose;

public class Review {
    @Expose(serialize = true)
    private String description;
    @Expose(serialize = true)
    private Status status;
    @Expose(serialize = true)
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
