package Model.ProductsOrganization.Sort;

import Model.ProductsOrganization.Product;

import java.util.Comparator;

public class SortByLowestPrice implements Comparator<Product> {
    public int compare(Product product1, Product product2) {
        return Double.compare(product1.getPrice(), product2.getPrice());
    }
}
