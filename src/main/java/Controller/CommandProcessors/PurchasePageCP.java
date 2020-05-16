package Controller.CommandProcessors;

import Controller.DataBase.DataCenter;
import Model.Account.Seller;

public class PurchasePageCP extends CommandProcessor {
    private static CommandProcessor Instance;

    protected PurchasePageCP() {
        super(MainMenuCP.getInstance());
    }

    public static CommandProcessor getInstance() {
        if (Instance == null)
            Instance = new PurchasePageCP();
        return Instance;
    }

    public void showProductsInCart() {
        System.out.println(CommandProcessor.getCart().toString());
    }

    public void increaseProduct(int productID,String sellerUserName) throws Exception {

        if (!checkProductAvailability(productID,sellerUserName)||sellerHasProduct(productID,sellerUserName))
            throw new Exception("cannot purchase this product",new Throwable("this account isn a seller or the seller doesnt have this product"));
        if (CommandProcessor.getCart().getProductWIthID(productID).getSellerUserName().equals(sellerUserName))
            CommandProcessor.getCart().increaseProductWithId(productID);
        else
            CommandProcessor.getCart().addProduct(productID,sellerUserName);
    }

    private boolean checkProductAvailability(int productID, String sellerUserName) throws Exception {
        ProductInfo productInfo =((Seller)DataCenter.getInstance().getAccountByName(sellerUserName)).getProductInfo(DataCenter.getInstance().getProductById(productID).getName());
        ProductInfo productInfoInCart = CommandProcessor.getCart().getProductWIthID(productID);
        if (productInfoInCart.getSellerUserName().equals(sellerUserName)){
            return productInfoInCart.getQuantity()+1<=productInfo.getQuantity();
        }
        return productInfo.getQuantity() > 0;
    }

    public String viewProductSellers(int productId) throws Exception {
        return DataCenter.getInstance().getProductById(productId).getSellersInfo() + "you have selected " +
                getCart().getProductWIthID(productId).getQuantity() + " of this product from seller" + getCart().getProductWIthID(productId).getSellerUserName();
    }

    public void decreaseProductWithID(int productID){
        CommandProcessor.getCart().decreaseProductWithId(productID);

    }

    public void showTotalPrice(){
        //TODO:this method should be written carefully bcz the product might be in auction.
    }

    private boolean sellerHasProduct(int productId, String username){
        if (!(DataCenter.getInstance().getAccountByName(username) instanceof Seller)||((Seller)DataCenter.getInstance().getAccountByName(username)).getProductInfo(productId)==null)
            return false;
        return true;
    }
}
