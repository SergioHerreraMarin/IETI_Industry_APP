package IETI.Projecte;

import android.app.Activity;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

class WebSocket {

    static Activity act;
    WebSocketClient client;

    public void connecta (String uri) {
        try {
            client = new WebSocketClient(new URI(uri), (Draft) new Draft_6455()) {
                @Override
                public void onMessage(String message){

                    if ((message.equalsIgnoreCase("V") || message.equalsIgnoreCase("NV")) && act instanceof MainActivity) {
                        ((MainActivity) act).login(message);
                    } else if (message.contains("id")){
                        Model.componentsData = message;
                    }
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected to: " + getURI());
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    if (act instanceof RemotControlActivity) {
                        ((RemotControlActivity) act).connectionLost();
                    }
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };

            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("Error: " + uri + " no és una direcció URI de WebSocket vàlida");
        }
    }
}
