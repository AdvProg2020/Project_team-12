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
}
