package Controller.CommandProcessors;

import Controller.DataBase.DataCenter;
import Model.Account.*;
import View.Exceptions.InvalidCommandException;
import View.Exceptions.RegisterPanelException;

public class TestCommandProcessor {
    private Account loggedInAccount;
    private DataCenter dataCenter;

    public TestCommandProcessor() {
        this.loggedInAccount = null;
        this.dataCenter = new DataCenter();
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
        Account newAccount;
        if (role.equals("customer")) {
            newAccount = new Customer(username, name, lastName, emailAddress, phoneNumber, password);
            dataCenter.saveAccount((Customer) newAccount);
        }else if (role.equals("seller")){
            //send a request to manager
        }
    }

    public void login(String username, String password) throws Exception{
        setLoggedInAccount(dataCenter.getAccountByName(username));
        if (!checkPassword(password)) {
            setLoggedInAccount(null);
            throw new RegisterPanelException("incorrect password");
        }
    }

    public boolean doesUsernameExists(String username){
        return dataCenter.doesUsernameExist(username);
    }


    public boolean checkPassword(String password){

        //check this.loggedInAccount.Password

        return true;
    }

    public String getPersonalInfo(){
        String personalInfo = "";

        //this.loggedInAccount.getPersonalInfo

        return personalInfo;
    }

    public void editPersonalInfo(String field, String newValue) throws Exception{
        if ((field.equals("first name") || field.equals("last name")) && !newValue.matches("\\w+")) {
            throw new InvalidCommandException("illegal field input");
        } else if (field.equals("phone number") && !newValue.matches("(\\d+)$")) {
            throw new InvalidCommandException("invalid field input");
        } else if (field.equals("email address") && !newValue.matches("(\\w+)@(\\w+)\\.(\\w+)$")) {
            throw new InvalidCommandException("invalid field input");
        } else if (field.equals("password") && !newValue.matches("\\S+")) {
            throw new InvalidCommandException("invalid field input");
        }
        //change the field in loggedInAccount
        //write new object in file
        //or create new object -> delete previous version and save new object
    }
}
