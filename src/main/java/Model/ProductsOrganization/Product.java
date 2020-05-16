package Model.ProductsOrganization;

import Model.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Product {
    private String ID;
    private Status status;
    private String name;
    private String brand;
    private double price;
    private double discountPercentInAuction;
    private String seller;
    private int remainingItems;
    private Category parent;
    private String parentStr;
    private HashMap<String, String> specs;
    private String description;
    private ArrayList<Review> allReviews;
    private ArrayList<Score> allScores;
    private double averageMark;
    private Date date;
    private int views;

    public void submitScore(Score score) {
        allScores.add(score);
        averageMark = averageMark * (allScores.size() - 1) / allScores.size();
    }

    public void submitReview(Review review) {
        allReviews.add(review);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountPercentInAuction() {
        return discountPercentInAuction;
    }

    public void setDiscountPercentInAuction(double discountPercentInAuction) {
        this.discountPercentInAuction = discountPercentInAuction;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getRemainingItems() {
        return remainingItems;
    }

    public void setRemainingItems(int remainingItems) {
        this.remainingItems = remainingItems;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public String getParentStr() {
        return parentStr;
    }

    public void setParentStr(String parentStr) {
        this.parentStr = parentStr;
    }

    public HashMap<String, String> getSpecs() {
        return specs;
    }

    public void setSpecs(HashMap<String, String> specs) {
        this.specs = specs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Review> getAllReviews() {
        return allReviews;
    }

    public void setAllReviews(ArrayList<Review> allReviews) {
        this.allReviews = allReviews;
    }

    public ArrayList<Score> getAllScores() {
        return allScores;
    }

    public void setAllScores(ArrayList<Score> allScores) {
        this.allScores = allScores;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
