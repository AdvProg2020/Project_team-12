package Model.ProductsOrganization;

import Model.Account.Customer;

import java.util.ArrayList;

public class Cart {
    //private ArrayList<Product> products;
    private ArrayList<ProductInCart> products;
    private Customer owner;
    private String receiverInfo;
    private static Cart Instance;

    private Cart(Customer owner) {
        this.owner = owner;
        products = new ArrayList<>();
    }

    public static Cart getInstance() {
        if (Instance == null)
            Instance = new Cart(null);
        return Instance;
    }

    public void resetCart() {
        Instance = null;
    }

    public Double getPayAmount() {
        Double price = Double.valueOf(0);
        for (ProductInCart product : this.products) {
            price += product.getPrice();
        }
        return price;
    }

    public void addProduct(Product product) {
        products.add(new ProductInCart(product, 1));
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
            var1000 += product.getProduct().toString() + "you have selected "
                    + product.getQuantity() + "\n";
        }
        var1000 += "Sum of prices is:" + getPayAmount();
        return var1000;
    }

    public Product getProductWIthID(String productId) throws Exception {
        for (ProductInCart product : products) {
            if (product.getProduct().getID().equals(productId))
                return product.getProduct();
        }
        throw new Exception("Product with id didnt found");
    }

    public void increaseProductWithId(String productID) throws Exception {
        for (ProductInCart entry : products) {
            if (entry.getProduct().getID().equals(productID)) {
                increaseProductEach(entry);
                return;
            }
        }
        throw new Exception("Product with this id didnt found" + productID);
    }


    private void increaseProductEach(ProductInCart product) throws Exception {
        if (product.getProduct().getRemainingItems() >= (product.getQuantity() + 1))
            product.setQuantity(product.getQuantity() + 1);
        else
            throw new Exception("Can not add more of this item.");
    }

    public void decreaseProductWithId(String productID) throws Exception {
        for (ProductInCart entry : products) {
            if (entry.getProduct().getID().equals(productID)) {
                products.remove(entry);
                for (ProductInCart product : products) {
                    if (product.getQuantity() == 0)
                        products.remove(product);
                }
                return;
            }
        }
        throw new Exception("Product with this id didnt found" + productID);
    }


    public String getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(String receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public int getQuantityWIthID(String productID) throws Exception {
            for (ProductInCart product : products) {
                if (product.getProduct().getID().equals(productID))
                    return product.getQuantity();
            }
            throw new Exception("Product with id didnt found");
    }


    public class ProductInCart {
        private Product product;
        private int quantity;

        public ProductInCart(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Double getPrice() {
            return quantity * product.getPrice();
        }
    }
}