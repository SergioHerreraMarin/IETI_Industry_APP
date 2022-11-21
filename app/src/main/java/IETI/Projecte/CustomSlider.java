package IETI.Projecte;

import android.content.Context;

import com.google.android.material.slider.Slider;

public class CustomSlider {

    private String id;
    private String block;
    private String label;
    private float defaultValue;
    private float min;
    private float max;
    private float step;

    public CustomSlider(String id, String block, String label, float defaultValue, float min, float max, float step) {
        this.id = id;
        this.block = block;
        this.label = label;
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.step = step;

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

    public float getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(float defaultValue) {
        this.defaultValue = defaultValue;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public Slider createCustomSlider(Context context){
        Slider slider = new Slider(context);
        slider.setValueFrom(min);
        slider.setValueTo(max);
        //slider.setStepSize(step);
        slider.setValue(defaultValue);
        return slider;
    }


    @Override
    public String toString() {
        return "%CustomSlider:id=" + id + ", block=" + block + ", label=" + label + ", defaultValue=" + defaultValue
                + ", min=" + min + ", max=" + max + ", step=" + step;
    }

}
