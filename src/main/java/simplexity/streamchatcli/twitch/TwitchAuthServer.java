package simplexity.streamchatcli.twitch;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import simplexity.streamchatcli.config.ChatConfig;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class TwitchAuthServer {
    
    public static void run() throws IOException {
        setupServer();
    }
    
    private static void setupServer() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(3000), 0);
        httpServer.createContext("/", new AuthHandler());
        httpServer.setExecutor(null);
        httpServer.start();
    }
    
    static class AuthHandler implements HttpHandler {
        
        @Override
        public void handle(HttpExchange t) throws IOException {
            String twitchCode = t.getRequestURI().getQuery().split("=")[1];
            twitchCode = twitchCode.split("&")[0];
            
            // Your logic to handle the Twitch code goes here
            System.out.println("Twitch Code: " + twitchCode);
            ChatConfig.getInstance().setTwitchCode(twitchCode);
            
            // Respond to the client
            String response = "Twitch authentication successful!";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            
        }
    }
}
