package local.tomo.login.network;

import android.support.v4.app.Fragment;

import com.octo.android.robospice.SpiceManager;

/**
 * Created by tomo on 2016-05-03.
 */
public class InternetFragment extends Fragment {

    protected SpiceManager spiceManager = new SpiceManager(RestService.class);



    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(getActivity());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }
}
