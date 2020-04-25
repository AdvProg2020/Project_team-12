package Model.Discount;

import java.util.Date;

public class Discount {
    protected Date start;
    protected Date end;
    protected double percent;

    public Discount(Date start, Date end, double percent) {
        this.start = start;
        this.end = end;
        this.percent = percent;
    }
}
