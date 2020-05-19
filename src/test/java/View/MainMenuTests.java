package View;

import View.Profiles.RegisterPanel;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MainMenuTests {
    MainMenu mainMenu = new MainMenu();

    @Test
    public void getCommandTest(){
        String input = "go to register panel";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Menu menu1 = null;
        try {
            menu1 = mainMenu.getCommand();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Menu menu2 = new RegisterPanel(mainMenu);
        Assert.assertEquals(menu2, menu1);
    }
}
