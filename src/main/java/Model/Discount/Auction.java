package Model.Discount;

import Model.ProductsOrganization.Product;

import java.util.ArrayList;
import java.util.Date;

public class Auction {
    private String ID;
    private ArrayList<Product> allProducts;
    private AuctionStatus status;
    private Date start;
    private Date end;
    private double discountPercent;
}
