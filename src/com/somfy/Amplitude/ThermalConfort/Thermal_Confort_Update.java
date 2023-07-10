package com.somfy.Amplitude.ThermalConfort;

import com.somfy.parameter.Parameter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.somfy.Amplitude.GetActivity.getUserActivity;

public class Thermal_Confort_Update {

    public static void main(String[] args) throws Exception {
        System.out.println(ThermalComfortUpdate());
    }

    /**
     * for event thermal_comfort_completed/thermal_comfort_updated
     * @return False if an error is detected test
     * @throws Exception
     */
    public static boolean ThermalComfortUpdate() throws Exception {
        Parameter p = new Parameter("testParameter");
        JSONObject Activity = getUserActivity(p.getValue("Amplitude_User"),p.getValue("Amplitude_Activity_Limit"));
        if(Activity.has("events")){
            JSONArray ArrayEvent = Activity.getJSONArray("events");
            for(Object o : ArrayEvent){
                JSONObject ObjectEvent = (JSONObject) o;
                //if(ObjectEvent.getString("event_type").equals("thermal_comfort_completed")){
                if(Objects.equals(ObjectEvent.getString("event_type"), "thermal_comfort_updated")){
                    JSONObject eventProperties = ObjectEvent.getJSONObject("event_properties");
                    System.out.println(eventProperties);
                    Iterator<String> keys = eventProperties.keys();
                    while(keys.hasNext()) {
                        String key = keys.next();
                        boolean getProperties = getThermalConfortUpdatedProperties().contains(key);
                        if(!getProperties){
                            System.out.println("Event " + key + " non present dans les event_properties.");
                            System.out.println("JSON = " + ObjectEvent);
                            return false;
                        }else {
                            String valuePropertie = eventProperties.get(key).toString();
                            boolean getPropertie = getThermalConfortUpdatedProperties(key).contains(valuePropertie);
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

    /**
     * @return List of Properties
     */
    private static List getThermalConfortUpdatedProperties(){
        return Arrays.asList("nb_sensor", "nb_mix_sensor_hour", "different_lux", "different_hours");
    }

    /**
     * @param Propertie
     * @return List of Values we want
     */
    private static List getThermalConfortUpdatedProperties(String Propertie){
        List<String> Properties = null;
        switch (Propertie){
            case("nb_sensor"):
            case("nb_mix_sensor_hour"):
                Properties = Arrays.asList("0","1", "2", "3", "4");
                break;
            case("different_lux"):
            case("different_hours"):
                Properties = Arrays.asList("true","false");
                break;
        }
        return Properties;
    }
}
