package Model.Account;

public class Manager extends Account{
    //if you added anything to this part, just notify me to tell you which ones is for expose true
    public Manager(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {
        super(username, firstName, lastName, emailAddress, phoneNumber, password);
    }

    public String toString() {
        return null;
    }

    public void writeInfoInFile() {

    }
}
