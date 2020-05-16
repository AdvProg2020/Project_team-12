package Model.ProductsOrganization.Filter;

import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Product;

import java.util.ArrayList;

public class SelectiveFilter implements Filterable {
    private String name;
    private ArrayList<String> selectedValues;

    public SelectiveFilter(String name, ArrayList<String> selectedValues) {
        this.name = name;
        this.selectedValues = new ArrayList<>();
        this.selectedValues.addAll(selectedValues);
    }

    public boolean canPassFilter(Product product) {
        switch (name) {
            case "Category":
                Category category = product.getParent();
                while (category != null) {
                    if (selectedValues.contains(category.getName()))
                        return true;
                    category = category.getParent();
                }
                return false;
            case "Name":
                return selectedValues.contains(product.getName());
            case "Brand":
                return selectedValues.contains(product.getBrand());
            case "Seller":
                return selectedValues.contains(product.getSeller());
            case "Availability":
                return product.getRemainingItems() > 0;
            default:
                if (product.getSpecs().containsKey(name)) {
                    String value = product.getSpecs().get(name);
                    return selectedValues.contains(value);
                }
                return false;
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String string = name + ":";
        for (String value : selectedValues)
            string += (" " + value);
        return string;
    }
}
