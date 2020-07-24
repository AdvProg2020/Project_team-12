package Model;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

public class AuthToken {
    private static final long timeout = 1000 * 60 * 60;
    private static ArrayList<AuthToken> authTokens = new ArrayList<>();
    private Account account;
    private String token;
    private boolean expired = false;

    public AuthToken(Account account) {
        this.account = account;
        generateToken();
        startTimer();
        authTokens.add(this);
    }

    public static @NotNull AuthToken getToken(String authToken) throws TokenException {
        for (AuthToken token : authTokens) {
            if (token.token.equals(authToken))
                if (token.expired)
                    throw new TokenException("token expired");
                else return token;
        }
        throw new TokenException("token is invalid");
    }

    public static @NotNull AuthToken getNewToken(Account account) {
        for (AuthToken authToken : authTokens) {
            if (authToken.account.equals(account))
                if (authToken.expired) {
                    authTokens.remove(authToken);
                    break;
                } else
                    return authToken;
        }
        return new AuthToken(account);
    }


    private void startTimer() {
        new Thread(() -> {
            try {
                Thread.sleep(timeout);
                expired = true;
            } catch (InterruptedException e) {

            }
        }).start();
    }

    private void generateToken() {
        byte[] randomBytes = new byte[24];
        new SecureRandom().nextBytes(randomBytes);
        token = Base64.getUrlEncoder().encodeToString(randomBytes);
    }

    public Account getAccount() {
        return account;
    }


    public String getToken() {
        return token;
    }

}
