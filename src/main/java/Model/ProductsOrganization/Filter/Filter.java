package Model.ProductsOrganization.Filter;

import Model.ProductsOrganization.Product;

public interface Filter {
    public String toString();
    public String getName();
    public boolean canPassFilter(Product product);
}
