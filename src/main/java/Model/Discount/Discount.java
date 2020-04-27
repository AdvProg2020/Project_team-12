package Model.Discount;

import com.google.gson.annotations.Expose;

import java.util.Date;

public abstract class Discount {
    @Expose(serialize = true)
    protected Date start;
    @Expose(serialize = true)
    protected Date end;
    @Expose(serialize = true)
    protected double percent;

    public Discount(Date start, Date end, double percent) {
        this.start = start;
        this.end = end;
        this.percent = percent;
    }
}
