package Controller.CommandProcessors;

import Controller.DataBase.DataCenter;
import Model.Account.CanRequest;
import Model.Account.Customer;
import Model.Log.PurchaseLog;
import Model.ProductsOrganization.Cart;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.ProductOnLog;
import Model.ProductsOrganization.Review;
import Model.Request.Request;
import Model.Request.ReviewRequest;
import View.ProductsPage;

import java.util.ArrayList;

public class ProductPageCP extends CommandProcessor{
    private Product selectedProduct;


    public static CommandProcessor getInstance(String ID) {
        Instance = new ProductPageCP(ID);
        return Instance;
    }
    protected ProductPageCP(String ID) {
        super(ProductsPageCP.getInstance());
        this.selectedProduct = DataCenter.getInstance().getProductById(ID);
    }

    // Command: digest & attributes
    private Product getProductDetails() {
        return selectedProduct;
    }


    // Command: compare (productID)
    // Use: DATA center : getProductByID

    // Command: comments
    // Use: selectedProduct.getAllReviews();
    // Use: selectedProduct.getAverageMark();

    // Command: Add comment

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void addToCart(){
        Cart cart = CommandProcessor.getCart();
        cart.addProduct(this.selectedProduct);
    }

    public ArrayList<Review> getComments(){
        return this.selectedProduct.getAllReviews();
    }

    public Product getProductById(String id){
        return dataCenter.getProductById(id);
    }

    public boolean hasUserBoughtThisProduct(){
        if (!(getLoggedInAccount() instanceof Customer))
            return false;
        for (PurchaseLog buyLog : ((Customer) getLoggedInAccount()).getBuyLogs()) {
            for (ProductOnLog purchasedProduct : buyLog.getAllPurchasedProducts()) {
                if(purchasedProduct.getName().equals(selectedProduct.getName()))
                    return true;
            }
        }
        return false;
    }

    public void addReview(String title , String content) throws Exception{
        Review review = new Review(title,content,hasUserBoughtThisProduct());
        Request request = new ReviewRequest(getLoggedInAccount().getUsername(),dataCenter.requestIDGenerator((CanRequest) getLoggedInAccount()),false,review,selectedProduct.getID());

        dataCenter.addRequest(request);
        dataCenter.saveRequest(request);
    }

}

//digest (Done)
//⇒ add to cart
//⇒ select seller [seller_username] (Removed)
//attributes (Done)
//compare [productID]
//Comments
//⇒ Add comment
//Title:
//Content:

