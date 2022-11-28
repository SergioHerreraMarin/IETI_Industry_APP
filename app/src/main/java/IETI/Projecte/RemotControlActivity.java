package IETI.Projecte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import org.java_websocket.client.WebSocketClient;

public class RemotControlActivity extends AppCompatActivity {

    static WebSocket socket = new WebSocket();
    LinearLayout exteriorLinearLayout;
    private final int CONTROL_LAYOUT_PADDING = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remot_control);

        WebSocket.act = RemotControlActivity.this;

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
            for(Object comp : Model.customComponents){

                if(comp instanceof CustomSlider){

                    for(CustomControlLayout control : Model.customControls){
                        if(((CustomSlider)comp).getBlock().equals(control.getControlId())){
                            control.addSliderToPanel(((CustomSlider)comp));
                        }
                    }

                    ((CustomSlider)comp).addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
                        @Override
                        public void onStartTrackingTouch(@NonNull Slider slider) {

                        }

                        @Override
                        public void onStopTrackingTouch(@NonNull Slider slider) {
                            ((CustomSlider)comp).setDefaultValue(((CustomSlider)comp).getValue());
                            String data;
                            data = "blockID:" + ((CustomSlider)comp).getBlock() + "!id:" + ((CustomSlider)comp).getIdd() + "!current:" + ((CustomSlider)comp).getDefaultValue();
                            WebSocket.updateServerComponents(data);
                        }
                    });


                }else if(comp instanceof CustomDropdown){

                    for(CustomControlLayout control : Model.customControls){
                        if(((CustomDropdown)comp).getBlock().equals(control.getControlId())){
                            control.addDropdownToPanel(((CustomDropdown)comp));
                        }
                    }

                    ((CustomDropdown)comp).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            ((CustomDropdown)comp).setDefaultValue(((CustomDropdown)comp).getSelectedItemPosition());
                            String data;
                            data = "blockID:" + ((CustomDropdown)comp).getBlock() + "!id:" + ((CustomDropdown)comp).getIdd() + "!current:" + ((CustomDropdown)comp).getDefaultValue();
                            WebSocket.updateServerComponents(data);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }else if(comp instanceof CustomSwitch){

                    for(CustomControlLayout control : Model.customControls){
                        if(((CustomSwitch)comp).getBlock().equals(control.getControlId())){
                            control.addSwitchToPanel(((CustomSwitch)comp));
                        }
                    }

                    ((CustomSwitch)comp).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            if(((CustomSwitch)comp).isChecked()){
                                ((CustomSwitch)comp).setDefaultValue("on");
                            }else{
                                ((CustomSwitch)comp).setDefaultValue("off");
                            }

                            String data;
                            data = "blockID:" + ((CustomSwitch)comp).getBlock() + "!id:" + ((CustomSwitch)comp).getIdd() + "!current:" + ((CustomSwitch)comp).getDefaultValue();
                            WebSocket.updateServerComponents(data);
                        }
                    });

                }
            }

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
                Model.customControls.clear();
                Model.customComponents.clear();
                Model.componentsData = "";
                serverDisconnectedDialog().show();
            }
        });
    }

    public void connectionLost(){
        Intent intent = new Intent(RemotControlActivity.this, MainActivity.class);
        MainActivity.socket = RemotControlActivity.socket;
        startActivity(intent);
    }

    public AlertDialog serverDisconnectedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RemotControlActivity.this);
        builder.setMessage("You have disconnected from the server");
        builder.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                socket.client.close();
                Intent intent = new Intent(RemotControlActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        return builder.create();
    }

    public void serverShutdownDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(RemotControlActivity.this);
                builder.setMessage("The server has been disconnected, press OK to close this window");
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        connectionLost();
                    }
                });
                builder.create().show();
            }
        });
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