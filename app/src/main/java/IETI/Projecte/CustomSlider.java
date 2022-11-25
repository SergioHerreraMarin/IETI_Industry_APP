package IETI.Projecte;

import android.content.Context;

import com.google.android.material.slider.Slider;

public class CustomSlider extends Slider {

    private String id;
    private String block;
    private String label;
    private float defaultValue;
    private float min;
    private float max;
    private float step;

    public CustomSlider(Context context, String id, String block, String label, float defaultValue, float min, float max, float step) {
        super(context);
        this.id = id;
        this.block = block;
        this.label = label;
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.step = step;

        this.setValueFrom(min);
        this.setValueTo(max);
        //slider.setStepSize(step);
        this.setValue(defaultValue);

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

    public float getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(float defaultValue) {
        this.defaultValue = defaultValue;
        this.setValue(defaultValue);
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


    @Override
    public String toString() {
        return "%CustomSlider:id=" + id + ", block=" + block + ", label=" + label + ", defaultValue=" + defaultValue
                + ", min=" + min + ", max=" + max + ", step=" + step;
    }

}
