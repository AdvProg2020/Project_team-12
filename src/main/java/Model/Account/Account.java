package Model.Account;

import Model.Discount.DiscountCode;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public abstract class Account {
    @Expose(serialize = true)
    private String username;
    @Expose(serialize = true)
    private String firstName;
    @Expose(serialize = true)
    private String lastName;
    @Expose(serialize = true)
    private String emailAddress;
    @Expose(serialize = true)
    private String phoneNumber;
    @Expose(serialize = true)
    private String password;
    @Expose(serialize = true)
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
}