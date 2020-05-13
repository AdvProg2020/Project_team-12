package Model.Request;

import java.io.IOException;

public interface Requestable {
    void AcceptRequest() throws Exception;

    void deleteRequest() throws IOException;

}
