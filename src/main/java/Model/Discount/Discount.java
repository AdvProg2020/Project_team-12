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
}
