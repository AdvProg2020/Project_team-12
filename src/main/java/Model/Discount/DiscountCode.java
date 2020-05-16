package Model.Discount;

import Model.Account.Account;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class DiscountCode extends Discount {
    @Expose
    private String code;
    @Expose
    private int maximumDiscount;
    @Expose
    private int maxUsageNumber;
    @Expose(serialize = false, deserialize = false)
    private ArrayList<Account> allAllowedAccounts = new ArrayList<>();

    public DiscountCode(Date start, Date end, double percent, String ID, String code, int maximumDiscount, int maxUsageNumber, ArrayList<Account> allAllowedAccounts) {
        super(start, end, percent, ID);
        this.code = code;
        this.maximumDiscount = maximumDiscount;
        this.maxUsageNumber = maxUsageNumber;
        this.allAllowedAccounts = allAllowedAccounts;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMaximumDiscount() {
        return maximumDiscount;
    }

    public void setMaximumDiscount(int maximumDiscount) {
        this.maximumDiscount = maximumDiscount;
    }

    public int getMaxUsageNumber() {
        return maxUsageNumber;
    }

    public void setMaxUsageNumber(int maxUsageNumber) {
        this.maxUsageNumber = maxUsageNumber;
    }

    public ArrayList<Account> getAllAllowedAccounts() {
        return allAllowedAccounts;
    }

    public void setAllAllowedAccounts(ArrayList<Account> allAllowedAccounts) {
        this.allAllowedAccounts = allAllowedAccounts;
    }
}
