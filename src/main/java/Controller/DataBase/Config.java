package Controller.DataBase;

import Controller.DataBase.Json.JsonFileReader;

import java.io.FileNotFoundException;

public class Config {
    private static final String configPath = "configurations.json";
    private static Config Instance;
    private String accountsPath;
    private String productsPath;

    private Config() {
        this.accountsPath = "Resources/Accounts";
        this.productsPath = "Resources/Products";
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

    public String getAccountsPath() {
        return accountsPath;
    }

    public void setAccountsPath(String usersPath) {
        this.accountsPath = usersPath;
    }

    public String getProductsPath() {
        return productsPath;
    }

    public void setProductsPath(String channelsPath) {
        this.productsPath = channelsPath;
    }
}
