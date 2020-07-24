package Server;

import Model.Account;
import Model.AuthToken;
import Model.Receipt;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class RequestHandler {

    public String executeRequest(String request) throws Exception {
        try {
            Matcher requestMatcher;
            if ((requestMatcher = Patterns.createAccount.matcher(request)).find()) {
                return this.createAccount(requestMatcher);
            } else if ((requestMatcher = Patterns.createReceipt.matcher(request)).find()) {
                return this.createReceipt(requestMatcher);
            } else if ((requestMatcher = Patterns.getBalance.matcher(request)).find()) {
                return this.getBalance(requestMatcher);
            } else if ((requestMatcher = Patterns.getToken.matcher(request)).find()) {
                return this.getToken(requestMatcher);
            } else if ((requestMatcher = Patterns.getTransactions.matcher(request)).find()) {
                return this.getTransactions(requestMatcher);
            } else if ((requestMatcher = Patterns.payReceipt.matcher(request)).find()) {
                return this.payReceipt(requestMatcher);
            } else if (request.contains("create_receipt")) {
                return ("invalid parameter passed");
            } else
                throw new BadRequestException("invalid input");
        } catch (Exception var3) {
            return var3.getMessage();
        }
    }


    private String payReceipt(@NotNull Matcher requestMatcher) throws Exception {
        return payReceipt(requestMatcher.group(1));
    }

    private String payReceipt(String receiptID) throws Exception {
        int id;
        try {
            id = Integer.parseInt(receiptID);
        } catch (NumberFormatException e) {
            throw new Exception("invalid receipt id");
        }
        Receipt receipt = Receipt.getReceipt(id);
        if (receipt.isPaid())
            throw new Exception("receipt is paid before");
        return payReceipt(receipt);
    }

    private String payReceipt(@NotNull Receipt receipt) throws Exception {
        if (receipt.getSourceId() == -1)
            return deposit(receipt);
        else if (receipt.getDestId() == -1)
            return withdraw(receipt);
        else if (!(Account.getAccount(receipt.getSourceId()).getBalance() >= receipt.getMoney()))
            throw new Exception("source account does not have enough money");
        return doTransaction(receipt);
    }

    private @NotNull String doTransaction(@NotNull Receipt receipt) throws Exception {
        Account.getAccount(receipt.getSourceId()).withdraw(receipt.getMoney());
        Account.getAccount(receipt.getDestId()).deposit(receipt.getMoney());
        receipt.setPaid(true);
        return "done successfully";
    }

    private @NotNull String withdraw(@NotNull Receipt receipt) throws Exception {
        Account.getAccount(receipt.getSourceId()).withdraw(receipt.getMoney());
        receipt.setPaid(true);
        return "done successfully";
    }

    private @NotNull String deposit(@NotNull Receipt receipt) throws Exception {
        Account.getAccount(receipt.getDestId()).deposit(receipt.getMoney());
        receipt.setPaid(true);
        return "done successfully";
    }

    private String getTransactions(@NotNull Matcher requestMatcher) {
        String authToken = requestMatcher.group(1);
        String type = requestMatcher.group(2);
        return getTransactions(authToken, type);
    }

    private String getTransactions(String authToken, String type) {
        try {
            AuthToken var10 = authTokenQualifier(authToken);
            if (type.matches("\\+"))
                return getDestLogs(var10);
            else if (type.matches("-"))
                return getSourceLogs(var10);
            else if (type.matches("\\*"))
                return getAllLogs(var10);
            else try {
                    int id = Integer.parseInt(type);
                    return Receipt.getReceipt(id).toString();
                } catch (NumberFormatException e) {
                    return "invalid receipt id";
                }
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    private @NotNull String getSourceLogs(AuthToken var10) {
        String out = "";
        for (Receipt receipt : Receipt.getSourceReceipts(var10)) {
            out += receipt.toString() + "*";
        }
        return out.substring(0, out.length() - 1);
    }

    private @NotNull String getDestLogs(AuthToken var10) {
        String out = "";
        for (Receipt receipt : Receipt.getDestReceipts(var10)) {
            out += receipt.toString() + "*";
        }
        return out.substring(0, out.length() - 1);
    }

    private @NotNull String getAllLogs(AuthToken authToken) {
        String out = "";
        for (Receipt receipt : Receipt.getReceipts(authToken)) {
            out += receipt.toString() + "*";
        }
        return out.substring(0, out.length() - 1);
    }

    private @NotNull AuthToken authTokenQualifier(String authToken) throws Exception {
        return AuthToken.getToken(authToken);
    }

    private String getToken(@NotNull Matcher requestMatcher) {
        String username = requestMatcher.group(1);
        String password = requestMatcher.group(2);
        return getToken(username, password);
    }

    private String getToken(String username, String password) {
        try {
            Account account = Account.getAccount(username, password);
            return AuthToken.getNewToken(account).getToken();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String getBalance(@NotNull Matcher requestMatcher) {
        String token = requestMatcher.group(1);
        return getBalance(token);
    }

    private String getBalance(String token) {
        try {
            return String.valueOf(authTokenQualifier(token).getAccount().getBalance());
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    private @NotNull String createAccount(@NotNull Matcher requestMatcher) {
        if (Account.accountExist(requestMatcher.group(3)))
            return "username is not available";
        else if (!requestMatcher.group(4).equals(requestMatcher.group(5)))
            return "passwords do not match";
        else
            return String.valueOf(Account.add(DataCenter.save(new Account(requestMatcher.group(1), requestMatcher.group(2), requestMatcher.group(3), requestMatcher.group(4)))).getAccountNumber());

    }

    private String createReceipt(@NotNull Matcher requestMatcher) throws Exception {
        String authToken = requestMatcher.group(1);
        authTokenQualifier(authToken);
        String receiptType = requestMatcher.group(2).trim();
        String description = requestMatcher.group(6);
        if (!description.matches("\\w*"))
            return "your input contains invalid characters";
        double money = changeToMoney(requestMatcher.group(3));
        long sourceId = changeToId(requestMatcher.group(4), "source account id is invalid");
        long destId = changeToId(requestMatcher.group(5), "dest account id is invalid");
        if (sourceId == destId)
            throw new Exception("equal source and dest account");
        return createReceipt(authToken, receiptType, description, money, sourceId, destId);
    }

    private String createReceipt(String authToken, @NotNull String receiptType, String description, double money, long sourceId, long destId) throws Exception {
        if (receiptType.equals("deposit"))
            return deposit(description, money, sourceId, destId);
        if (receiptType.equals("withdraw"))
            return withdraw(description, money, sourceId, destId);
        if (receiptType.equals("move"))
            return move(description, money, sourceId, destId);
        throw new Exception("invalid receipt type");
    }

    private @NotNull String move(String description, double money, long sourceId, long destId) throws Exception {
        if (destId < 0)
            return "dest account id is invalid";
        if (sourceId < 0)
            return "source account id is invalid";
        accountIdQualifier(sourceId, destId);
        return String.valueOf(DataCenter.save(Receipt.add(new Receipt("move", money, sourceId, destId, description))).getId());

    }

    private void accountIdQualifier(long sourceId, long destId) throws Exception {
        accountIdQualifier(sourceId, "source account id is invalid");
        accountIdQualifier(destId, "dest account id is invalid");
    }

    private void accountIdQualifier(long id, String source_account_id_is_invalid) throws Exception {
        try {
            Account.getAccount(id);
        } catch (Exception e) {
            throw new Exception(source_account_id_is_invalid);
        }

    }

    private @NotNull String withdraw(String description, double money, long sourceId, long destId) throws Exception {
        if (destId != -1)
            return "dest account id is invalid";
        if (sourceId < 0)
            return "source account id is invalid";
        accountIdQualifier(sourceId, "source account id is invalid");
        return String.valueOf(DataCenter.save(Receipt.add(new Receipt("withdraw", money, sourceId, destId, description))).getId());
    }

    private @NotNull String deposit(String description, double money, long sourceId, long destId) throws Exception {
        if (sourceId != -1)
            return "source account id is invalid";
        if (destId < 0)
            return "dest account id is invalid";
        accountIdQualifier(destId, "dest account id is invalid");
        return String.valueOf(DataCenter.save(Receipt.add(new Receipt("deposit", money, sourceId, destId, description))).getId());
    }

    private long changeToId(String source, String message) throws Exception {
        long sourceId;
        try {
            sourceId = Long.parseLong(source);
            if (sourceId < -1)
                throw new Exception(message);
        } catch (NumberFormatException e) {
            throw new Exception(message);
        }
        return sourceId;
    }

    private double changeToMoney(String money) throws Exception {
        double out;
        try {
            out = Double.parseDouble(money);
            if (out < 0) {
                throw new Exception("invalid money");
            }
        } catch (NumberFormatException e) {
            throw new Exception("invalid money");
        }
        return out;
    }

    private static class BadRequestException extends Exception {
        public BadRequestException(String s) {
            super(s);
        }
    }
}
