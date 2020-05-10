package Model.ProductsOrganization;

import Model.Status;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Product {
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
    public Product(int id, Status status, String name, String brand, int remainingItems, HashMap<String, String> specifications, String description, Category parent) {
        this.id = id;
        this.status = status;
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

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", specifications=" + specifications +
                ", Description='" + Description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", remainingItems=" + remainingItems +
                ", allSellers=" + allSellers +
                ", parent=" + parent +
                ", allSubmittedScores=" + allSubmittedScores +
                ", allReviews=" + allReviews +
                ", categoryPath='" + categoryPath + '\'' +
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
                status == product.status &&
                Objects.equals(allSellers, product.allSellers) &&
                Objects.equals(parent, product.parent) &&
                Objects.equals(allSubmittedScores, product.allSubmittedScores) &&
                Objects.equals(allReviews, product.allReviews) &&
                Objects.equals(categoryPath, product.categoryPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand, specifications, Description, id, status, remainingItems, allSellers, parent, allSubmittedScores, allReviews, categoryPath);
    }
}
