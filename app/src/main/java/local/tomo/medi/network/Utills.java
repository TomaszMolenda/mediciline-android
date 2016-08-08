package local.tomo.medi.network;

import android.util.Log;

public class Utills {

    public static final String TAG = "meditomo";

    public static String readJsonError(String json) {
        int message = json.indexOf("message");
        if(message != -1) {
            json = json.substring(message + 12);
            int i = json.indexOf("\\\"}");
            json = json.substring(0, i);
        }
        return json;
    }
}
