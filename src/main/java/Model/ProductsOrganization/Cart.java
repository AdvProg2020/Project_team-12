package Model.ProductsOrganization;

import Controller.DataBase.DataCenter;
import Model.Account.Customer;
import Model.Account.Seller;

import java.util.ArrayList;

public class Cart {
    private ArrayList<ProductInCart> products;
    private Customer owner;

    public Cart(Customer owner) {
        this.owner = owner;
        products = new ArrayList<>();
    }

    public int getPayAmount() {
        return 0;
    }

    public void addProduct(int productId, String sellerUsername) {
        products.add(new ProductInCart(((Seller) DataCenter.getInstance().getAccountByName(sellerUsername)).getProductInfo(productId), 1));
    }

    public ArrayList<ProductInCart> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductInCart> products) {
        this.products = products;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        String var1000 = "Owner Username IS:" + owner.getUsername() +
                "your Products are:";
        for (ProductInCart product : products) {
            var1000 += product.getProductInfo().toString() + "you have selected "
                    + product.getQuantity() + "\n";
        }
        var1000 += "Sum of prices is:" + getPayAmount();
        return var1000;
    }

    public ProductInfo getProductWIthID(int productId) throws Exception {
        for (ProductInCart product : products) {
            if (product.getProductInfo().getId() == productId)
                return product.getProductInfo();
        }
        throw new Exception("Product with id didnt found");
    }

    public void increaseProductWithId(int productID) {
        for (ProductInCart product : products) {
            if (product.getProductInfo().getId() == productID)
                product.setQuantity(product.getQuantity() + 1);
        }
    }

    public void decreaseProductWithId(int productID) {
        for (ProductInCart product : products) {
            if (product.getProductInfo().getId() == productID)
                product.setQuantity(product.getQuantity() - 1);
        }
    }


    class ProductInCart {
        private ProductInfo productInfo;
        private int quantity;

        public ProductInCart(ProductInfo productInfo, int quantity) {
            this.productInfo = productInfo;
            this.quantity = quantity;
        }

        public ProductInfo getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(ProductInfo productInfo) {
            this.productInfo = productInfo;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

}