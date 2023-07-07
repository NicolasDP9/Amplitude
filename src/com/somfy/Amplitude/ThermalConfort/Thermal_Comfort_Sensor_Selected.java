package com.somfy.Amplitude.ThermalConfort;

import com.somfy.parameter.Parameter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.somfy.Amplitude.GetActivity.*;

public class Thermal_Comfort_Sensor_Selected {

    public static void main(String[] args) throws Exception {
        System.out.println(ThermalComfortSensor_Selected());
    }

    public static boolean ThermalComfortSensor_Selected() throws Exception {
        Parameter p = new Parameter("testParameter");
        JSONObject Activity = getUserActivity(p.getValue("Amplitude_User"),p.getValue("Amplitude_Activity_Limit"));
        if(Activity.has("events")){
            JSONArray ArrayEvent = Activity.getJSONArray("events");
            for(Object o : ArrayEvent){
                JSONObject ObjectEvent = (JSONObject) o;
                if(ObjectEvent.getString("event_type").equals("thermal_comfort_sensor_selected")){
                    JSONObject eventProperties = ObjectEvent.getJSONObject("event_properties");
                    System.out.println(eventProperties);
                    Iterator<String> keys = eventProperties.keys();
                    while(keys.hasNext()) {
                        String key = keys.next();
                        boolean getProperties = getThermalConfortSensorSelectedProperties().contains(key);
                        if(!getProperties){
                            System.out.println("Event " + key + " non present dans les event_properties.");
                            System.out.println("JSON = " + ObjectEvent);
                            return false;
                        }else {
                            String valuePropertie = eventProperties.get(key).toString();
                            boolean getPropertie = getThermalConfortSensorSelectedProperties(key).contains(valuePropertie);
                            if(!getPropertie){
                                System.out.println("Valeur de l'event " + key + " n'est pas bon. Value = " + valuePropertie);
                                System.out.println("JSON = " + ObjectEvent);
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private static List getThermalConfortSensorSelectedProperties(){
        return List.of("selected");
    }

    private static List getThermalConfortSensorSelectedProperties(String Propertie){
        List<String> Properties = null;
        if (Propertie.equals("selected")) {
            Properties = Arrays.asList("NONE", "LUMINOSITY");
        }
        return Properties;
    }
}
