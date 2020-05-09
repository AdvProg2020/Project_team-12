package Model.Account;

import Model.Discount.DiscountCode;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Objects;

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
    private ArrayList<DiscountCode> allDiscountCodes;



    public Account(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        allDiscountCodes = new ArrayList<DiscountCode>();
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", credit=" + credit +
                ", allDiscountCodes=" + allDiscountCodes +
                '}';
    }

    public abstract void writeInfoInFile();

    public void addDiscountCode(DiscountCode discountCode){
        allDiscountCodes.add(discountCode);
    }

    public void setAllDiscountCodes(ArrayList<DiscountCode> allDiscountCodes) {
        this.allDiscountCodes = allDiscountCodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.credit, credit) == 0 &&
                username.equals(account.username) &&
                firstName.equals(account.firstName) &&
                lastName.equals(account.lastName) &&
                emailAddress.equals(account.emailAddress) &&
                phoneNumber.equals(account.phoneNumber) &&
                password.equals(account.password) &&
                Objects.equals(allDiscountCodes, account.allDiscountCodes);
    }

}