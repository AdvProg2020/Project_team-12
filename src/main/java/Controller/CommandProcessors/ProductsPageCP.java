package Controller.CommandProcessors;

import Controller.CommandProcessor;
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Filter.Filter;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.Sort.*;

import java.util.ArrayList;

public class ProductsPageCP extends CommandProcessor {
    private static CommandProcessor Instance;
    private ArrayList<Category> allCategories;
    private ArrayList<Product> allProducts;
    private ArrayList<Filter> allFilters;
    private Category currentCategory;
    private Filter filter;
    private Sort sort;

    public ProductsPageCP(ArrayList<Category> allCategories) {
        // TODO: get these from data center:
        this.allCategories = allCategories;
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
    public ArrayList<String> getProductsNames() {
        ArrayList<Product> products = getSortedProducts();
        ArrayList<String> productsNames = new ArrayList<>();
        for (Product product : products)
            productsNames.add(product.getName());
        return productsNames;
    }
    public ArrayList<Product> getSortedProducts() {
        return sort.getSortedProducts(getFilteredProducts());
    }
    public ArrayList<Product> getFilteredProducts() {
        return filter.getFilteredProducts(allProducts);
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
//TODO: show product [productId]
