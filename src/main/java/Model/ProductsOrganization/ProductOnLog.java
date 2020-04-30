package Model.ProductsOrganization;

import com.google.gson.annotations.Expose;

public class ProductOnLog {
    @Expose
    private final String name;
    @Expose
    private final int price;
    @Expose
    private final String sellerOrBuyer;

    public ProductOnLog(String name, int price, String seller) {
        this.name = name;
        this.price = price;
        this.sellerOrBuyer = seller;
    }
}
