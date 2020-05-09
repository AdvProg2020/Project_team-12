package Controller.CommandProcessors;

import Model.Account.*;
import View.Exceptions.InvalidCommandException;
import View.Exceptions.RegisterPanelException;

public class TestCommandProcessor {
    Account loggedInAccount;

    public TestCommandProcessor() {
        this.loggedInAccount = null;
    }

    public String getProfileType() {
        if (loggedInAccount instanceof Customer)
            return "customer";
        else if (loggedInAccount instanceof Seller)
            return "seller";
        else if (loggedInAccount instanceof Manager)
            return "manager";
        else
            return "not logged in";
    }

    public void setLoggedInAccount(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public void createAccount(String username, String role, String password, String name, String lastName, String phoneNumber, String emailAddress)throws Exception{

        //write account info in data base
        //create new account
        //set loggedInAccount

    }

    public void login(String username, String password) throws Exception{
        if (!checkPassword(password));
            throw new RegisterPanelException("incorrect password");

        //read files
        //get account object with username
        //set loggedInAccount by username

    }


    public boolean doesUsernameExists(String username){
        //check if username exists
        return true;
    }

    public boolean checkPassword(String password){
        //check if password is true
        return true;
    }

    public String getPersonalInfo(){
        String personalInfo = "";

        //read files and get personal info og loggedInAccount

        return personalInfo;
    }

    public void editPersonalInfo(String field, String newValue) throws Exception{
        /*String password = getField("password", "\\S+");
                String firstName = getField("first name", "\\w+");
                String lastName = getField("last name", "\\w+");
                String emailAddress = getField("email address", "(\\w+)@(\\w+)\\.(\\w+)$");
                String phoneNumber = getField("phone number", "(\\d+)$");*/
        if ((field.equals("first name") || field.equals("last name")) && !newValue.matches("\\w+")) {
            throw new InvalidCommandException("invalid field input");
        } else if (field.equals("phone number") && !newValue.matches("(\\d+)$")) {
            throw new InvalidCommandException("invalid field input");
        }
        //change the field in loggedInAccount
        //write new object in file
    }
}
