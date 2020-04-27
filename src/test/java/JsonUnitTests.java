import Controller.DataBase.Json.JsonFileReader;
import Controller.DataBase.Json.JsonFileWriter;
import Model.Account.Customer;
import Model.Discount.Discount;
import Model.Discount.DiscountCode;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class JsonUnitTests {
    @Test
    public void test() {
        JsonFileWriter test = new JsonFileWriter();
        DiscountCode discountCode = new DiscountCode(new Date(),new Date(),12,"alinajibi",12,12);
        discountCode.addAllowedAccount(new Customer("A","A","A","A","A","A"));
        try {
            test.write(discountCode,"resources");
            JsonFileReader test2 = new JsonFileReader();
            DiscountCode code = test2.read("resources",DiscountCode.class);
            System.out.println(discountCode.toString());
        }catch (IOException var1){
            System.out.println("file not added");
        }
    }
}
