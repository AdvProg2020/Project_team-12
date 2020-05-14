package Controller.CommandProcessors;

public class PurchasePageCP extends CommandProcessor {
    private static CommandProcessor Instance;

    protected PurchasePageCP() {
        super(MainMenuCP.getInstance());
    }

    public static CommandProcessor getInstance(){
        if (Instance == null)
            Instance = new PurchasePageCP();
        return Instance;
    }
}
