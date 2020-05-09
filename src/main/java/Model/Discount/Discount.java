package Model.Discount;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.Objects;

public abstract class Discount {
    @Expose
    protected Date start;
    @Expose
    protected Date end;
    @Expose
    protected double percent;
    @Expose
    protected int id;

    public Discount(Date start, Date end, double percent, int id) {
        this.start = start;
        this.end = end;
        this.percent = percent;
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public double getPercent() {
        return percent;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Double.compare(discount.percent, percent) == 0 &&
                id == discount.id &&
                start.equals(discount.start) &&
                end.equals(discount.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, percent, id);
    }
}