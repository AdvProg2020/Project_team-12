package Server;

import logger.Logger;

public class Main {
    public static void main(String[] args) {
        final NetworkHandler networkHandler = new NetworkHandler(new Logger());
        networkHandler.init();
        new Thread(() -> {
            try {
                networkHandler.Run();
            } catch (Exception e) {

            }
        }).start();
    }
}
