package Controller;

import Model.Account.Account;
import Model.ProductsOrganization.Category;
import Model.ProductsOrganization.Product;
import Model.Status;
import com.sun.org.apache.xpath.internal.operations.String;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CommandProcessor {
    private Account currentAccount;

    public void createAccount(String name, String Type) {

    }

    private void canCreateManager() {

    }

    private void createNewCustomer(String name) {

    }

    private void createFirstManager(String name) {

    }

    private void createNewSeller(String name) {
    }

    public boolean login(String username, String password) {
        return true;
    }

    private boolean userExists(String username) {
        return true;
    }

    public void viewPersonalInfo() {
    }

    public void editField(String fieldName) {
    }

    public boolean manageUsers() {
        return true;
    }

    public String viewUser(String userName) {
        return null;
    }

    public void deleteUser(String userName) {
    }

    public boolean canCreateNewManager() {
        return true;
    }

    public void removeProduct(int productId) {
    }

    public boolean createNewManagerByManager(String name) {
        return true;
    }

    public void createDiscountCode(Date start, Date end, double percent, java.lang.String code, int maximumDiscountAmount, int maximumNumberOfUsages, ArrayList<Account> allAllowedAccounts) {
    }

    public String viewDiscountCodes() {
        return null;
    }

    public String viewDiscountCode(String code) {
        return null;
    }

    //public void editDiscountCode(ArrayList<String> changed){}
    //I dont know what inputs should be for this function
    public void removeDiscount(String code) {
    }

    public String manageRequests() {
        return null;
    }

    public String getDetailOnRequest(int id) {
        return null;
    }

    public void acceptRequest(int id) {
    }

    public void DeclineRequest(int id) {
    }

    public String ManageCategories() {
        return null;
    }

    public void editCategory(String name) {
    }

    public void addCategory(String name) {
    }

    public void removeCategory(String name) {
    }

    public String viewCompanyInfo() {
        return null;
    }

    public String viewSaleHistory() {
        return null;
    }

    public void viewBuyers(int productId) {
    }

    public void editProduct(int productId) {
    }

    public void addProduct(int id, Status status, java.lang.String name, java.lang.String brand, int price, int remainingItems, HashMap<java.lang.String, java.lang.String> specifications, java.lang.String description, Category parent) {

    }

    public String showCategories() {
        return null;
    }

    public String viewOffs() {
        return null;
    }

    public String viewOff(int offId) {
        return null;
    }

    public String editOff(int offId, ArrayList<Product> allIncludedProducts, Status status) {
        return null;
    }

    public String addOff(int offId, ArrayList<Product> allIncludedProducts, Status status) {
        return null;
    }

    public int viewBalance() {
        return 0;
    }

    public String showProducts() {
        return null;
    }

    public String viewProduct(int id) {
        return null;
    }

    public void increaseProduct(int id) {
    }

    public void decreaseProduct(int id) {
    }

    public int showTotalPrice() {
        return 0;
    }

    public void purchase() {
    }

    public String vieOrders() {
        return null;
    }

    public String vieOrder(int orderId) {
        return null;
    }

    public void rateProduct(int productId, double score) {
    }

    /*public double viewBalance() {
    }*/

    /*public String viewDiscountCodes() {
    }*/

    public String viewCategories() {
        return null;
    }


}
