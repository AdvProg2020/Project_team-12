package Model.ProductsOrganization;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    private String name;
    private Category parent;
    private ArrayList<Category> allSubCategories;
    private ArrayList<Product> allIncludedProducts;

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
        allIncludedProducts = new ArrayList<>();
        allIncludedProducts = new ArrayList<>();
    }

    public void addSubCategory(Category category){}
    public void addProduct (Product product){}

    @Override
    public String toString() {
        if (parent == null)
            return "/" + name;
        return parent.toString() + "/" + name;
    }
}