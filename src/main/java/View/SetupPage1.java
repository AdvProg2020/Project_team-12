package View;


import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import sun.swing.WindowsPlacesBar;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SetupPage1 implements Initializable {
    HamburgerSlideCloseTransition transition2 ;
    boolean formIsOpen = false;
    @FXML
    private JFXHamburger test;

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