package IETI.Projecte;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.java_websocket.client.WebSocketClient;

public class RemotControlActivity extends AppCompatActivity {

    LinearLayout exteriorLinearLayout;
    private final int CONTROL_LAYOUT_PADDING = 20;
    CustomControlLayout customControlLayout;
    static WebSocketClient client;
    static void setStateConnected(WebSocketClient c){
        client = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remot_control);

        Button logoutButton = new Button(this);
        logoutButton.setText("LOGOUT");
        logoutButton.setBackgroundColor(Color.rgb(89,7,166));
        logoutButton.setTextColor(Color.WHITE);

        exteriorLinearLayout = new LinearLayout(this);
        exteriorLinearLayout.setOrientation(LinearLayout.VERTICAL);
        exteriorLinearLayout.setBackgroundColor(Color.WHITE);
        exteriorLinearLayout.setPadding(CONTROL_LAYOUT_PADDING, CONTROL_LAYOUT_PADDING , CONTROL_LAYOUT_PADDING, CONTROL_LAYOUT_PADDING);

        Model.loadDataFromServer(this);

        for(CustomControlLayout control : Model.customControls){
            exteriorLinearLayout.addView(control);
        }

        exteriorLinearLayout.addView(logoutButton);
        this.setContentView(exteriorLinearLayout);


        AlertDialog dialog = serverDisconnectedDialog();


        logoutButton.setOnClickListener(new View.OnClickListener() {
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