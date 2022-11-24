package IETI.Projecte;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    String location = "10.0.2.2";
    String uri = "ws://" + location + ":" + port;
    static WebSocket socket = new WebSocket();
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

        WebSocket.act = MainActivity.this;

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
                        credentials.add(String.valueOf(user.getText()));
                        credentials.add(String.valueOf(password.getText()));

                        socket.connecta(uri);
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        socket.client.send("UC#"+user.getText().toString()+"#"+password.getText().toString());

                        user.setText("");
                        password.setText("");
                    }
                }
            });
    }

    public void login(String message) {
        if (message.equals("V")) {
            socket.client.send("XML");
            RemotControlActivity.socket = MainActivity.socket;
            Intent intent = new Intent(MainActivity.this, RemotControlActivity.class);
            startActivity(intent);
        } else {
            AlertDialog.Builder popup = new AlertDialog.Builder(MainActivity.this);
            popup.setTitle("Log In, Check the credentials again");
            popup.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            popup.create();
            popup.show();
        }
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