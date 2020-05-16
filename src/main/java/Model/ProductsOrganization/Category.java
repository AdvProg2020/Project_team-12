package Model.ProductsOrganization;

import java.util.ArrayList;

public class Category {
    private String name;
    private Category parent;
    private ArrayList<String> features;
    private ArrayList<Category> allSubCategories;
    private ArrayList<Product> allProductsInside;

    public String getName() {
        return name;
    }

    public Category getParent() {
        return parent;
    }

    public ArrayList<String> getFeatures() {
        return features;
    }

    public ArrayList<Category> getAllSubCategories() {
        return allSubCategories;
    }

    public ArrayList<Product> getAllProductsInside() {
        return allProductsInside;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = features;
    }

    public void setAllSubCategories(ArrayList<Category> allSubCategories) {
        this.allSubCategories = allSubCategories;
    }

    public void setAllProductsInside(ArrayList<Product> allProductsInside) {
        this.allProductsInside = allProductsInside;
    }
}
