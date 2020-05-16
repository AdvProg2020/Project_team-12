package Model.Log;

import com.google.gson.annotations.Expose;

import java.util.Date;

public abstract class Log {
    @Expose
    private Date date;

    public Log( Date date) {
        this.date = date;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}