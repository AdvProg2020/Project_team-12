import Controller.DataBase.DataCenter;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Discount.Auction;
import Model.Discount.Discount;
import Model.Discount.DiscountCode;
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.Review;
import Model.ProductsOrganization.Score;
import Model.Status;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class DataCenterUnitTest {
    @Test
    public void accountDataSaving() throws Exception {
        DataCenter dataCenter = new DataCenter();
        Account[] accounts= new Account[3];
        accounts[0] = new Seller("ali","ali","ali","ali","ali","ali","ali");
        ArrayList<DiscountCode> codes = new ArrayList<>();
        codes.add(dataCenter.getDiscountcodeWithId(13));
        accounts[0].setAllDiscountCodes(codes);
        accounts[1] = new Customer("ali2","ali","ali","ali","ali","ali");
        accounts[1].setAllDiscountCodes(codes);
        accounts[2] = new Manager("ali3","ali","ali","ali","ali","ali");
        accounts[2].setAllDiscountCodes(codes);
        dataCenter.saveAccount((Seller)accounts[0]);
        dataCenter.saveAccount((Customer) accounts[1]);
        dataCenter.saveAccount((Manager) accounts[2]);
        dataCenter = new DataCenter();

        Assert.assertEquals(accounts[0],dataCenter.getAccountByName(accounts[0].getUsername()));
        Assert.assertEquals(accounts[1],dataCenter.getAccountByName(accounts[1].getUsername()));
        Assert.assertEquals(accounts[2],dataCenter.getAccountByName(accounts[2].getUsername()));
    }
    @Test
    public void productDataSaving() throws IOException {
        DataCenter dataCenter = new DataCenter();
        HashMap<String,String> tmp = new HashMap<>();
        tmp.put("first","second");
        Product product = new Product(1, Status.ACCEPTED,"ali","ali",123,tmp,"asdassda",null);
        ArrayList<Review> var1 = new ArrayList<>();
        var1.add(new Review("review description", Review.Status.CONFIRMED,true));
        product.setAllReviews(var1);
        ArrayList<Score> var2 = new ArrayList<>();
        var2.add(new Score(12));
        product.setAllSubmittedScores(var2);
        dataCenter.saveProduct(product);
        Assert.assertEquals(product,dataCenter.getProductById(1));
        Assert.assertEquals(product,dataCenter.getProductByName("ali"));
        dataCenter = new DataCenter();
        Assert.assertEquals(product,dataCenter.getProductById(1));
    }
    @Test
    public void discountDataSaving() throws Exception {
        DataCenter dataCenter = new DataCenter();
        Discount[] discounts = new Discount[2];
        ArrayList<Product> products = new ArrayList<>();
        products.add(dataCenter.getProductByName("ali"));
        discounts[0] = new Auction(new Date(0),new Date(0),12,12,products,Status.ACCEPTED);
        Account[] accounts= new Account[3];
        accounts[0] = dataCenter.getAccountByName("ali");
        accounts[1] = dataCenter.getAccountByName("ali2");
        accounts[2] = dataCenter.getAccountByName("ali3");
        ArrayList<Account> accounts1 = new ArrayList(Arrays.asList(accounts));
        discounts[1] = new DiscountCode(new Date(0),new Date(0),12,13,"string code",12,13, accounts1);
        dataCenter.saveDiscount(discounts[0]);
        dataCenter.saveDiscount(discounts[1]);
        dataCenter = new DataCenter();
        Assert.assertEquals(discounts[0],dataCenter.getAuctionWithId(12));
        Assert.assertEquals(discounts[1],dataCenter.getDiscountcodeWithId(13));
    }

}
