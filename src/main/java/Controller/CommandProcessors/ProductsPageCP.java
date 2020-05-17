package Controller.CommandProcessors;


import Controller.DataBase.DataCenter;
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Filter.Filter;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.Sort.Sort;
import View.Exceptions.CustomerExceptions;
import View.ProductPage;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class ProductsPageCP extends CommandProcessor {
    private static CommandProcessor Instance;
    private ArrayList<Category> allCategories;
    private ArrayList<Product> allProducts;
    private Filter filter;
    private Sort sort;


    public ProductsPageCP() {
        super(MainMenuCP.getInstance());
        /* TODO: get these from data center:
        this.allCategories = ;
        this.allProducts = ;
        this.filter = new Filter(null);
        this.sort = new Sort(); */
        this.allCategories = DataCenter.getInstance().getCategories();
        this.allProducts = DataCenter.getInstance().getAllProductsObject();
        this.filter = new Filter(null);
        this.sort = new Sort();
    }

    public static CommandProcessor getInstance() {
        Instance = new ProductsPageCP();
        return Instance;
    }

    // Command: view categories
    public ArrayList<String> getAllCategories() {
        ArrayList<String> categoriesNames = new ArrayList<>();
        for (Category category : allCategories)
            categoriesNames.add(category.getName());
        return categoriesNames;
    }

    // Command: show available filters
    public ArrayList<String> getAvailableFilters() {
        return filter.getAvailableFilters();
    }

    // Command: filter [an available filter]
    public boolean canFilter(String name) {
        return filter.canFilter(name);
    }

    public void filterBySelectedFeatures(String name, ArrayList<String> selectedValues) {
        filter.filterBySelectedFeatures(name, selectedValues);
        if (name.equals("Category"))
            filter.setCurrentCategory(getCategoryByName(selectedValues.get(0)));
    }

    public void filterByRange(String name, double minValue, double maxValue) {
        filter.filterByRange(name, minValue, maxValue);
    }

    // Command: current filters
    public ArrayList<String> getCurrentFilters() {
        return filter.getCurrentFilters();
    }

    // Command: disable filter [a selected filter]
    public boolean canDisableFilter(String name) {
        return filter.canDisableFilter(name);
    }

    public void disableFilter(String name) {
        filter.disableFilter(name);
    }

    // Command: show available sorts
    public ArrayList<String> getAvailableSorts() {
        return sort.getAvailableSorts();
    }

    // Command: sort [an available sort]
    public boolean canSort(String type) {
        return sort.canSort(type);
    }

    public void setSortType(String type) {
        sort.setSortType(type);
    }

    // Command: current sort
    public String getCurrentSort() {
        return sort.getCurrentSort();
    }

    // Command: disable sort
    public void disableSort() {
        sort.disableSort();
    }

    // Command: show products
    public ArrayList<Product> getProducts() {
        return getSortedProducts();
    }

    public ArrayList<Product> getSortedProducts() {
        return sort.getSortedProducts(getFilteredProducts());
    }

    public ArrayList<Product> getFilteredProducts() {
        return filter.getFilteredProducts(allProducts);
    }

    public Category getCategoryByName(String name) {
        for (Category category : allCategories)
            if (category.getName().equals(name))
                return category;
        return null;
    }
    public void goToProduct(String Id){
        ProductPageCP.getInstance(Id).setParent(this);
        CommandProcessor.setInstance(ProductPageCP.getInstance());
    }

    public void showProduct(String productID){
        ProductPageCP.getInstance(productID).setParent(this);
        CommandProcessor.setInstance(ProductPageCP.getInstance());
    }

    public boolean doesFilterExistsByName(String name){
        for (String availableFilter : getAvailableFilters()) {
            if (availableFilter.equals(name))
                return true;
        }
        return false;
    }

    public boolean checkRangeFilters(String name) throws Exception{
        if (!doesFilterExistsByName(name))
            throw new CustomerExceptions("filter doesn't exist");
        return filter.checkRangeFilters(name);
    }
}


//products (not related to this part)
//view categories (Done)
//filtering
//⇒ show available filters (Done)
//⇒ filter [an available filter] (Done)
//⇒ current filters (Done)
//⇒ disable filter [a selected filter] (Done)
//sorting
//⇒ show available sorts (Done)
//⇒ sort [an available sort] (Done)
//⇒ current sort (Done)
//⇒ disable sort (Done)
//show products (Done)
//TODO: show product [productId]//DONE
