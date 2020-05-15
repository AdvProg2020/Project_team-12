package Model.ProductsOrganization.Sort;

import Model.ProductsOrganization.Product;

import java.util.Comparator;

public class SortByScore implements Comparator<Product> {
    public int compare(Product product1, Product product2) {
        return Double.compare(product2.getAverageMark(), product1.getAverageMark());
    }
}
