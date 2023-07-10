package com.somfy.Amplitude.ThermalConfort;

import com.somfy.parameter.Parameter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.somfy.Amplitude.GetActivity.getUserActivity;

public class Thermal_Comfort_Modify_Condition {

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
                if(ObjectEvent.getString("event_type").equals("thermal_comfort_modify_condition")){
                    JSONObject eventProperties = ObjectEvent.getJSONObject("event_properties");
                    System.out.println(eventProperties);
                    Iterator<String> keys = eventProperties.keys();
                    while(keys.hasNext()) {
                        String key = keys.next();
                        boolean getProperties = getThermalConfortModifyContitionProperties().contains(key);
                        if(!getProperties){
                            System.out.println("Event " + key + " non present dans les event_properties.");
                            System.out.println("JSON = " + ObjectEvent);
                            return false;
                        }else {
                            String valuePropertie = eventProperties.get(key).toString();
                            if(!Objects.equals(key, "start_minutes") && !Objects.equals(key, "stop_minutes")
                                    && !Objects.equals(key, "luminosity")){
                                boolean getPropertie = getThermalConfortModifyContitionProperties(key).contains(valuePropertie);
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
        }
        return true;
    }

    private static List getThermalConfortModifyContitionProperties(){
        return Arrays.asList("luminosity", "season", "start_minutes", "stop_minutes","time_type");
    }

    private static List getThermalConfortModifyContitionProperties(String Propertie){
        List<String> Properties = null;
        switch (Propertie){
            case("season"):
                Properties = Arrays.asList("SUMMER","WINTER");
                break;
            case("time_type"):
                Properties = Arrays.asList("RANGE","AFTER","BEFORE");
                break;
        }
        return Properties;
    }
}
