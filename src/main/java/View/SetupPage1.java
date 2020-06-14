package View;


import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sun.swing.WindowsPlacesBar;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SetupPage1 implements Initializable {
    HamburgerSlideCloseTransition transition2 ;
    boolean formIsOpen = false;
    @FXML
    private JFXHamburger test;
    @FXML
    ImageView imageView;
    @FXML
    ImageView profileImage;
    public SetupPage1() {

    }

    @FXML
    public void change() {
        if (!formIsOpen){
            transition2 = new HamburgerSlideCloseTransition(test);
            transition2.setRate(-1);
            formIsOpen = true;
        }
        transition2.setRate(transition2.getRate() * -1);
        transition2.play();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFileChooser jFileChooser = new JFileChooser();

        jFileChooser.showSaveDialog(null);
    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void choseImage(MouseEvent mouseEvent) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored){}
        new Thread(() -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileFilter(new Filter());
            jFileChooser.setApproveButtonToolTipText("Chose");
            jFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
            jFileChooser.setToolTipText("Chose your profile image");
            jFileChooser.showSaveDialog(null);

        }).start();


    }
    @FXML
    private void register(){

    }
    @FXML
    private void proceed(){

    }

    /*public void zoom(MouseEvent mouseEvent) {
                cropImage((int)mouseEvent.getSceneX(),(int)mouseEvent.getSceneY(),imageView);
    }
    private void cropImage(int width,int height, ImageView imageView) {
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            parameters.setViewport(new Rectangle2D(width-10, height-10, width, height));

            WritableImage wi = new WritableImage(50, 50);
            Image croppedImage = imageView.snapshot(parameters, wi);
            profileImage.setImage(croppedImage);
    }*/
    class Filter extends FileFilter {

        public boolean accept(File f) {
            if (f.isDirectory())
                return true;
            String s = f.getName();
            if (s.endsWith(".jpg")||s.endsWith(".png"))
                return true;
            return false;
        }

        public String getDescription() {
            return "(*.jpg),(*.png)";
        }
    }


}
//TODO:register and proceed method
//TODO:animation for proceed button
//TODO:Stack pane animation
//TODO:change avatar for file save