package Model.Account;

import Model.Log.Log;
import Model.Log.SellLog;

import java.util.ArrayList;

public class Seller extends Account {
    private String companyInformation;
    private ArrayList<SellLog> sellLogs = new ArrayList<>();
    public Seller(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String  password, String companyInformation) {
        super(username, firstName, lastName, emailAddress, phoneNumber, password);
        this.companyInformation = companyInformation;
    }

    @Override
    public void writeInfoInFile() {

    }

    @Override
    public String toString() {
        return null;
    }
}
