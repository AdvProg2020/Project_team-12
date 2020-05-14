package Controller.CommandProcessors;

public enum CPS {
    AuctionPageCP(1),MainMenuCp(2),ProductPageCP(3),ProductsPageCP(4),ProfileCP(5),PurchasePageCP(6),RegisterPanelCP(7);
    private int id;

    CPS(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
