package Model.ProductsOrganization.Filter;

import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.Sort.Sort;

import java.util.ArrayList;

public class Filter {
    private ArrayList<Filterable> allFilters;
    private Category currentCategory;
    private Sort sort;

    public Filter(Category currentCategory) {
        this.allFilters = new ArrayList<>();
        this.currentCategory = currentCategory;
        this.sort = new Sort();
    }

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

    public boolean canFilter(String name) {
        return getAvailableFilters().contains(name);
    }

    public void filterBySelectedFeatures(String name, ArrayList<String> selectedValues) {
        SelectiveFilter filter = new SelectiveFilter(name, selectedValues);
        if (canDisableFilter(name))
            disableFilter(name);
        allFilters.add(filter);
    }

    public void filterByRange(String name, double minValue, double maxValue) {
        RangeFilter filter = new RangeFilter(name, minValue, maxValue);
        if (canDisableFilter(name))
            disableFilter(name);
        allFilters.add(filter);
    }

    public ArrayList<String> getCurrentFilters() {
        ArrayList<String> currentFilters = new ArrayList<>();
        for (Filterable filter : allFilters)
            currentFilters.add(filter.toString());
        return currentFilters;
    }

    public boolean canDisableFilter(String name) {
        Filterable filter = getFilterByName(name);
        return filter != null;
    }

    public void disableFilter(String name) {
        Filterable filter = getFilterByName(name);
        allFilters.remove(filter);
    }

    public ArrayList<Product> getFilteredProducts(ArrayList<Product> products) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            boolean canPassFilter = true;
            for (Filterable filter : allFilters) {
                if (!filter.canPassFilter(product)) {
                    canPassFilter = false;
                    break;
                }
            }
            if (canPassFilter)
                filteredProducts.add(product);
        }
        return filteredProducts;
    }

    public Filterable getFilterByName(String name) {
        for (Filterable filter : allFilters)
            if (filter.getName().equals(name))
                return filter;
        return null;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }
}
