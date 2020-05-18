package Model.ProductsOrganization;

import Model.Account.Customer;
import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Score {
    @Expose
    private double score;

    public Score( double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return Double.compare(score1.score, score) == 0;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }

    @Override
    public String toString() {
        return "Score{" +
                "score=" + score +
                '}';
    }
}
