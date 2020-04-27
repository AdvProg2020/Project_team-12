package Model.ProductsOrganization;

import Model.Account.Customer;
import com.google.gson.annotations.Expose;

public class Score {
    @Expose(serialize = true)
    private double score;

    public Score(Customer customer, double score, Product product) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{}";
    }
}
