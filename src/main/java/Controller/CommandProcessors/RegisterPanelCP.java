package Controller.CommandProcessors;

public class RegisterPanelCP extends CommandProcessor {
    private static CommandProcessor Instance;

    protected RegisterPanelCP() {
        super(MainMenuCP.getInstance());
    }

    public static CommandProcessor getInstance(){
        if (Instance == null)
            Instance = new RegisterPanelCP();
        return Instance;
    }
}
