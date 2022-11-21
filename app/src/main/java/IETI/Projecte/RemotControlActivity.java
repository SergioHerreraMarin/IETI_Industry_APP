package IETI.Projecte;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;

public class RemotControlActivity extends AppCompatActivity {

    LinearLayout exteriorLinearLayout;
    private final int CONTROL_LAYOUT_PADDING = 20;
    // CustomControlLayout customControlLayout;
    static WebSocketClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remot_control);

        Button logoutButton = new Button(this);
        logoutButton.setText("LOG OUT");
        logoutButton.setBackgroundColor(Color.rgb(89,7,166));
        logoutButton.setTextColor(Color.WHITE);

        exteriorLinearLayout = new LinearLayout(this);
        exteriorLinearLayout.setOrientation(LinearLayout.VERTICAL);
        exteriorLinearLayout.setBackgroundColor(Color.WHITE);
        exteriorLinearLayout.setPadding(CONTROL_LAYOUT_PADDING, CONTROL_LAYOUT_PADDING , CONTROL_LAYOUT_PADDING, CONTROL_LAYOUT_PADDING);

        if(Model.componentsData != null){
            Model.loadDataFromServer(this);
            for(CustomControlLayout control : Model.customControls){
                exteriorLinearLayout.addView(control);
            }
        } else {
            Toast(RemotControlActivity.this,"There aren't any componenets loaded in the server yet.");
        }


        exteriorLinearLayout.addView(logoutButton);
        this.setContentView(exteriorLinearLayout);



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverDisconnectedDialog().show();
            }
        });


    }

    static void setStateConnected(WebSocketClient c){
        client = c;
    }

    public AlertDialog serverDisconnectedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You have disconnected from the server");
        builder.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                client.close();
                changeActiviy();
            }
        });
        return builder.create();
    }

    public void changeActiviy() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Toast(Activity activity, CharSequence text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}