package Model.ProductsOrganization;


import java.util.HashMap;

public class Category {
    private String name;
    private Category parent;
    private HashMap<String,Category> allSubCategories;
    private HashMap<String,Product> allIncludedProducts;

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
        allIncludedProducts = new HashMap<>();
        allIncludedProducts = new HashMap<>();
    }

    public static String  createCategoryStringPath(Category category){
        String var1000 = "";
        Category temp = category;
        while (temp.getParent() != null) {
            var1000 += temp.getName() + "/";
            temp = category.getParent();
        }
        var1000 += temp.getName();
        String[] strings = var1000.split("/");
        var1000 = "";
        for (int i = strings.length - 1 ; i >-1 ; i--) {
            var1000 += strings[i] + "/";
        }
        return var1000;
    }

    public void addSubCategory(Category category){}
    public void addProduct (Product product){}

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

    public HashMap<String, Category> getAllSubCategories() {
        return allSubCategories;
    }

    public void setAllSubCategories(HashMap<String, Category> allSubCategories) {
        this.allSubCategories = allSubCategories;
    }

    public HashMap<String, Product> getAllIncludedProducts() {
        return allIncludedProducts;
    }

    public void setAllIncludedProducts(HashMap<String, Product> allIncludedProducts) {
        this.allIncludedProducts = allIncludedProducts;
    }
}