import Controller.DataBase.DataCenter;
import Model.Account.Seller;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class DataCenterUnitTest {
    @Test
    public void testDataSaving() throws IOException {
        DataCenter dataCenter = new DataCenter();
        dataCenter.saveAccount(new Seller("ali","ali","ali","ali","ali","ali","ali"));
        dataCenter = new DataCenter();
        Assert.assertEquals(dataCenter.getAccountByName("ali").toString(),new Seller("ali","ali","ali","ali","ali","ali","ali").toString());
    }
}
