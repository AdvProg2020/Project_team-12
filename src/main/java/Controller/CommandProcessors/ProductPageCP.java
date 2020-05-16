package Controller.CommandProcessors;

import Model.ProductsOrganization.Product;
import Model.ProductsOrganization.Review;

import java.util.ArrayList;

public class ProductPageCP {
    private Product selectedProduct;

    public ProductPageCP(String ID) {
        //TODO: this.selectedProduct = getProductByID(ID);
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
        selectedProduct.getAllReviews().add(new Review(selectedProduct, title, content));
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

