package Model.ProductsOrganization.Sort;

import Model.ProductsOrganization.Product;

import java.util.ArrayList;
import java.util.Collections;

public class Sort {
    private String sortType = "MostViewed";

    // Command: show available sorts
    public ArrayList<String> getAvailableSorts() {
        ArrayList<String> availableSorts = new ArrayList<>();
        availableSorts.add("MostViewed");
        availableSorts.add("Newest");
        availableSorts.add("BestScore");
        availableSorts.add("LowestPrice");
        availableSorts.add("HighestPrice");
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
        sortType = "MostViewed";
    }

    public ArrayList<Product> getSortedProducts(ArrayList<Product> products) {
        switch(sortType) {
            case "MostViewed":
                Collections.sort(products, new SortByView());
                break;
            case "Newest":
                Collections.sort(products, new SortByDate());
                break;
            case "BestScore":
                Collections.sort(products, new SortByScore());
                break;
            case "LowestPrice":
                Collections.sort(products, new SortByLowestPrice());
                break;
            case "HighestPrice":
                Collections.sort(products, new SortByHighestPrice());
                break;
        }
        return products;
    }
}
