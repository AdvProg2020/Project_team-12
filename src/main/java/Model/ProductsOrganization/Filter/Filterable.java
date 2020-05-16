package Model.ProductsOrganization.Filter;

import Model.ProductsOrganization.Product;

public interface Filterable {
    public String toString();
    public String getName();
    public boolean canPassFilter(Product product);
}
