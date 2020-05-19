package Model.Account;

import java.util.ArrayList;

public interface CanRequest {
    public void deleteRequestWithId(String id);

    public ArrayList<String> getSolvedRequests();

    public void setSolvedRequests(ArrayList<String> solvedRequests);
    public ArrayList<String> getActiveRequestsId();
}
