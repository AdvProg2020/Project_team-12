package Model.Discount;

import Model.Account.Account;

import java.util.ArrayList;
import java.util.Date;

public class DiscountCode {
    private String code;
    private Date start;
    private Date end;
    private double percent;
    private double maxDiscount;
    private int usageNumber;
    private ArrayList<Account> allowedAccounts;
}
