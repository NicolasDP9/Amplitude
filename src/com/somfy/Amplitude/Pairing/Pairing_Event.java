package com.somfy.Amplitude.Pairing;

import com.somfy.parameter.Parameter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import static com.somfy.Amplitude.GetActivity.*;

public class Pairing_Event {

    public static void main(String[] args) throws Exception {
        System.out.println(getSteeringCommand_controllable("Device paired"));
        System.out.println(getSteeringCommand_brand("Pairing brand selection"));
        System.out.println(getSteeringCommand_Product("Pairing product selection"));
    }

    public static JSONObject getPairingEvent(String event_type ) throws Exception {
        Parameter p = new Parameter("testParameter");
        JSONObject Activity = getUserActivity(p.getValue("Amplitude_User"),p.getValue("Amplitude_Activity_Limit"));
        if(Activity.has("events")){
            JSONArray ArrayEvent = Activity.getJSONArray("events");
            JSONObject ObjetEvent = new JSONObject(ArrayEvent);
            for(Object o : ArrayEvent){
                JSONObject ObjectEvent = (JSONObject) o;
                if(Objects.equals(ObjectEvent.getString("event_type"), event_type)){
                    //System.out.println(eventProperties);
                    return ObjectEvent.getJSONObject("event_properties");
                }
            }
        }
        return null;
    }

    public static String getSteeringCommand_controllable(String event_type) throws Exception {
        return getPairingEvent(event_type).getString("controllable");
    }

    public static String getSteeringCommand_brand(String event_type) throws Exception {
        return getPairingEvent(event_type).getString("brand");
    }

    public static String getSteeringCommand_Product(String event_type) throws Exception {
        return getPairingEvent(event_type).getString("product");
    }
}
