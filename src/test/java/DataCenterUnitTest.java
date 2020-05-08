import Controller.DataBase.DataCenter;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.Review;
import Model.ProductsOrganization.Score;
import Model.Status;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataCenterUnitTest {
    @Test
    public void testDataSaving() throws IOException {
        DataCenter dataCenter = new DataCenter();
        Account[] accounts= new Account[3];
        accounts[0] = new Seller("ali","ali","ali","ali","ali","ali","ali");
        accounts[1] = new Customer("ali2","ali","ali","ali","ali","ali");
        accounts[2] = new Manager("ali3","ali","ali","ali","ali","ali");
        dataCenter.saveAccount((Seller)accounts[0]);
        dataCenter.saveAccount((Customer) accounts[1]);
        dataCenter.saveAccount((Manager) accounts[2]);
        dataCenter = new DataCenter();
        Assert.assertEquals(accounts[0].toString(),dataCenter.getAccountByName(accounts[0].getUsername()).toString());
        Assert.assertEquals(accounts[1].toString(),dataCenter.getAccountByName(accounts[1].getUsername()).toString());
        Assert.assertEquals(accounts[2].toString(),dataCenter.getAccountByName(accounts[2].getUsername()).toString());
        HashMap<String,String> tmp = new HashMap<>();
        tmp.put("first","second");
        Product product = new Product(1, Status.ACCEPTED,"ali","ali",123,tmp,"asdassda",new Category("ali",null));
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


}
