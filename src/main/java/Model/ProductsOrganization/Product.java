package Model.ProductsOrganization;


import Model.Status;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;


public class Product {

    @Expose
    private int views = 0;
    @Expose
    private String name;
    @Expose
    private double discountPercentInAuction = 0;
    @Expose
    private String seller;
    @Expose
    private int remainingItems;
    @Expose
    private double price;
    @Expose
    private String brand;
    @Expose
    private HashMap<String, String> specs;
    @Expose
    private String description;
    @Expose
    private String ID;
    @Expose
    private Status status = Status.CONSTRUCTING;
    @Expose
    private double averageMark;
    @Expose
    private Date date;
    @Expose(serialize = false, deserialize = false)
    private Category parent;
    @Expose
    private ArrayList<Score> allScores;
    @Expose
    private ArrayList<Review> allReviews;
    @Expose
    private String parentStr;
    @Expose
    private ArrayList<String> buyers = new ArrayList<>();

    public Product(String name, String seller, int remainingItems, double price, String brand, HashMap<String, String> specs, String description, String ID, Date date) {
        this.name = name;
        this.seller = seller;
        this.remainingItems = remainingItems;
        this.price = price;
        this.brand = brand;
        this.specs = specs;
        this.description = description;
        this.ID = ID;
        this.date = date;
    }

    public void submitScore(Score score) {
        allScores.add(score);
        averageMark = (averageMark * (allScores.size() - 1) + score.getScore()) / allScores.size();
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
        return price - (price*discountPercentInAuction/100);
    }
    public double getRawPrice(){
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

    public Review getReview(Review review) throws Exception {
        for (Review review1 : allReviews) {
            if (review1.equals(review))
                return review1;
        }
        throw new Exception("Review not found");
    }

    public ArrayList<String> getBuyers() {
        return buyers;
    }


}
