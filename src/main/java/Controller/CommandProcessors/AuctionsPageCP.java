package Controller.CommandProcessors;

import View.MainMenu;

public class AuctionsPageCP extends CommandProcessor  {
    private static CommandProcessor Instance;

    protected AuctionsPageCP() {
        super(MainMenuCP.getInstance());
    }

    public static CommandProcessor getInstance(){
        if (Instance == null)
            Instance = new AuctionsPageCP();
        return Instance;
    }

}
