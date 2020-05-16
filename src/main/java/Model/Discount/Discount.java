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
    protected String ID;

    public Discount(Date start, Date end, double percent, String ID) {
        this.start = start;
        this.end = end;
        this.percent = percent;
        this.ID = ID;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public abstract double calculatePrice(Double amount);
}