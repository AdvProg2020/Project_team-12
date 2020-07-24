//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Server;

import logger.LogLevel;
import logger.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Formatter;


public class NetworkHandler {
    private final Logger logger;
    private final int port = 6666;
    private RequestHandler requestHandler;
    private DataCenter dataCenter;
    private ServerSocket serverSocket;

    public NetworkHandler(Logger logger) {
        this.logger = logger;
        DataCenter.setLogger(logger);
        DataCenter.initFromFile();
        this.requestHandler = new RequestHandler();
    }

    public void init() {
        this.logger.log(LogLevel.Info, "Initializing...");
        this.dataCenter.initFromFile();
        this.logger.log(LogLevel.Info, "Initialized.");
    }

    public void Run() throws Exception {
        try {
            this.serverSocket = new ServerSocket(port);
            this.logger.log(LogLevel.Info, "Listening on port " + port);

            while (true) {
                Socket socket = this.serverSocket.accept();
                while (true) {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        Formatter writer = new Formatter(socket.getOutputStream());
                        String request = reader.readLine();
                        System.out.println(request);
                        Logger var10000 = this.logger;
                        LogLevel var10001 = LogLevel.Info;
                        InetAddress var10002 = socket.getInetAddress();
                        var10000.log(var10001, "Request from " + var10002 + ": " + request);
                        if (request.matches("^exit$")) {
                            socket.close();
                            break;
                        }
                        String response = this.requestHandler.executeRequest(request);
                        if (response.charAt(response.length()-1)!='\n')
                            response+="\n";
                        writer.format(response );
                        writer.flush();
                        this.logger.log(LogLevel.Info, "Response sent: " + response);
                    } catch (NullPointerException var10) {
                        this.handleError(var10);
                    }catch (SocketException e) {
                        break;
                    }
                }
            }
        } catch (Exception var12) {
            this.handleError(var12);
        }
    }

    private <T extends Exception> void handleError(@NotNull T e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        this.logger.log(LogLevel.Error, sw.toString());
    }
}
