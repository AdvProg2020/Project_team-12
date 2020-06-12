package View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerProfile1 implements Initializable {
    @FXML
    private AnchorPane anchor;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            anchor = FXMLLoader.load(getClass().getClassLoader().getResource("SetupPage.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
