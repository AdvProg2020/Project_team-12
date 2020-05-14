package Controller.CommandProcessors;

import View.MainMenu;

public class ProductsPageCP extends CommandProcessor  {
    private static CommandProcessor Instance;

    protected ProductsPageCP() {
        super(MainMenuCP.getInstance());
    }

    public static CommandProcessor getInstance(){
        if (Instance == null)
            Instance = new ProductsPageCP();
        return Instance;
    }
}
