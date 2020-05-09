package Model.Discount;

import com.google.gson.annotations.Expose;

import java.util.Date;

public abstract class Discount {
    @Expose
    protected Date start;
    @Expose
    protected Date end;
    @Expose
    protected double percent;

    public Discount(Date start, Date end, double percent) {
        this.start = start;
        this.end = end;
        this.percent = percent;
    }
}