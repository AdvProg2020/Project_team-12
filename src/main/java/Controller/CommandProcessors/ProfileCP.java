package Controller.CommandProcessors;

public class ProfileCP  extends CommandProcessor {
    private static CommandProcessor Instance;

    protected ProfileCP() {
        super(MainMenuCP.getInstance());
    }

    public static CommandProcessor getInstance(){
        if (Instance == null)
            Instance = new ProfileCP();
        return Instance;
    }
}
