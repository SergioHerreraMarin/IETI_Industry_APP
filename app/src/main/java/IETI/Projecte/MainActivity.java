package IETI.Projecte;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // Parámetros para la conexión

    int port = 8888;
    static WebSocket socket = new WebSocket();

    EditText server;
    EditText user;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        server = findViewById(R.id.serverConnection);
        server.setText("10.0.2.2");

        user = findViewById(R.id.user);
        password = findViewById(R.id.pwd);

        WebSocket.act = MainActivity.this;

        Button entrarAConexion = findViewById(R.id.button);
        entrarAConexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!server.getText().toString().contains("10.0.2.2")){
                    Toast(MainActivity.this,"Server IP incorrect");
                }

                if(TextUtils.isEmpty(server.getText()) || TextUtils.isEmpty(user.getText()) || TextUtils.isEmpty(password.getText())){
                    Toast(MainActivity.this, "All fields are required");
                } else {
                    String uri = "ws://" + server.getText().toString() + ":" + port;
                    socket.connecta(uri);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    socket.client.send("UC#"+user.getText().toString()+"#"+password.getText().toString());
                }
            }
        });



    }

    public void loginActivity(String message) {
        if (message.equals("V")) {
            socket.client.send("XML");
            RemotControlActivity.socket = MainActivity.socket;
            Intent intent = new Intent(MainActivity.this, RemotControlActivity.class);
            startActivity(intent);
        } else if (message.equals("NV")){
            userIncorrect();
        }
    }

    public void userIncorrect(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("User or password incorrect, press OK to close this window");
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                builder.create().show();
            }
        });
    };

    public void Toast(Activity activity, CharSequence text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}