package Model.ProductsOrganization;

import Model.Status;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    @Expose
    private String name;
    @Expose
    private String brand;
    @Expose
    private int price;
    @Expose
    private HashMap<String, String> specifications;
    @Expose
    private String Description;
    @Expose
    private int id;
    @Expose
    private Status status;
    @Expose
    private int remainingItems;
    @Expose
    private ArrayList<String> allSellers;
    @Expose(serialize = false, deserialize = false)
    private Category parent;
    @Expose
    private ArrayList<Score> allSubmittedScores;
    @Expose
    private ArrayList<Review> allReviews;
    @Expose
    private String categoryPath;
    public Product(int id, Status status, String name, String brand, int price, int remainingItems, HashMap<String, String> specifications, String description, Category parent) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.remainingItems = remainingItems;
        this.specifications = specifications;
        Description = description;
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRemainingItems() {
        return remainingItems;
    }

    public void setRemainingItems(int remainingItems) {
        this.remainingItems = remainingItems;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public ArrayList<Score> getAllSubmittedScores() {
        return allSubmittedScores;
    }

    public void setAllSubmittedScores(ArrayList<Score> allSubmittedScores) {
        this.allSubmittedScores = allSubmittedScores;
    }

    public ArrayList<Review> getAllReviews() {
        return allReviews;
    }

    public void setAllReviews(ArrayList<Review> allReviews) {
        this.allReviews = allReviews;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }
}