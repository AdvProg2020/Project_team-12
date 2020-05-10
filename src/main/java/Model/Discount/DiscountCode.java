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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DiscountCode that = (DiscountCode) o;
        return maximumDiscountAmount == that.maximumDiscountAmount &&
                maximumNumberOfUsages == that.maximumNumberOfUsages &&
                code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code, maximumDiscountAmount, maximumNumberOfUsages, allAllowedAccounts);
    }
}
