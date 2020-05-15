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
    private ArrayList<String> allSellers;
    private int remainingItems;
    private Category parent;
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

    public Status getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<String> getAllSellers() {
        return allSellers;
    }

    public int getRemainingItems() {
        return remainingItems;
    }

    public Category getParent() {
        return parent;
    }

    public HashMap<String, String> getSpecs() {
        return specs;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Review> getAllReviews() {
        return allReviews;
    }

    public ArrayList<Score> getAllScores() {
        return allScores;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public Date getDate() {
        return date;
    }

    public int getViews() {
        return views;
    }
}
