package IETI.Projecte;

import android.content.Context;
import android.widget.Switch;

public class CustomSwitch extends Switch {

    private String id;
    private String block;
    private String label;
    private String defaultValue;

    public CustomSwitch(Context context, String id,String block, String label, String defaultValue) {
        super(context);
        this.id = id;
        this.block = block;
        this.label = label;
        this.defaultValue = defaultValue;

        if(defaultValue.equals("on")){
            this.setChecked(true);
        }else{
            this.setChecked(false);
        }

        this.setText(label);
    }

    public String getIdd() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;

        if(defaultValue.equals("on")){
            this.setChecked(true);
        }else{
            this.setChecked(false);
        }
    }


    @Override
    public String toString() {
        return "%CustomSwitch:id=" + id + ", block=" + block + ", label=" + label + ", defaultValue=" + defaultValue;
    }

}
