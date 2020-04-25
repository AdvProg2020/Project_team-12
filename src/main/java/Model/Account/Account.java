package Model.Account;

import Model.Discount.DiscountCode;

import java.util.ArrayList;

public abstract class Account {
    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private ArrayList<DiscountCode> allDiscountCodes = new ArrayList<DiscountCode>();
    private double credit;


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