package IETI.Projecte;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {

    public static String componentsData;
    public static ArrayList<CustomControlLayout> customControls = new ArrayList<CustomControlLayout>();
    public static ArrayList<Object> customComponents = new ArrayList<Object>();

    public static String[] splitToControls(){
        if(componentsData != null){
            return componentsData.split(";");
        }
        return null;
    }

    public static void loadDataFromServer(Context context){

        if(componentsData != null){

            String[] controls = splitToControls();

            for(String control : controls){ //Por cada control crea uno.
                //Log.i("CONTROL: ", control);
                CustomControlLayout customControl = new CustomControlLayout(context);

                String[] componentsBlockid = control.split("¿"); //[0] = componente [1] bloque id
                //Log.i("CONTROLLL: ", componentsBlockid[0]);
                //Log.i("BLOQUE ID: ", componentsBlockid[1]);
                customControl.setControlId(componentsBlockid[1]);

                String components[] = componentsBlockid[0].split("%");
                for(String component : components){
                    //Log.i("COMPONENT: ", component);
                    if(component.length() != 0){
                        String keyValues[] = component.split(":");
                        //Log.i("KEY: ", keyValues[0]);
                        //Log.i("VALUES: ", keyValues[1]); //Aquí se almacenan los valores...
                        switch(keyValues[0]){

                            case "CustomSlider": //KeyValues[1] para sacar los valores por defecto del componente actual.

                                String sliderId = "", sliderBlock = "", sliderLabel = "";
                                float sliderDefaultValue = 5, sliderMin = 0, sliderMax = 10, sliderStep = 0;

                                String[] keyValuesSlider = keyValues[1].split(",");
                                for(String keyValue: keyValuesSlider){
                                    //Log.i("KEYVALUE: ", keyValue);
                                    String keysValues[] = keyValue.split("=");
                                    //Log.i("ONLY KEY: " , keysValues[0]);
                                    //Log.i("ONLY VALUE: ", keysValues[1]);

                                    switch(keysValues[0].trim()){
                                        case "id":
                                            sliderId = keysValues[1];
                                            break;
                                        case "block":
                                            sliderBlock = keysValues[1];
                                            break;
                                        case "label":
                                            sliderLabel = keysValues[1];
                                            break;
                                        case "defaultValue":
                                            sliderDefaultValue = Float.valueOf(keysValues[1]);
                                            break;
                                        case "min":
                                            sliderMin = Float.valueOf(keysValues[1]);
                                            break;
                                        case "max":
                                            sliderMax = Float.valueOf(keysValues[1]);
                                            break;
                                        case "step":
                                            sliderStep = Float.valueOf(keysValues[1]);
                                            break;
                                        default:
                                            Log.i("ERROR", "valor del slider no encontrado");
                                            break;
                                    }
                                }
                                //CREATE CUSTOM SLIDER
                                CustomSlider slider = new CustomSlider(context, sliderId, sliderBlock, sliderLabel, sliderDefaultValue, sliderMin, sliderMax, sliderStep);
                                customComponents.add(slider);
                                break;

                            case "CustomSwitch":

                                String switchId = "", switchBlock = "", switchLabel = "", switchDefaultValue = "";
                                String[] keyValuesSwitch = keyValues[1].split(",");

                                for(String keyValue: keyValuesSwitch){
                                    //Log.i("KEYVALUE: ", keyValue);
                                    String keysValues[] = keyValue.split("=");
                                    //Log.i("ONLY KEY: " , keysValues[0]);
                                    //Log.i("ONLY VALUE: ", keysValues[1]);

                                    switch(keysValues[0].trim()){
                                        case "id":
                                            switchId = keysValues[1];
                                            break;
                                        case "block":
                                            switchBlock = keysValues[1];
                                            break;
                                        case "label":
                                            switchLabel = keysValues[1];
                                            break;
                                        case "defaultValue":
                                            switchDefaultValue = keysValues[1];
                                            break;
                                        default:
                                            Log.i("ERROR", "valor del switch no encontrado");
                                            break;
                                    }
                                }
                                //CREATE CUSTOM SWITCH
                                CustomSwitch switchs = new CustomSwitch(context, switchId, switchBlock, switchLabel, switchDefaultValue);
                                customComponents.add(switchs);
                                break;

                            case "CustomSensor":

                                String sensorId = "", sensorBlock = "", sensorLabel = "", sensorUnits = "", sensorThresholdlow = "", sensorThresholdhigh = "";
                                String[] keyValuesSensor = keyValues[1].split(",");

                                for(String keyValue: keyValuesSensor){

                                    Log.i("KEYVALUE: ", keyValue);
                                    String keysValues[] = keyValue.split("=");
                                    Log.i("ONLY KEY: " , keysValues[0]);
                                    Log.i("ONLY VALUE: ", keysValues[1]);

                                    switch(keysValues[0].trim()){
                                        case "id":
                                            sensorId = keysValues[1];
                                            break;
                                        case "block":
                                            sensorBlock = keysValues[1];
                                            break;
                                        case "label":
                                            sensorLabel = keysValues[1];
                                            break;
                                        case "units":
                                            sensorUnits = keysValues[1];
                                            break;
                                        case "thresholdlow":
                                            sensorThresholdlow = keysValues[1];
                                            break;
                                        case "thresholdhigh":
                                            sensorThresholdhigh = keysValues[1];
                                        default:
                                            Log.i("ERROR", "valor del dropdown no encontrado");
                                            break;
                                    }
                                }
                                //CREATE CUSTOM SENSOR
                                CustomSensor sensor = new CustomSensor(sensorId, sensorBlock, sensorLabel, sensorUnits, sensorThresholdlow, sensorThresholdhigh);
                                TextView sens = sensor.createCustomSensor(context);
                                customControl.addSensorToPanel(sens);
                                break;

                            case "CustomDropdown":

                                String dropdownId = "", dropdownBlock = "", dropdownLabel = "";
                                int dropdownDefaultValue = 0;
                                ArrayList<String> dropdownOptions = new ArrayList<String>();

                                String[] keyValuesDropdown = keyValues[1].split(",");

                                for(String keyValue: keyValuesDropdown){

                                    //Log.i("KEYVALUE: ", keyValue);
                                    String keysValues[] = keyValue.split("=");
                                    //Log.i("ONLY KEY: " , keysValues[0]);
                                    //Log.i("ONLY VALUE: ", keysValues[1]);

                                    switch(keysValues[0].trim()){
                                        case "id":
                                            dropdownId = keysValues[1];
                                            break;
                                        case "block":
                                            dropdownBlock = keysValues[1];
                                            break;
                                        case "label":
                                            dropdownLabel = keysValues[1];
                                            break;
                                        case "defaultValue":
                                            dropdownDefaultValue = Integer.valueOf(keysValues[1]);
                                            break;
                                        case "options":
                                            String[] optionsValues = keysValues[1].split("!");
                                            for(int i = 0; i < optionsValues.length; i++){
                                                dropdownOptions.add(optionsValues[i]);
                                            }
                                            break;
                                        default:
                                            Log.i("ERROR", "valor del dropdown no encontrado");
                                            break;
                                    }
                                }
                                //CREATE CUSTOM DROPDOWN
                                CustomDropdown dropdown = new CustomDropdown(context, dropdownId, dropdownBlock, dropdownLabel, dropdownDefaultValue, dropdownOptions);
                                customComponents.add(dropdown);
                                break;
                            default:
                                Log.i("ERROR: ", "*Clave desconocida*");
                                break;
                        }
                    }
                }
                customControls.add(customControl);
            }

        }
    }

    public static void updateComponent(String message){

        String idBlock = "", idComponent = "",  currentComponentValue = "";

        String[] componentProperties = message.split("!");

        for(String propert : componentProperties){
            String[] nameValue = propert.split(":"); //[0] value name, [1] value

            switch(nameValue[0]){
                case "blockID":
                    idBlock = nameValue[1];
                    break;
                case "id":
                    idComponent = nameValue[1];
                    break;
                case "current":
                    currentComponentValue = nameValue[1];
                    break;
                default:
                    System.out.println("Valor no encontrado");
                break;

            }
        }

        for(Object comp : Model.customComponents){
            if(comp instanceof CustomSlider){
                if(((CustomSlider)comp).getIdd().equals(idComponent)){
                    ((CustomSlider)comp).setDefaultValue(Float.valueOf(currentComponentValue));

                }
            }else if(comp instanceof CustomDropdown){
                if(((CustomDropdown)comp).getIdd().equals(idComponent)){
                    ((CustomDropdown)comp).setDefaultValue(Integer.valueOf(currentComponentValue));
                }
            }else if(comp instanceof CustomSwitch){
                if(((CustomSwitch)comp).getIdd().equals(idComponent)){
                    ((CustomSwitch)comp).setDefaultValue(currentComponentValue);
                }
            }
        }

    }


}
