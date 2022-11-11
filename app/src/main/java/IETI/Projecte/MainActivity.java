package IETI.Projecte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URI;
import java.net.URISyntaxException;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;


public class MainActivity extends AppCompatActivity {

    // Parámetros para la conexión
    int port = 8888;
    String location = "10.0.2.2";
    String uri = "ws://" + location + ":" + port;
    WebSocketClient client;

    EditText servidor = findViewById(R.id.serverConnection);
    EditText usuari = findViewById(R.id.user);
    EditText password = findViewById(R.id.pwd);
    Button entrarAConexion = findViewById(R.id.button);

    @Override
    protected void onStart(){
        super.onStart();
        connecta();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connecta () {
        try {
            client = new WebSocketClient(new URI(uri), (Draft) new Draft_6455()) {
                @Override
                public void onMessage(String message) {

                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected to: " + getURI());
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Disconnected from: " + getURI());
                }

                @Override
                public void onError(Exception ex) { ex.printStackTrace(); }
            };
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("Error: " + uri + " no és una direcció URI de WebSocket vàlida");
        }
    }

    protected void envia (boolean salt) {
        if (salt) {
        } else {
        }

        try {
            client.send("Hola desde Android");
        } catch (WebsocketNotConnectedException e) {
            System.out.println("Connexió perduda ...");
            connecta();
        }

    }

}