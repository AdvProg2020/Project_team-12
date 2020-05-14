package Controller.CommandProcessors;

public class ProductPageCP extends CommandProcessor  {
    private static CommandProcessor Instance;

    protected ProductPageCP() {
        super(ProductsPageCP.getInstance());
    }

    public static CommandProcessor getInstance(){
        if (Instance == null)
            Instance = new ProductsPageCP();
        return Instance;
    }
}
