package View;

import java.util.Scanner;

public class InputUtility {
    private static Scanner instance;

    public static Scanner getInstance() {
        if (instance == null) {
            instance = new Scanner(System.in);
        }
        return instance;
    }
}
