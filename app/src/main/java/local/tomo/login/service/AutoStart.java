package local.tomo.login.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by tomo on 2016-05-28.
 */
public class AutoStart extends BroadcastReceiver {

    Alarm alarm = new Alarm();
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            alarm.SetAlarm(context);
        }
    }
}
