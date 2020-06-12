package View;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsPage1 implements Initializable {
    HamburgerSlideCloseTransition animation;
    @FXML
    private JFXHamburger hamburgerBox;
    @FXML
    private AnchorPane adPane;
    @FXML
    private Pagination productsPages;
    @FXML
    private StackPane menu;
    @FXML
    private ImageView backButton;
    @FXML
    private ScrollPane filteringPane;
    @FXML
    private MenuButton sortMenu;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label optionLabel;
    @FXML
    private ImageView registerImg;
    @FXML
    private Label registerLabel;
    @FXML
    private ImageView showCatImg;
    @FXML
    private Label showCatLabel;
    @FXML
    private boolean menuIsOpen = false;

    @FXML
    private void showOptions() {
        if (menu.getOpacity() == 0) {
            openMenu();
        }
    }

    private void openMenu() {
        animation.setRate(animation.getRate() * (-1));
        animation.play();
        disableFields(true);
        showMenu(0);
        menu.toFront();
        menu.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        menuIsOpen = !menuIsOpen;
    }

    private void closeMenu() {
        animation.setRate(animation.getRate() * (-1));
        animation.play();
        disableFields(false);
        showMenu(1);
        menuIsOpen = !menuIsOpen;
        menu.toBack();
    }

    private void disableFields(boolean b) {
        adPane.setDisable(b);
        productsPages.setDisable(b);
        backButton.setDisable(b);
        backButton.setDisable(b);
        sortMenu.setDisable(b);
        filteringPane.setDisable(b);
    }

    private void showMenu(int start) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setFromValue(start);
        fadeTransition.setToValue(~start + 2);
        fadeTransition.setDuration(new Duration(Constants.menusFadeTime));
        fadeTransition.setNode(menu);
        fadeTransition.play();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        animation = new HamburgerSlideCloseTransition(hamburgerBox);
        animation.setRate(-1);

    }

    @FXML
    public void update(MouseEvent mouseEvent) {
        try {
            if (!mouseEvent.getTarget().equals(showCatImg) && !((LabeledText) mouseEvent.getTarget()).getText().equals(showCatLabel.getText()) && !mouseEvent.getTarget().equals(registerImg) && !((LabeledText) mouseEvent.getTarget()).getText().equals(registerLabel.getText()))
                if (menu.getOpacity() == 1)
                    closeMenu();
        } catch (ClassCastException ignored) {
            if (menu.getOpacity() == 1)
                closeMenu();
        }
    }


}
