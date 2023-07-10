package com.somfy.Amplitude.Steering;

import com.somfy.parameter.Parameter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import static com.somfy.Amplitude.GetActivity.getUserActivity;

public class Steering_Touch {

    public static void main(String[] args) throws Exception {
        System.out.println(getSteeringTouch_controllable());
        System.out.println(getSteeringTouch_type());
    }

    public static JSONObject getSteeringTouch() throws Exception {
        Parameter p = new Parameter("testParameter");
        JSONObject Activity = getUserActivity(p.getValue("Amplitude_User"),p.getValue("Amplitude_Activity_Limit"));
        if(Activity.has("events")){
            JSONArray ArrayEvent = Activity.getJSONArray("events");
            JSONObject ObjetEvent = new JSONObject(ArrayEvent);
            for(Object o : ArrayEvent){
                JSONObject ObjectEvent = (JSONObject) o;
                if(Objects.equals(ObjectEvent.getString("event_type"), "Steering touch")){
                    //System.out.println(eventProperties);
                    return ObjectEvent.getJSONObject("event_properties");
                }
            }
        }
        return null;
    }

    public static String getSteeringTouch_controllable() throws Exception {
        return getSteeringTouch().getString("controllable");
    }

    public static String getSteeringTouch_type() throws Exception {
        return getSteeringTouch().getString("type");
    }
}
