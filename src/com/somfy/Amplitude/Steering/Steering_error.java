package com.somfy.Amplitude.Steering;

import com.somfy.parameter.Parameter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import static com.somfy.Amplitude.GetActivity.*;

public class Steering_error {

    public static void main(String[] args) throws Exception {
        System.out.println(getSteeringError_controllable());
        System.out.println(getSteeringError_type());
    }

    public static JSONObject getSteeringError() throws Exception {
        Parameter p = new Parameter("testParameter");
        JSONObject Activity = getUserActivity(p.getValue("Amplitude_User"),p.getValue("Amplitude_Activity_Limit"));
        if(Activity.has("events")){
            JSONArray ArrayEvent = Activity.getJSONArray("events");
            for(Object o : ArrayEvent){
                JSONObject ObjectEvent = (JSONObject) o;
                if(Objects.equals(ObjectEvent.getString("event_type"), "Steering error")){
                    //System.out.println(eventProperties);
                    return ObjectEvent.getJSONObject("event_properties");
                }
            }
        }
        return null;
    }

    public static String getSteeringError_controllable() throws Exception {
        return getSteeringError().getString("controllable");
    }

    public static String getSteeringError_type() throws Exception {
        return getSteeringError().getString("Type");
    }
}
