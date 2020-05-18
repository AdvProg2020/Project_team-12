package Controller.CommandProcessors;

import Controller.DataBase.BadRequestException;
import Controller.DataBase.DataCenter;
import Model.Account.Seller;
import Model.Log.PurchaseLog;
import Model.Log.SellLog;
import Model.ProductsOrganization.Cart;
import Model.ProductsOrganization.ProductOnLog;
import View.Exceptions.CustomerExceptions;

import java.util.ArrayList;
import java.util.HashMap;

public class PurchasePageCP extends CommandProcessor {
    private static CommandProcessor Instance;
    private final int MINIMUM_PAYMENT_TO_GET_REWARD = 1000000;
    private final int PERCENTAGE_OF_REWARD = 5;

    protected PurchasePageCP() {
        super(MainMenuCP.getInstance());
    }

    public static CommandProcessor getInstance() {
        if (Instance == null)
            Instance = new PurchasePageCP();
        return Instance;
    }

    public String showProductsInCart() {
        return CommandProcessor.getCart().toString();
    }

    public void increaseProduct(String productID) throws Exception {

        if (!sellerHasEnoughProduct(productID))
            throw new Exception("cannot purchase this product", new Throwable("this account is'n a seller or the seller doesnt have enough product"));
        else
            CommandProcessor.getCart().increaseProductWithId(productID);
    }

    private boolean sellerHasEnoughProduct(String productID) throws Exception {
        return DataCenter.getInstance().getProductById(productID).getRemainingItems() > (Cart.getInstance().getQuantityWIthID(productID) + 1);
    }


    public void decreaseProductWithID(String productID) throws Exception {
        CommandProcessor.getCart().decreaseProductWithId(productID);

    }

    public Double showTotalPrice() {
        return CommandProcessor.getCart().getPayAmount();
    }

    public void buy(String discountCodeId) throws Exception {
        if (discountCodeId == null || discountCodeId.equals("") || Cart.getInstance().getOwner().hasDiscountCode(discountCodeId))
            if (Cart.getInstance().getOwner().getCredit() >= DataCenter.getInstance().getDiscountcodeWithId(discountCodeId).calculatePrice(Cart.getInstance().getPayAmount()))
                buyWithoutDiscountCode();
            else
                throw new CustomerExceptions("You dont have enough credit");
        else if (Cart.getInstance().getOwner().getCredit() >= Cart.getInstance().getPayAmount())
            buyWithDiscountCode(discountCodeId);
        else
            throw new CustomerExceptions("You dont have enough credit");

    }

    private void buyWithoutDiscountCode() throws Exception {
        calculateSellersLog();
        double finalPrice = Cart.getInstance().getPayAmount();
        HashMap<Cart.ProductInCart, Seller> sellers = getTraders();
        ArrayList<ProductOnLog> productOnLogs = new ArrayList<>();
        for (Cart.ProductInCart productInCart : sellers.keySet()) {
            productOnLogs.add(new ProductOnLog(productInCart.getProduct().getName(), productInCart.getPrice(), productInCart.getProduct().getSeller(), productInCart.getQuantity()));
        }
        if (finalPrice > MINIMUM_PAYMENT_TO_GET_REWARD) {
            finalPrice *= ((double) (100 - PERCENTAGE_OF_REWARD) / 100);
        }
        PurchaseLog purchaseLog = new PurchaseLog(DataCenter.getInstance().getDate(), getCart().getOwner(), finalPrice, finalPrice, productOnLogs);
        Cart.getInstance().getOwner().setCredit(Cart.getInstance().getOwner().getCredit() - finalPrice);
        Cart.getInstance().getOwner().getBuyLogs().add(purchaseLog);
    }

    private void buyWithDiscountCode(String discountCodeID) throws Exception {
        calculateSellersLog();
        double finalPrice;
        try {
            finalPrice = DataCenter.getInstance().getDiscountcodeWithId(discountCodeID).calculatePrice(Cart.getInstance().getPayAmount());
            DataCenter.getInstance().getDiscountcodeWithId(discountCodeID).useCode();

        } catch (BadRequestException e) {
            buyWithoutDiscountCode();
            return;
        }
        HashMap<Cart.ProductInCart, Seller> sellers = getTraders();
        ArrayList<ProductOnLog> productOnLogs = new ArrayList<>();
        for (Cart.ProductInCart productInCart : sellers.keySet()) {
            productOnLogs.add(new ProductOnLog(productInCart.getProduct().getName(), productInCart.getPrice(), productInCart.getProduct().getSeller(), productInCart.getQuantity()));
        }
        if (finalPrice > MINIMUM_PAYMENT_TO_GET_REWARD) {
            finalPrice *= ((double) (100 - PERCENTAGE_OF_REWARD) / 100);
        }
        PurchaseLog purchaseLog = new PurchaseLog(DataCenter.getInstance().getDate(), getCart().getOwner(), finalPrice, finalPrice, productOnLogs);
        Cart.getInstance().getOwner().setCredit(Cart.getInstance().getOwner().getCredit() - finalPrice);
        Cart.getInstance().getOwner().getBuyLogs().add(purchaseLog);
    }

    private void calculateSellersLog() throws Exception {
        HashMap<Cart.ProductInCart, Seller> sellers = getTraders();
        Cart.ProductInCart[] productsInCart = (Cart.ProductInCart[]) sellers.keySet().toArray();
        for (int i = 0; i < sellers.size(); ) {
            ArrayList<ProductOnLog> productsOnLog = new ArrayList<>();
            Seller seller = sellers.get(productsInCart[i]);
            double receivedPrice = 0;
            double decreasedPrice = 0;
            for (int j = 0; j < sellers.size(); )
                if (productsInCart[i].getProduct().getSeller().equals(productsInCart[j].getProduct().getSeller())) {
                    productsOnLog.add(new ProductOnLog(productsInCart[j].getProduct().getName(), productsInCart[j].getPrice(), productsInCart[j].getProduct().getSeller(), productsInCart[j].getQuantity()));
                    receivedPrice += productsInCart[j].getPrice();
                    decreasedPrice += productsInCart[j].getPrice() - productsInCart[j].getProduct().getRawPrice();
                    sellers.remove(productsInCart[j]);
                }
            productsInCart = (Cart.ProductInCart[]) sellers.keySet().toArray();
            seller.getSellLogs().add(new SellLog(DataCenter.getInstance().getDate(), seller, receivedPrice, decreasedPrice, productsOnLog));
            seller.setCredit(seller.getCredit() + receivedPrice);
        }
    }

    public HashMap<Cart.ProductInCart, Seller> getTraders() throws Exception {
        HashMap<Cart.ProductInCart, Seller> sellers = new HashMap<>();
        for (Cart.ProductInCart product : Cart.getInstance().getProducts()) {
            sellers.put(product, (Seller) DataCenter.getInstance().getAccountByName(product.getProduct().getSeller()));
        }
        return sellers;
    }
}
