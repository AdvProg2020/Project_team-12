package Controller.CommandProcessors;

import Controller.DataBase.DataCenter;
import Model.ProductsOrganization.Cart;
import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.Review;
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
        //TODO: this.selectedProduct = getProductByID(ID);
        this.selectedProduct = DataCenter.getInstance().getProductById(ID);
    }

    // Command: digest & attributes
    private Product getProductDetails() {
        return selectedProduct;
    }

    // TODO: Command: add to cart

    // Command: compare (productID)
    // Use: DATA center : getProductByID

    // Command: comments
    // Use: selectedProduct.getAllReviews();
    // Use: selectedProduct.getAverageMark();

    // Command: Add comment
    public void addComment(String title, String content) {
        selectedProduct.getAllReviews().add(new Review( title, content,true));
    }

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

}

//digest (Done)
//⇒ add to cart (TODO)
//⇒ select seller [seller_username] (Removed)
//attributes (Done)
//compare [productID]
//Comments
//⇒ Add comment
//Title:
//Content:

