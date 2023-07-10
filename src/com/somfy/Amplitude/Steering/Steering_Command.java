package com.somfy.Amplitude.Steering;

import com.somfy.parameter.Parameter;
import org.json.*;
import java.util.Objects;

import static com.somfy.Amplitude.GetActivity.getUserActivity;

public class Steering_Command {

    public static void main(String[] args) throws Exception {
        System.out.println(getSteeringCommand_uiclass());
        System.out.println(getSteeringCommand_controllable());
        System.out.println(getSteeringCommand_has_tempo());
        System.out.println(getSteeringCommand_ui_widget());
    }

    public static JSONObject getSteeringCommand() throws Exception {
        Parameter p = new Parameter("testParameter");
        JSONObject Activity = getUserActivity(p.getValue("Amplitude_User"),p.getValue("Amplitude_Activity_Limit"));
        if(Activity.has("events")){
            JSONArray ArrayEvent = Activity.getJSONArray("events");
            for(Object o : ArrayEvent){
                JSONObject ObjectEvent = (JSONObject) o;
                if(Objects.equals(ObjectEvent.getString("event_type"), "Steering command")){
                    //System.out.println(eventProperties);
                    return ObjectEvent.getJSONObject("event_properties");
                }
            }
        }
        return null;
    }

    public static String getSteeringCommand_uiclass() throws Exception {
        return getSteeringCommand().getString("ui_class");
    }

    public static String getSteeringCommand_controllable() throws Exception {
        return getSteeringCommand().getString("controllable");
    }

    public static Boolean getSteeringCommand_has_tempo() throws Exception {
        return getSteeringCommand().getBoolean("has_tempo");
    }

    public static String getSteeringCommand_ui_widget() throws Exception {
        return getSteeringCommand().getString("ui_widget");
    }
}
