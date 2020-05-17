package Controller.CommandProcessors;


import Controller.DataBase.DataCenter;
import Model.Discount.Auction;
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Filter.Filter;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.Sort.Sort;
import View.Exceptions.CustomerExceptions;

import java.util.ArrayList;
import java.util.HashMap;

public class AuctionsPageCP extends Controller.CommandProcessors.CommandProcessor {
    private static CommandProcessor Instance;
    private ArrayList<Category> allCategories;
    private ArrayList<Auction> allAuctions;
    private ArrayList<Product> allProducts;
    private ArrayList<Filter> allFilters;
    private Filter filter;
    private Sort sort;
    public static CommandProcessor getInstance(){
        if (Instance == null)
            Instance = new AuctionsPageCP();
        return Instance;
    }
    public AuctionsPageCP() {
        super(MainMenuCP.getInstance());
        /* TODO: get these from data center:*/
        this.allCategories = DataCenter.getInstance().getCategories();
        this.allProducts = new ArrayList<>();
        for (Auction auction : allAuctions)
            allProducts.addAll(auction.getAllProducts());
        this.allFilters = new ArrayList<>();
        this.filter = new Filter(null);
        this.sort = new Sort();
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
        if (name.equals("Category") && selectedValues.size() > 0)
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

    // Command: offs
    public HashMap<Product, Auction> getProductsInAuction() {
        ArrayList<Product> products = getSortedProducts();
        HashMap<Product, Auction> map = new HashMap<>();
        for (Product product : products)
            map.put(product, getAuctionByProduct(product));
        return map;
    }
    public ArrayList<Product> getSortedProducts() {
        return sort.getSortedProducts(getFilteredProducts());
    }
    public ArrayList<Product> getFilteredProducts() {
        return filter.getFilteredProducts(allProducts);
    }

    // Additional methods
    public Category getCategoryByName(String name) {
        for (Category category : allCategories)
            if (category.getName().equals(name))
                return category;
        return null;
    }
    public Auction getAuctionByProduct(Product product) {
        for (Auction auction : allAuctions)
            if (auction.getAllProducts().contains(product))
                return auction;
        return null;
    }

    public ArrayList<Filter> getAllFilters() {
        return allFilters;
    }

    public void setAllFilters(ArrayList<Filter> allFilters) {
        this.allFilters = allFilters;
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

    public boolean doesProductExist(String id){
        return dataCenter.doesProductExist(id);
    }
}
