package Controller.CommandProcessors;

import Model.Account.Manager;

public class MainMenuCP extends CommandProcessor {
    private static CommandProcessor Instance;

    public static CommandProcessor getInstance(){
        if (Instance == null)
            Instance = new MainMenuCP();
        return Instance;
    }
    private MainMenuCP() {
        super(CommandProcessor.getPrimitive());
    }

    public static void setInstance(CommandProcessor instance) {
        Instance = instance;
    }
}
