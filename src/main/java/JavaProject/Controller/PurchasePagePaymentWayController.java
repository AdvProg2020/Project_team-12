package JavaProject.Controller;

import JavaProject.App;
import JavaProject.Model.Account.Account;
import JavaProject.Model.Account.Buyer;
import JavaProject.Model.Account.Seller;
import JavaProject.Model.Database.Database;
import JavaProject.Model.Discount.Auction;
import JavaProject.Model.Log.ProductOnLog;
import JavaProject.Model.Log.SellLog;
import JavaProject.Model.ProductOrganization.Cart;
import JavaProject.Model.ProductOrganization.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PurchasePagePaymentWayController implements Initializable {

    @FXML Label totalPriceLabel;
    @FXML Label discountLabel;
    @FXML Label toBePaidLabel;
    @FXML Label balanceLabel;
    @FXML Button balanceButton;
    @FXML Button increaseButton;

    double totalPrice;
    double discount;
    double toBePaid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        totalPrice = App.getCart().getPrice();
        if (App.getCart().getCode() != null) {
            discount = Math.min(App.getCart().getCode().getMaxDiscount(), totalPrice * App.getCart().getCode().getDiscountPercent() / 100);
        }
        toBePaid = totalPrice - discount;
        totalPriceLabel.setText("Total price : $" + totalPrice);
        discountLabel.setText("Discount : $" + discount);
        toBePaidLabel.setText("To pay : $" + toBePaid);
        balanceLabel.setText("Your balance : $" + ((Buyer)App.getSignedInAccount()).getBalance());
        if (toBePaid > ((Buyer)App.getSignedInAccount()).getBalance()) {
            balanceButton.setDisable(true);
            increaseButton.setText("Increase balance : $" + (toBePaid - ((Buyer)App.getSignedInAccount()).getBalance()));
        } else {
            increaseButton.setDisable(true);
        }
    }

    public void payWithBalance(ActionEvent event) {
        Buyer buyer = (Buyer) App.getSignedInAccount();
        buyer.setBalance(buyer.getBalance() - toBePaid);
        ArrayList<Seller> handledSellers = new ArrayList<>();
        for (Product pro : App.getCart().getProducts().keySet()) {
            Seller seller = (Seller) Database.getInstance().getAccountByUsername(pro.getSellerUsername());
            if (handledSellers.contains(seller)) continue;
            handledSellers.add(seller);
            double totalGainedAmount = 0, totalDecreasedAmount = 0;
            ArrayList<ProductOnLog> productOnLogs = new ArrayList<>();
            for (Product product : App.getCart().getProducts().keySet()) {
                if (!seller.getUsername().equals(product.getSellerUsername())) continue;
                
                Auction auction = Database.getInstance().getCurrentAuction(product);
                int quantity = App.getCart().getProducts().get(product);
                double gainedAmount = quantity * product.getPrice() * (auction == null ? 100 : 100 - auction.getDiscountPercent()) / 100;
                double decreasedAmount = quantity * product.getPrice() * (auction == null ? 0 : auction.getDiscountPercent()) / 100;
                totalGainedAmount += gainedAmount;
                totalDecreasedAmount += decreasedAmount;
                ProductOnLog productOnLog = new ProductOnLog(product.getName(), auction == null ? null : auction.getID(), null, seller.getUsername(), quantity, gainedAmount, decreasedAmount);
                productOnLogs.add(productOnLog);
            }
            seller.setBalance(seller.getBalance() + totalGainedAmount);
            SellLog sellLog = new SellLog(buyer.getUsername(), totalGainedAmount, totalDecreasedAmount, productOnLogs);
            seller.getSellLogsID().add(sellLog.getID());
        }
    }

    public void increaseBalance(ActionEvent event) throws IOException {
        App.setRoot("payment");
        PurchasePageCardPaymentController.amount = toBePaid;
    }

    public void goToDiscountSection(MouseEvent mouseEvent) throws IOException {
        App.setRoot("discountCodeValidation");
    }
}
