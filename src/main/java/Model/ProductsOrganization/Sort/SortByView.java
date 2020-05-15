package Model.ProductsOrganization.Sort;

import Model.ProductsOrganization.Product;

import java.util.Comparator;

public class SortByView implements Comparator<Product> {
    public int compare(Product product1, Product product2) {
        return product2.getViews() - product1.getViews();
    }
}
