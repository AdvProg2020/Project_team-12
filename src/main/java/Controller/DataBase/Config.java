package Controller.DataBase;

import Controller.DataBase.Json.JsonFileReader;
import Controller.DataBase.Json.JsonFileWriter;
import com.google.gson.annotations.Expose;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Config {
    @Expose
    private static final String configPath = "configurations.json";
    @Expose
    private static Config Instance;
    @Expose
    private final String[] accountsPath;
    @Expose
    private final String productsPath;
    @Expose
    private final String[] discountsPath;
    @Expose
    private final String requestsPath;
    @Expose
    private final String categoriesPath;
    @Expose
    private int purchaseID;
    @Expose
    private int sellID;
    @Expose
    private int requestsId;

    public Config() {
        accountsPath = new String[]{"Resources/Accounts/Customers", "Resources/Accounts/Sellers", "Resources/Accounts/Managers"};
        productsPath = "Resources/Products";
        discountsPath = new String[]{"Resources/Discounts/CodedDiscounts", "Resources/Discounts/Auctions"};
        requestsPath = "Resources/Requests";
        categoriesPath = "Resources/Categories";
        purchaseID = 0;
        sellID=0;
        requestsId = 0;
    }

    public static Config getInstance() {
        if (Instance == null) {
            try {
                JsonFileReader jsonReader = new JsonFileReader();
                Instance = jsonReader.read("configurations.json", Config.class);
            } catch (FileNotFoundException var1) {
                Instance = new Config();
                try {
                    new JsonFileWriter().write(Instance, configPath);
                } catch (IOException e) {

                }
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

    public String getRequestsPath() {
        return requestsPath;
    }

    public void updateConfig() throws Exception {
        try {
            new JsonFileWriter().write(Instance, configPath);
        } catch (IOException e) {
            throw new Exception("can not update Config", e.getCause());
        }
    }

    public String getCategoriesPath() {
        return categoriesPath;
    }

    public int getRequestsId() {
        return requestsId;
    }

    public void setRequestsId(int requestsId) {
        this.requestsId = requestsId;
        saveConfig();
    }

    public enum AccountsPath {
        CUSTOMER(0), SELLER(1), MANAGER(2);
        private int num;

        AccountsPath(int i) {
            num = i;
        }

        public int getNum() {
            return num;
        }
    }

    public enum DiscountsPath {
        DISCOUNTCODE(0), AUCTION(1);
        private int num;

        DiscountsPath(int i) {
            num = i;
        }

        public int getNum() {
            return num;
        }
    }

    public int getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
        saveConfig();
    }

    public int getSellID() {
        return sellID;

    }

    public void setSellID(int sellID) {
        this.sellID = sellID;
        saveConfig();
    }

    private void saveConfig() {
        JsonFileWriter writer = new JsonFileWriter();
        try {
            writer.write(Config.getInstance(),Config.configPath,Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}