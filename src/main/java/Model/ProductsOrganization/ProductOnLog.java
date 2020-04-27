package Model.ProductsOrganization;

import com.google.gson.annotations.Expose;

public class ProductOnLog {
    @Expose(serialize = true)
    private final String name;
    @Expose(serialize = true)
    private final int price;
    @Expose(serialize = true)
    private final String sellerOrBuyer;

    public ProductOnLog(String name, int price, String seller) {
        this.name = name;
        this.price = price;
        this.sellerOrBuyer = seller;
    }
}
