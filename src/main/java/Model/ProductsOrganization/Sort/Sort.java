package Model.ProductsOrganization.Sort;

import Model.ProductsOrganization.Product;

import java.util.ArrayList;
import java.util.Collections;

public class Sort {
    private String sortType;

    // Command: show available sorts
    public ArrayList<String> getAvailableSorts() {
        ArrayList<String> availableSorts = new ArrayList<>();
        availableSorts.add("Most viewed");
        availableSorts.add("Newest");
        availableSorts.add("Best score");
        availableSorts.add("Lowest price");
        availableSorts.add("Highest price");
        return availableSorts;
    }

    // Command: sort [an available sort]
    public boolean canSort(String type) {
        return getAvailableSorts().contains(type);
    }
    public void setSortType(String type) {
        sortType = type;
    }

    // Command: current sort
    public String getCurrentSort() {
        return sortType;
    }

    // Command: disable sort
    public void disableSort() {
        sortType = "Most viewed";
    }

    public ArrayList<Product> getSortedProducts(ArrayList<Product> allProducts) {
        ArrayList<Product> products = allProducts;
        switch(sortType) {
            case "Most viewed":
                Collections.sort(products, new SortByView());
                break;
            case "Newest":
                Collections.sort(products, new SortByDate());
                break;
            case "Best Score":
                Collections.sort(products, new SortByScore());
                break;
            case "Lowest Price":
                Collections.sort(products, new SortByLowestPrice());
                break;
            case "Highest Price":
                Collections.sort(products, new SortByHighestPrice());
                break;
        }
        return products;
    }
}
