package IETI.Projecte;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;


public class MainActivity extends AppCompatActivity {

    // Parámetros para la conexión
    int port = 8888;
    String location = "";
    String uri = "";
    WebSocketClient client;
    ArrayList<String> credentials = new ArrayList<>();

    EditText server;
    EditText user;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        server = findViewById(R.id.serverConnection);
        user = findViewById(R.id.user);
        password = findViewById(R.id.pwd);

        Button entrarAConexion = findViewById(R.id.button);
            entrarAConexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(server.getText().equals("10.0.2.2")){
                        server.setText("");
                        Toast(MainActivity.this,"Server IP incorrect");
                    }

                    if(TextUtils.isEmpty(server.getText()) || TextUtils.isEmpty(user.getText()) || TextUtils.isEmpty(password.getText())){
                        Toast(MainActivity.this, "All fields are required");
                    } else {
                        location = String.valueOf(server.getText());
                        uri = "ws://" + location + ":" + port;

                        credentials.add(String.valueOf(user.getText()));
                        credentials.add(String.valueOf(password.getText()));

                        connecta(uri);
                        user.setText("");
                        password.setText("");
                    }
                }
            });

    }

    public void connecta (String uri) {
        try {
            client = new WebSocketClient(new URI(uri), (Draft) new Draft_6455()) {
                @Override
                public void onMessage(String message){

                    if(message.equals("V")){
                        changeActivity();
                        client.send("XML");
                    }else if(message.equals("NV")){
                        user.setText("");
                        password.setText("");
                        Toast(MainActivity.this,"User or password incorrect");
                    }else if(message.contains("id")){
                        Log.i("DATA: " , message);
                        Model.componentsData = message;
                    }
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected to: " + getURI());
                    envia();
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Toast(MainActivity.this,"Server IP incorrect");
                    System.out.println("Disconnected from: " + getURI());
                    // Desconecta el cliente del servidor
                    RemotControlActivity.setStateConnected(client);
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

    protected void envia(){
        try {
            String userCredentials = "UC#" + credentials.get(0) + "#" + credentials.get(1);
            client.send(userCredentials);
        } catch (WebsocketNotConnectedException e) {
            System.out.println("Connexió perduda ...");
            Log.i("AQUI", "AQUI");
            connecta(uri);
        }
    }

    public void changeActivity() {
        Intent intent = new Intent(this, RemotControlActivity.class);
        intent.putExtra("credentials",credentials);
        startActivity(intent);
    }

    // Para mandar el
    public void Toast(Activity activity, CharSequence text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}