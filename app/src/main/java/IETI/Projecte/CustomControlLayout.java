package IETI.Projecte;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

public class CustomControlLayout extends LinearLayout {

    private String controlId;
    private LinearLayout slidersLinearLayout;
    private LinearLayout switchLinearLayout;
    private LinearLayout dropdownLinearLayout;
    private LinearLayout sensorsLinearLayout;

    private final int CONTROL_LAYOUT_PADDING = 40;

    public CustomControlLayout(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setBaselineAligned(true);
        this.setPadding(CONTROL_LAYOUT_PADDING,CONTROL_LAYOUT_PADDING,CONTROL_LAYOUT_PADDING,CONTROL_LAYOUT_PADDING);

        GradientDrawable border = new GradientDrawable();
        border.setColor(Color.LTGRAY);
        border.setStroke(15,Color.WHITE);
        this.setBackground(border);

        slidersLinearLayout = new LinearLayout(context);
        slidersLinearLayout.setOrientation(LinearLayout.VERTICAL);
        slidersLinearLayout.setBackgroundColor(Color.LTGRAY);

        switchLinearLayout = new LinearLayout(context);
        switchLinearLayout.setOrientation(LinearLayout.VERTICAL);
        switchLinearLayout.setBackgroundColor(Color.LTGRAY);

        dropdownLinearLayout = new LinearLayout(context);
        dropdownLinearLayout.setOrientation(LinearLayout.VERTICAL);
        dropdownLinearLayout.setBackgroundColor(Color.LTGRAY);

        sensorsLinearLayout = new LinearLayout(context);
        sensorsLinearLayout.setOrientation(LinearLayout.VERTICAL);
        sensorsLinearLayout.setBackgroundColor(Color.LTGRAY);


        this.addView(slidersLinearLayout);
        this.addView(switchLinearLayout);
        this.addView(dropdownLinearLayout);
        this.addView(sensorsLinearLayout);

    }

    public void setControlId(String controlId){
        this.controlId = controlId;
    }

    public String getControlId(){
        return this.controlId;
    }


    public void addSliderToPanel(Slider slider){
        slidersLinearLayout.addView(slider);
    }

    public void addSwitchToPanel(Switch toggle){
        switchLinearLayout.addView(toggle);
    }

    public void addDropdownToPanel(Spinner spinner){
        dropdownLinearLayout.addView(spinner);
    }

    public void addSensorToPanel(TextView textView){
        sensorsLinearLayout.addView(textView);
    }



}
