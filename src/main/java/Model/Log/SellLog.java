package Model.Log;

import Model.ProductsOrganization.ProductOnLog;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;

public class SellLog extends Log {
    @Expose(serialize = true)
    private int receivedCredit;
    @Expose(serialize = true)
    private int decreasedPriceAtAuction;
    @Expose(serialize = true)
    private ArrayList<ProductOnLog> allSoldProducts;
    /*@Expose(serialize = true)
    private Status status;*///I dont know what you want this for but if its necessary, uncomment it.

    public SellLog(int id, Date date) {
        super(id, date);
    }

    @Override
    public String toString() {
        return "SellLog{}";
    }



    private enum Status {SENT, TO_BE_SENT}
}
