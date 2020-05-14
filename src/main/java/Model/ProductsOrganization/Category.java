package Model.ProductsOrganization;


import com.google.gson.annotations.Expose;

import java.util.HashMap;

public class Category {
    @Expose
    private String name;
    @Expose
    private String categoryPath;
    private Category parent;
    private HashMap<String, Category> subCategories = new HashMap<>();
    private HashMap<String, Product> includedPRoducts = new HashMap<>();

    public Category(String name, Category parent) {
        this.name = name;
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

    @Override
    public String toString() {
        return "";
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

    public HashMap<String, Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(HashMap<String, Category> subCategories) {
        this.subCategories = subCategories;
    }

    public HashMap<String, Product> getIncludedPRoducts() {
        return includedPRoducts;
    }

    public void setIncludedPRoducts(HashMap<String, Product> includedPRoducts) {
        this.includedPRoducts = includedPRoducts;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }
}