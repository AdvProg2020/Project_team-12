package Model.ProductsOrganization;

import Controller.DataBase.DataCenter;
import Model.Account.Seller;
import Model.Status;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Product {

    @Expose
    private int numberOfViews = 0;
    @Expose
    private String name;
    @Expose
    private String brand;
    @Expose
    private HashMap<String, String> specifications;
    @Expose
    private String Description;
    @Expose
    private int id;
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
    private String categoryName;

    public Product(int id, Status status, String name, String brand, int remainingItems, HashMap<String, String> specifications, String description, Category parent) {
        this.id = id;
        this.name = name;
        this.brand = brand;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void addScore(Score score) {
        this.allSubmittedScores.add(score);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", specifications=" + specifications +
                ", Description='" + Description + '\'' +
                ", id=" + id +
                ", remainingItems=" + remainingItems +
                ", allSellers=" + allSellers +
                ", parent=" + parent +
                ", allSubmittedScores=" + allSubmittedScores +
                ", allReviews=" + allReviews +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                remainingItems == product.remainingItems &&
                name.equals(product.name) &&
                brand.equals(product.brand) &&
                Objects.equals(specifications, product.specifications) &&
                Objects.equals(Description, product.Description) &&
                Objects.equals(allSellers, product.allSellers) &&
                Objects.equals(parent, product.parent) &&
                Objects.equals(allSubmittedScores, product.allSubmittedScores) &&
                Objects.equals(allReviews, product.allReviews) &&
                Objects.equals(categoryName, product.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand, specifications, Description, id, remainingItems, allSellers, parent, allSubmittedScores, allReviews, categoryName);
    }

    public Review getReview(Review review) {
        for (Review allReview : allReviews) {
            if (review.equals(review))
                return review;
        }
        return null;
    }

    public ArrayList<String> getAllSellers() {
        return allSellers;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public String getSellersInfo() {
        String var100 = "";
        for (String s : allSellers) {
            var100 += ((Seller) DataCenter.getInstance().getAccountByName(s)).getProductInfo(name).getQuantityString();
        }
        return var100;
    }
}
