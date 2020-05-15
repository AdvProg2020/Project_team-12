package Model.ProductsOrganization.Filter;

import Model.ProductsOrganization.Product;

public class RangeFilter implements Filter {
    private String name;
    private double minValue;
    private double maxValue;

    public RangeFilter(String name, double minValue, double maxValue) {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public boolean canPassFilter(Product product) {
        if (name.equals("Price")) {
            double price = product.getPrice();
            return (price >= minValue) && (price <= maxValue);
        } else {
            if (product.getSpecs().containsKey(name)) {
                String value = product.getSpecs().get(name);
                try {
                    int intValue = Integer.parseInt(value);
                    return (intValue >= minValue) && (intValue <= maxValue);
                }
                catch (Exception e) {
                    return false;
                }
            }
            return false;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ": " + minValue + " to " + maxValue;
    }
}
