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

    public DiscountCode(Date start, Date end, double percent, int id, String code, int maximumDiscountAmount, int maximumNumberOfUsages, ArrayList<Account> allAllowedAccounts) {
        super(start, end, percent, id);
        this.code = code;
        this.maximumDiscountAmount = maximumDiscountAmount;
        this.maximumNumberOfUsages = maximumNumberOfUsages;
        this.allAllowedAccounts = allAllowedAccounts;
    }

    public void addAllowedAccount(Account account){
        allAllowedAccounts.add(account);
    }

    public void setAllAllowedAccounts(ArrayList<Account> allAllowedAccounts) {
        this.allAllowedAccounts = allAllowedAccounts;
    }

    public ArrayList<Account> getAllAllowedAccounts() {
        return allAllowedAccounts;
    }
}
