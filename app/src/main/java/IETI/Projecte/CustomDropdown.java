package IETI.Projecte;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class CustomDropdown {

    private String id;
    private String block;
    private String label;
    private int defaultValue;
    private ArrayList<String> options = new ArrayList<String>();

    public CustomDropdown(String id, String block, String label, int defaultValue, ArrayList<String> options) {

        this.id = id;
        this.block = block;
        this.label = label;
        this.defaultValue = defaultValue;
        this.options = options;
    }

    public String getId() {
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
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public Spinner createCustomSpinner(Context context){

            String[] adapterOptions = new String[options.size()];
            for(int i = 0; i < options.size(); i++){
                adapterOptions[i] = options.get(i);
            }

            Spinner spinner = new Spinner(context);
            ArrayAdapter<String> adaper = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, adapterOptions);
            spinner.setAdapter(adaper);
            spinner.setSelection(defaultValue);
            return spinner;
    }


    @Override
    public String toString() {
        return "%CustomDropdown:id=" + id + ", block=" + block + ", label=" + label + ", defaultValue=" + defaultValue
                + ", options=" + options;
    }

}
