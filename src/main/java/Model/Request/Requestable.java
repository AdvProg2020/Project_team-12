package Model.Request;

import java.io.IOException;

public interface Requestable {
    void acceptRequest() throws Exception;

    void deleteRequest() throws Exception;

}
