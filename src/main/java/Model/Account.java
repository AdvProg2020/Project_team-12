package Model;

import Server.DataCenter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Account {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String Username;
    private String Password;
    private long accountNumber;
    private AuthToken AuthToken;
    private double balance = 0;

    public Account(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        Username = username;
        Password = password;
        accountNumber = accounts.size();

    }

    public static Account add(Account account) {
        if (!accounts.contains(account))
            accounts.add(account);
        return account;
    }

    public static boolean accountExist(String username) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getUsername().equals(username))
                return true;
        }
        return false;
    }


    public static @NotNull Account getAccount(String username, String password) throws Exception {
        for (Account account : accounts) {
            if (account.getUsername().equals(username))
                if (account.getPassword().equals(password))
                    return account;
        }
        throw new Exception("invalid username or password");
    }

    @Contract(pure = true)
    public static @NotNull Account getAccount(long id) throws Exception {
        for (Account account : accounts) {
            if (account.accountNumber == id)
                return account;
        }
        throw new Exception("invalid account id");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber == account.accountNumber &&
                Username.equals(account.Username) &&
                Password.equals(account.Password);
    }



    public String getUsername() {
        return Username;
    }


    public String getPassword() {
        return Password;
    }


    public long getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double money) {
        this.balance += money;
        DataCenter.save(this);
    }

    public void withdraw(double money) {
        this.balance -= money;
        DataCenter.save(this);
    }
}
