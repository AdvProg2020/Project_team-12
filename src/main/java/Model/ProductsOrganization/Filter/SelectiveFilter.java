package Model.ProductsOrganization.Filter;

import Model.Account.Seller;
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Product;

import java.util.ArrayList;

public class SelectiveFilter implements Filter {
    private String name;
    private ArrayList<String> selectedValues = new ArrayList<>();

    public SelectiveFilter(String name, ArrayList<String> selectedValues) {
        this.name = name;
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
                for (String seller : product.getAllSellers())
                    if (selectedValues.contains(seller))
                        return true;
                return false;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String string = name + ":";
        for (String value : selectedValues)
            string += " " + value;
        return string;
    }
}
