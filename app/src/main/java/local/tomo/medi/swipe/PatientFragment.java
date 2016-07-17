package local.tomo.medi.swipe;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.RecyclerItemClickListener;
import local.tomo.medi.disease.DiseasesListActivity;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Patient;
import local.tomo.medi.patient.AddPatientActivity;
import local.tomo.medi.patient.AllPatientsAdapter;


public class PatientFragment extends Fragment {

    private DatabaseHelper databaseHelper;

    List<Patient> patients;

    private RecyclerView recyclerViewAllPatients;
    private FloatingActionButton floatingActionButtonAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_fragment_patient, container, false);

        recyclerViewAllPatients = (RecyclerView) view.findViewById(R.id.recyclerViewAllPatients);
        recyclerViewAllPatients.setLayoutManager(new LinearLayoutManager(getActivity()));
        setPatients();

        floatingActionButtonAdd = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonAdd);
        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPatientActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //http://stackoverflow.com/a/26196831
        recyclerViewAllPatients.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerViewAllPatients,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), DiseasesListActivity.class);
                        intent.putExtra("patientId", patients.get(position).getId());
                        startActivityForResult(intent, 1);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        return view;
    }

    private void setPatients() {
        try {
            patients = getHelper().getPatientDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AllPatientsAdapter allPatientsAdapter = new AllPatientsAdapter(patients, getContext());
        recyclerViewAllPatients.setAdapter(allPatientsAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        setPatients();
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getContext(),DatabaseHelper.class);
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
