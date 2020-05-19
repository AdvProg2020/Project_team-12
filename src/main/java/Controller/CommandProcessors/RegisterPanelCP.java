package Controller.CommandProcessors;

import Model.Account.Account;
import Model.Account.CanRequest;
import Model.Account.Customer;
import Model.Account.Seller;
import Model.Request.Request;
import Model.Request.SellerRequest;
import View.Exceptions.RegisterPanelException;

public class RegisterPanelCP extends CommandProcessor {
    private static CommandProcessor Instance;

    protected RegisterPanelCP() {
        super(MainMenuCP.getInstance());
    }

    public static CommandProcessor getInstance() {
        if (Instance == null)
            Instance = new RegisterPanelCP();
        return Instance;
    }

    public void createAccount(String username, String role, String password, String name, String lastName, String phoneNumber, String emailAddress, String companyInfo) throws Exception {
        Account newAccount;
        if (role.equals("customer")) {
            newAccount = new Customer(username, name, lastName, emailAddress, phoneNumber, password);
            dataCenter.saveAccount((Customer) newAccount);
            dataCenter.addAccount(newAccount);
        } else if (role.equals("seller")) {
            newAccount = new Seller(username, name, lastName, emailAddress, phoneNumber, password, companyInfo);
            dataCenter.addAccount(newAccount);
            Request request = new SellerRequest(dataCenter.requestIDGenerator((CanRequest) newAccount), false, newAccount.getUsername());
            dataCenter.addRequest(request);
            dataCenter.saveRequest(request);
            dataCenter.saveAccount(newAccount);
        }
    }


    public void login(String username, String password) throws Exception {
        if (getLoggedInAccount() != null)
            throw new RegisterPanelException("already logged in!!");
        setLoggedInAccount(dataCenter.getAccountByName(username));
        if (!checkPassword(password)) {
            setLoggedInAccount(null);
            throw new RegisterPanelException("incorrect password");
        }else if ((dataCenter.getAccountByName(username) instanceof Seller &&
                !((Seller) dataCenter.getAccountByName(username)).isAccountTypeAccepted())){
            setLoggedInAccount(null);
            throw new RegisterPanelException("your account is being checked by manager.");
        }

    }


    public boolean doesUsernameExists(String username) {
        return dataCenter.userExistWithUsername(username);
    }


    public boolean checkPassword(String password) {
        if (getLoggedInAccount().getPassword().equals(password))
            return true;
        else
            return false;
    }
}
