package local.tomo.login.swipe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import local.tomo.login.R;
import local.tomo.login.ormlite.DatabaseHelper;
import local.tomo.login.ormlite.data.UserDetails;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiseaseFragment extends Fragment {

    private DatabaseHelper databaseHelper = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disease, container, false);
        UserDetails userDetails = new UserDetails();
        userDetails.setName("tomo");

        try {
            Dao<UserDetails, Integer> userDao = getHelper().getUserDao();
            userDao.create(userDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //start temp
        //TODO remove this
        Button button = (Button) view.findViewById(R.id.button);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    Dao<UserDetails, Integer> userDao = getHelper().getUserDao();
                    List<UserDetails> users = userDao.queryForAll();
                    for (UserDetails user : users) {
                        Log.d("tomo", "user: " + user.getId());
                    }
                    Toast.makeText(getContext(), "user: " + users.size(), Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                return false;
            }
        });


        //end temp


        return view;

    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(),DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
