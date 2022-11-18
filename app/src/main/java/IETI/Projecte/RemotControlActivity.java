package IETI.Projecte;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;

public class RemotControlActivity extends AppCompatActivity {

    static WebSocketClient client;
    static void setStateConnected(WebSocketClient c){
        client = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remot_control);

        AlertDialog dialog = serverDisconnectedDialog();
        Button logout = findViewById(R.id.log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    public AlertDialog serverDisconnectedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You have disconnected from the server");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try{
                    client.close();
                    changeActiviy();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
        return builder.create();
    }

    public void changeActiviy() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}