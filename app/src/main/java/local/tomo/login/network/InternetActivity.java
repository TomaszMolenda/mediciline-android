package local.tomo.login.network;

import android.support.v7.app.AppCompatActivity;

import com.octo.android.robospice.SpiceManager;

/**
 * Created by tomo on 2016-05-02.
 */
public class InternetActivity extends AppCompatActivity {

    protected SpiceManager spiceManager = new SpiceManager(RestService.class);


    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }
}
