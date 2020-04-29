package Controller.DataBase;

import Controller.DataBase.Json.JsonFileReader;

import java.io.FileNotFoundException;

public class Config {
    private static final String configPath = "configurations.json";
    private static Config Instance;
    private final String[] accountsPath = {"Resources/Accounts/Customers","Resources/Accounts/Sellers","Resources/Accounts/Managers"};
    private final String productsPath = "Resources/Products";
    private final String discountCodePath = "Resources/Discounts/CodedDiscounts";
    private final String auctionsPath= "Resources/Discounts/Auctions";


    public static Config getInstance() {
        if (Instance == null) {
            try {
                JsonFileReader jsonReader = new JsonFileReader();
                Instance = (Config) jsonReader.read("configurations.json", Config.class);
            } catch (FileNotFoundException var1) {
                Instance = new Config();
            }
        }
        return Instance;
    }

    public String[] getAccountsPath() {
        return accountsPath;
    }

    public String getProductsPath() {
        return productsPath;
    }

    public String getDiscountCodePath() {
        return discountCodePath;
    }

    public String getAuctionsPath() {
        return auctionsPath;
    }
}
