package Controller.CommandProcessors;

import Controller.CommandProcessor;
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Filter.Filter;
import Model.ProductsOrganization.Filter.RangeFilter;
import Model.ProductsOrganization.Filter.SelectiveFilter;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.Sort.*;

import java.util.ArrayList;

public class ProductsPageCP extends CommandProcessor {
    private static CommandProcessor Instance;
    private ArrayList<Category> allCategories;
    private ArrayList<Product> allProducts;
    private ArrayList<Filter> allFilters;
    private Category currentCategory;
    private Sort sort;

    // Command: view categories
    public ArrayList<String> getAllCategories() {
        ArrayList<String> categoriesNames = new ArrayList<>();
        for (Category category : allCategories)
            categoriesNames.add(category.getName());
        return categoriesNames;
    }

    // Command: show available filters
    public ArrayList<String> getAvailableFilters() {
        ArrayList<String> availableFilters = new ArrayList<>();
        availableFilters.add("Category");
        availableFilters.add("Name");
        availableFilters.add("Brand");
        availableFilters.add("Seller");
        availableFilters.add("Availability");
        availableFilters.add("Price");
        if (currentCategory != null)
            availableFilters.addAll(currentCategory.getFeatures());
        return availableFilters;
    }

    // Command: filter [an available filter]
    public boolean canFilter(String name) {
        return getAvailableFilters().contains(name);
    }
    public void filterBySelectedFeatures(String name, ArrayList<String> selectedValues) {
        SelectiveFilter filter = new SelectiveFilter(name, selectedValues);
        allFilters.add(filter);
    }
    public void filterByRange(String name, int minValue, int maxValue) {
        RangeFilter filter = new RangeFilter(name, minValue, maxValue);
        allFilters.add(filter);
    }

    // Command: current filters
    public ArrayList<String> getCurrentFilters() {
        ArrayList<String> currentFilters = new ArrayList<>();
        for (Filter filter : allFilters)
            currentFilters.add(filter.toString());
        return currentFilters;
    }

    // Command: disable filter [a selected filter]
    public boolean canDisableFilter(String name) {
        Filter filter = getFilterByName(name);
        return filter != null;
    }
    public void disableFilter(String name) {
        Filter filter = getFilterByName(name);
        allFilters.remove(filter);
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
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : allProducts) {
            boolean canPassFilter = true;
            for (Filter filter : allFilters) {
                if (!filter.canPassFilter(product)) {
                    canPassFilter = false;
                    break;
                }
            }
            if (canPassFilter)
                products.add(product);
        }
        return products;
    }

    // Additional methods
    public Filter getFilterByName(String name) {
        for (Filter filter : allFilters)
            if (filter.getName().equals(name))
                return filter;
        return null;
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
