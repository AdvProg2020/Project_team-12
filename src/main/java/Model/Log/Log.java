package Model.Log;

import com.google.gson.annotations.Expose;

import java.util.Date;

public abstract class Log {
    @Expose
    private Date date;
    @Expose
    private String id;
    public Log(Date date, String id) {
        this.date = date;
        this.id = id;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Log{" +
                "date=" + date +
                ", id='" + id + '\'' +
                '}';
    }
}