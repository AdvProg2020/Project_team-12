package Model.ProductsOrganization;

import Model.Account.Customer;

public class Review {
    private Customer customer;
    private Product product;
    private String title;
    private String content;
    private ReviewStatus status;
    private boolean isBuyer;

    public Review(Product product, String title, String content) {
        this.product = product;
        this.title = title;
        this.content = content;
    }
}