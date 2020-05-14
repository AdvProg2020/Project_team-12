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
    private final InitializeObjectsNumber objectsNumber;

    public Config() {
        accountsPath = new String[]{"Resources/Accounts/Customers", "Resources/Accounts/Sellers", "Resources/Accounts/Managers"};
        productsPath = "Resources/Products";
        discountsPath = new String[]{"Resources/Discounts/CodedDiscounts", "Resources/Discounts/Auctions"};
        requestsPath = "Resources/Requests";
        categoriesPath = "Resources/Categories";
        objectsNumber = new InitializeObjectsNumber(0, 0, 0, 0, 0);
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

    private class InitializeObjectsNumber {
        @Expose
        private int createdDiscounts;
        @Expose
        private int createdLogs;
        @Expose
        private int createdProducts;
        @Expose
        private int createdAuctions;
        @Expose
        private int createdRequestsId;

        public InitializeObjectsNumber(int createdDiscounts, int createdLogs, int createdProducts, int createdAuctions, int createdRequestsId) {
            this.createdDiscounts = createdDiscounts;
            this.createdLogs = createdLogs;
            this.createdProducts = createdProducts;
            this.createdAuctions = createdAuctions;
            this.createdRequestsId = createdRequestsId;
        }

        public int getCreatedDiscounts() {
            return createdDiscounts;
        }

        public void setCreatedDiscounts(int createdDiscounts) {
            this.createdDiscounts = createdDiscounts;
        }

        public int getCreatedLogs() {
            return createdLogs;
        }

        public void setCreatedLogs(int createdLogs) {
            this.createdLogs = createdLogs;
        }

        public int getCreatedProducts() {
            return createdProducts;
        }

        public void setCreatedProducts(int createdProducts) {
            this.createdProducts = createdProducts;
        }

        public int getCreatedAuctions() {
            return createdAuctions;
        }

        public void setCreatedAuctions(int createdAuctions) {
            this.createdAuctions = createdAuctions;
        }

        public int getCreatedRequestsId() {
            return createdRequestsId;
        }

        public void setCreatedRequestsId(int createdRequestsId) {
            this.createdRequestsId = createdRequestsId;
        }
    }

    public String getCategoriesPath() {
        return categoriesPath;
    }

    public InitializeObjectsNumber getObjectsNumber() {
        return objectsNumber;
    }
}