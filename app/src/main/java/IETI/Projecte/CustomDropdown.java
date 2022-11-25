package IETI.Projecte;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

@SuppressLint("AppCompatCustomView")
public class CustomDropdown extends Spinner {

    private String id;
    private String block;
    private String label;
    private int defaultValue;
    private ArrayList<String> options = new ArrayList<String>();

    public CustomDropdown(Context context, String id, String block, String label, int defaultValue, ArrayList<String> options) {
        super(context);
        this.id = id;
        this.block = block;
        this.label = label;
        this.defaultValue = defaultValue;
        this.options = options;

        String[] adapterOptions = new String[options.size()];
        for(int i = 0; i < options.size(); i++){
            adapterOptions[i] = options.get(i);
        }

        ArrayAdapter<String> adaper = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, adapterOptions);
        this.setAdapter(adaper);
        this.setSelection(defaultValue);

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

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
        this.setSelection(defaultValue);
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }



    @Override
    public String toString() {
        return "%CustomDropdown:id=" + id + ", block=" + block + ", label=" + label + ", defaultValue=" + defaultValue
                + ", options=" + options;
    }

}
