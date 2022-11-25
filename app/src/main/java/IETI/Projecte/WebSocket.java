package IETI.Projecte;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class WebSocket {

    static Activity act;
    WebSocketClient client;

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    public void connecta (String uri) {
        try {
            client = new WebSocketClient(new URI(uri), (Draft) new Draft_6455()) {
                @Override
                public void onMessage(String message){

                    if ((message.equalsIgnoreCase("V") || message.equalsIgnoreCase("NV")) && act instanceof MainActivity) {
                        ((MainActivity) act).loginActivity(message);
                    }else if (message.contains("=")){
                        System.out.println("RECIIDO XD: " + message);
                        Model.componentsData = message;
                    }else if(message.contains("current")){

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("UPDATE COMPONENT XD: " + message);
                                Model.updateComponent(message);
                            }
                        });


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
