package local.tomo.medi.swipe;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import local.tomo.medi.disease.AddDiseaseActivity;
import local.tomo.medi.disease.DiseasesAdapter;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Patient;
import local.tomo.medi.patient.AddPatientActivity;
import local.tomo.medi.patient.AllPatientsAdapter;


public class DiseaseFragment extends Fragment {

    private static final String TAG = "meditomo";

    private static final String ALL = "Wszystkie";
    private static final String ACTIVE = "Aktywne";
    private static final String ENDED = "Zakończone";


    private DatabaseHelper databaseHelper;

    List<Patient> patients;
    private int patientId = 0;

    private Spinner spinnerPatient;
    private Spinner spinnerStatus;

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_fragment_disease, container, false);
        spinnerPatient = (Spinner) view.findViewById(R.id.spinnerPatient);
        spinnerStatus = (Spinner) view.findViewById(R.id.spinnerStatus);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvDiseases);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshSpinner();
        setDiseases();


        fabAdd = (FloatingActionButton) view.findViewById(R.id.fabAdd);
        if(patientId == 0)
            fabAdd.setVisibility(View.INVISIBLE);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddDiseaseActivity.class);
                intent.putExtra("patientId", patientId);
                startActivityForResult(intent, 1);
            }
        });

        spinnerPatient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Patient patient = patients.get(position);
                patientId = patient.getId();
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
                setDiseases();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDiseases();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return view;

    }

    private void setDiseases() {
        List<Disease> diseases = null;
        String status = spinnerStatus.getSelectedItem().toString();
        try {
            QueryBuilder<Disease, Integer> queryBuilder = getHelper().getDiseaseDao().queryBuilder();
            queryBuilder.where().eq("patient_id", patientId);
            switch (status) {
                case ALL:
                    break;
                case ACTIVE:
                    queryBuilder.where().eq("patient_id", patientId).and().eq("stopLong", 0);
                    break;
                case ENDED:
                    queryBuilder.where().eq("patient_id", patientId).and().ne("stopLong", 0);
                    break;
                default:
                    break;
            }
            diseases = queryBuilder.query();
            Log.d(TAG, "setDiseases: koniec");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DiseasesAdapter diseasesAdapter = new DiseasesAdapter(diseases, getContext());
        recyclerView.setAdapter(diseasesAdapter);
    }

    public void refreshSpinner() {
        List<String> listPatients = new ArrayList<String>();
        List<String> listStatus = new ArrayList<String>();

        try {
            Dao<Patient, Integer> patientDao = getHelper().getPatientDao();
            QueryBuilder<Patient, Integer> queryBuilder = patientDao.queryBuilder();
            queryBuilder.orderBy("lastUse", false);
            patients = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        patientId = patients.get(0).getId();
        for (Patient patient : patients) {
                listPatients.add(patient.getName());
        }
        listStatus.add(ACTIVE);
        listStatus.add(ENDED);
        listStatus.add(ALL);
        ArrayAdapter<String> dataAdapterPatients = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, listPatients);
        dataAdapterPatients.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatient.setAdapter(dataAdapterPatients);

        ArrayAdapter<String> dataAdapterStatus = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, listStatus);
        dataAdapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(dataAdapterStatus);
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(),DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        setDiseases();
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
