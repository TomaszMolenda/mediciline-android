package local.tomo.medi.swipe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Patient;


public class DiseaseFragment extends Fragment {

    private static final String TAG = "meditomo";

    private DatabaseHelper databaseHelper;

    List<Patient> patients;

    private Spinner spinnerPatient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_fragment_disease, container, false);
        spinnerPatient = (Spinner) view.findViewById(R.id.spinnerPatient);
        refreshSpinner();

        spinnerPatient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Patient patient = patients.get(position);
                for (Patient patientIter  : patients) {
                    if (patientIter.equals(patient))
                        patientIter.setLastUse(true);
                    else
                        patientIter.setLastUse(false);
                    try {
                        Dao<Patient, Integer> patientDao = getHelper().getPatientDao();
                        patientDao.update(patientIter);
                    } catch (SQLException e) {}
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }

    public void refreshSpinner() {
        List<String> list = new ArrayList<String>();
        try {
            Dao<Patient, Integer> patientDao = getHelper().getPatientDao();
            QueryBuilder<Patient, Integer> queryBuilder = patientDao.queryBuilder();
            queryBuilder.orderBy("lastUse", false);
            patients = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "refreshSpinner: "+patients.toString());
        for (Patient patient : patients) {
                list.add(patient.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatient.setAdapter(dataAdapter);
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
