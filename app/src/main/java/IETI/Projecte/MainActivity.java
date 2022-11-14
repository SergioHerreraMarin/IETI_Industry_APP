package IETI.Projecte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import java.io.Serializable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText server = findViewById(R.id.serverConnection);
        EditText user = findViewById(R.id.user);
        EditText password = findViewById(R.id.pwd);

        Button entrarAConexion = findViewById(R.id.button);
            entrarAConexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(TextUtils.isEmpty(server.getText()) || TextUtils.isEmpty(user.getText()) || TextUtils.isEmpty(password.getText())){
                        Toast("All fields are required");
                    } else {
                        location = String.valueOf(server.getText());
                        uri = "ws://" + location + ":" + port;

                        credentials.add(String.valueOf(user.getText()));
                        credentials.add(String.valueOf(password.getText()));

                        connecta(uri);
                        changeActiviy();
                    }
                }
            });

    }

    public void connecta (String uri) {
        try {
            client = new WebSocketClient(new URI(uri), (Draft) new Draft_6455()) {
                @Override
                public void onMessage(String message) {
                    System.out.println(message);
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
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
            client.connect();

            // Envia las credenciales
            envia();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("Error: " + uri + " no és una direcció URI de WebSocket vàlida");
        }
    }

    protected void envia(){
        try {
            client.send(objToBytes(credentials));
        } catch (WebsocketNotConnectedException e) {
            System.out.println("Connexió perduda ...");
        }
    }


    public void changeActiviy() {
        Intent intent = new Intent(this, RemotControlActivity.class);
        intent.putExtra("uri",uri);
        // intent.putExtra("client", client);
        startActivity(intent);
    }


    // Método para transformar el ArrayList de credenciales al Servidor
    public static byte[] objToBytes(Object obj) {
        byte[] result = null;
        try {
            // Transforma l'objecte a bytes[]
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Object bytesToObject(ByteBuffer arr) {
        Object result = null;
        try {
            // Transforma el ByteButter en byte[]
            byte[] bytesArray = new byte[arr.remaining()];
            arr.get(bytesArray, 0, bytesArray.length);

            // Transforma l'array de bytes en objecte
            ByteArrayInputStream in = new ByteArrayInputStream(bytesArray);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Para mandar el
    public void Toast(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}