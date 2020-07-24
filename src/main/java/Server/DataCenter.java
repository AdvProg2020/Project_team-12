package Server;

import Json.JsonFileReader;
import Json.JsonFileWriter;
import Model.Account;
import Model.Receipt;
import logger.LogLevel;
import logger.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class DataCenter {
    private static final String accountsPath = "resources\\accounts";
    private static final String logsPath = "resources\\logs";
    public static Logger logger;

    public static void setLogger(Logger logger) {
        DataCenter.logger = logger;
    }

    public static void initFromFile() {
        JsonFileReader reader = new JsonFileReader();
        initAccounts(reader);
        initLogs(reader);
    }

    private static void initLogs(JsonFileReader reader) {
        File var10 = new File(logsPath);
        if (!var10.exists())
            var10.mkdirs();
        File[] var11 = var10.listFiles();
        if (var11 != null)
            for (int i = 0; i < var11.length; i++) {
                initLogsEach(var11[i], reader);
            }
    }

    private static void initLogsEach(File file, @NotNull JsonFileReader reader) {
        try {
            reader.read(file, Receipt.class);

        } catch (FileNotFoundException e) {
            handleError(e);
        }
    }

    private static void initAccountsEach(File file, @NotNull JsonFileReader reader) {
        try {
            Account.add(reader.read(file, Account.class));
        } catch (FileNotFoundException e) {
            handleError(e);
        }
    }

    private static void initAccounts(JsonFileReader reader) {
        File var10 = new File(accountsPath);
        if (!var10.exists())
            var10.mkdirs();
        File[] var11 = var10.listFiles();
        if (var11 != null)
            for (int i = 0; i < var11.length; i++) {
                initAccountsEach(var11[i], reader);
            }
    }

    private static <T extends Exception> void handleError(@NotNull T e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        logger.log(LogLevel.Error, sw.toString());
    }

    public static Account save(Account account) {
        try {
            new JsonFileWriter().write(account, accountPathGenerator(account));
        } catch (IOException e) {
            handleError(e);
        } finally {
            return account;
        }
    }

    public static Receipt save(Receipt receipt) {
        try {
            new JsonFileWriter().write(receipt, logsPathGenerator(receipt));

        } catch (IOException e) {
            handleError(e);
        } finally {
            return receipt;
        }
    }

    private static @NotNull String accountPathGenerator(@NotNull Account account) {
        return accountsPath + "\\" + account.getUsername() + ".account";
    }

    private static @NotNull String logsPathGenerator(@NotNull Receipt receipt) {
        return logsPath + "\\" + receipt.getId() + ".log";
    }


}
