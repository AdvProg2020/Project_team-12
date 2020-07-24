package Server;

import java.util.regex.Pattern;

interface Patterns {
     Pattern createAccount = Pattern.compile("^create_account\\s(\\S+)\\s(\\S+)\\s(\\S+)\\s(.+)\\s(.+)$");
     Pattern getToken = Pattern.compile("^get_token\\s(\\S+)\\s(.+)$");
     Pattern createReceipt = Pattern.compile("^create_receipt\\s(.+)\\s(\\S+)\\s(-?\\d+\\.?\\d*)\\s(-?\\d+)\\s(-?\\d+)\\s(.+)$");
     Pattern getTransactions = Pattern.compile("^get_transactions\\s(.+)\\s(-|\\+|\\*|\\d+)$");
     Pattern payReceipt = Pattern.compile("^pay\\s(\\d+)$");
     Pattern getBalance = Pattern.compile("^get_balance\\s(.+)$");
}
