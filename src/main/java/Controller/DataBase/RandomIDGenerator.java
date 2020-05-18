package Controller.DataBase;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RandomIDGenerator {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
    // optional, make it more random
    private static final String PASSWORD_ALLOW_BASE_SHUFFLE = shuffleString(PASSWORD_ALLOW_BASE);
    private static final String PASSWORD_ALLOW = PASSWORD_ALLOW_BASE_SHUFFLE;

    private static SecureRandom random = new SecureRandom();


    public static String generateRandomPassword(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {

            int rndCharAt = random.nextInt(PASSWORD_ALLOW.length());
            char rndChar = PASSWORD_ALLOW.charAt(rndCharAt);


            sb.append(rndChar);

        }

        return sb.toString();

    }

    // shuffle
    public static String shuffleString(String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        return letters.stream().collect(Collectors.joining());
    }

    public static String generateProductID(String[] args) {
        String outPut = "PR_" + generateRandomPassword(13);
        for (String arg : args) {
            if (arg.equals(outPut))
                return generateProductID(args);
        }
        return outPut;
    }

    public static String discountIdGenerator(String[] args) {
        String outPut = "AU_" + generateRandomPassword(13);
        for (String arg : args) {
            if (arg.equals(outPut))
                return discountIdGenerator(args);
        }
        return outPut;
    }

    public static String generateSellID(String[] args) {
        String outPut = "SELL_" + generateRandomPassword(11);
        for (String arg : args) {
            if (arg.equals(outPut))
                return generateSellID(args);
        }
        return outPut;
    }

    public static String generateBuyID(String[] args) {
        String outPut = "BUY_" + generateRandomPassword(12);
        for (String arg : args) {
            if (arg.equals(outPut))
                return generateBuyID(args);
        }
        return outPut;
    }
}