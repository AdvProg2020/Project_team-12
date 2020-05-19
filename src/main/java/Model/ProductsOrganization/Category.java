package Model.ProductsOrganization;


import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    @Expose
    private String name;
    @Expose
    private String categoryPath;
    @Expose
    private ArrayList<String> features;
    private Category parent;
    private HashMap<String, Category> allSubCategories = new HashMap<>();
    private HashMap<String, Product> allProductsInside = new HashMap<>();

    public Category(String name, ArrayList<String> features, Category parent) {
        this.name = name;
        this.features = features;
        this.parent = parent;
    }

    public static String createCategoryStringPath(Category category) {
        String var1000 = "";
        Category temp = category;
        while (temp.getParent() != null) {
            var1000 += temp.getName() + "/";
            temp = category.getParent();
        }
        var1000 += temp.getName();
        String[] strings = var1000.split("/");
        var1000 = "";
        for (int i = strings.length - 1; i > -1; i--) {
            var1000 += strings[i] + "/";
        }
        return var1000;
    }

    public void addSubCategory(Category category) {
    }

    public void addProduct(Product product) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public HashMap<String, Category> getAllSubCategories() {
        return allSubCategories;
    }

    public void setAllSubCategories(HashMap<String, Category> allSubCategories) {
        this.allSubCategories = allSubCategories;
    }

    public HashMap<String, Product> getAllProductsInside() {
        return allProductsInside;
    }

    public void setAllProductsInside(HashMap<String, Product> allProductsInside) {
        this.allProductsInside = allProductsInside;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public ArrayList<String> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        String var = "Category{" +
                "name='" + name +
                ", features=" + features +
                ", parent=" + parent +
                ", allProductsInside=" + allProductsInside;
        var += "\n" + name + "\n";
        for (Category value : allSubCategories.values()) {
            var += value.toString() + "\n";
        }
        return var;
    }
}