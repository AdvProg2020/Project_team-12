package Model.Discount;

import Model.Account.Account;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;

public class DiscountCode extends Discount {
    @Expose
    private String code;
    @Expose
    private int maximumDiscountAmount;
    @Expose
    private int maximumNumberOfUsages;
    @Expose(serialize = false, deserialize = false)
    private ArrayList<Account> allAllowedAccounts = new ArrayList<>();

    public DiscountCode(Date start, Date end, double percent, String code, int maximumDiscountAmount, int maximumNumberOfUsages) {
        super(start, end, percent);
        this.code = code;
        this.maximumDiscountAmount = maximumDiscountAmount;
        this.maximumNumberOfUsages = maximumNumberOfUsages;
    }

    public void addAllowedAccount(Account account){
        allAllowedAccounts.add(account);
    }
}
