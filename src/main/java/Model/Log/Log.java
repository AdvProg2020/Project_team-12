package Model.Log;

import com.google.gson.annotations.Expose;

import java.util.Date;

public abstract class Log {
    @Expose
    private int id;
    @Expose
    private Date date;

    public Log(int id, Date date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
