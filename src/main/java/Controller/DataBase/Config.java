package Controller.DataBase;

import Controller.DataBase.Json.JsonFileReader;

import java.io.FileNotFoundException;

public class Config {
    private static final String configPath = "configurations.json";
    private static Config Instance;
    private final String[] accountsPath = {"Resources/Accounts/Customers","Resources/Accounts/Sellers","Resources/Accounts/Managers"};
    private final String productsPath = "Resources/Products";
    private final String[] discountsPath ={ "Resources/Discounts/CodedDiscounts","Resources/Discounts/Auctions"};
    public enum AccountsPath{CUSTOMER (0),SELLER(1),MANAGER(2);
        private int num;
        AccountsPath(int i) {
            num = i;
        }
        public int getNum(){
            return num;
        }
    }
    public enum DiscountsPath{DISCOUNTCODE (0),AUCTION(1);
        private int num;
        DiscountsPath(int i) {
            num = i;
        }
        public int getNum(){
            return num;
        }
    }
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

    public String[] getDiscountsPath() {
        return discountsPath;
    }
}