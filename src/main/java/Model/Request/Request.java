package Model.Request;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public abstract class Request implements Requestable{
    @Expose
    protected int id;
    @Expose
    protected String senderUserName;


    public Request(String sender, int id, String text, boolean active) {
        this.senderUserName = sender;
        this.id = id;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id &&
                senderUserName.equals(request.senderUserName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senderUserName);
    }

    public int getId() {
        return id;
    }
}
