package Model.Account;

import Model.Discount.DiscountCode;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public abstract class Account {
    @Expose
    private String username;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String emailAddress;
    @Expose
    private String phoneNumber;
    @Expose
    private String password;
    @Expose
    private double credit;
    @Expose(serialize = false, deserialize = false)
    private ArrayList<DiscountCode> allDiscountCodes = new ArrayList<DiscountCode>();



    public Account(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public abstract String toString();

    public abstract void writeInfoInFile();

    public void addDiscountCode(DiscountCode discountCode){
        allDiscountCodes.add(discountCode);
    }
}