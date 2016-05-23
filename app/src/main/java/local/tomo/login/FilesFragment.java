package local.tomo.login;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import local.tomo.login.network.InternetFragment;

/**
 * Created by tomo on 2016-05-03.
 */
public class FilesFragment extends InternetFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.files_layout, container, false);
        return rootView;
    }
}
