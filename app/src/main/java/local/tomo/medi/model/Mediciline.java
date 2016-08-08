package local.tomo.medi.model;

import android.app.Application;
import android.content.Context;

public class Mediciline extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        Mediciline.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Mediciline.context;
    }
}
