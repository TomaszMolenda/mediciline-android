package local.tomo.medi.swipe;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Patient;
import local.tomo.medi.patient.AddPatientActivity;
import local.tomo.medi.patient.AllPatientsAdapter;


public class PatientFragment extends Fragment {

    private DatabaseHelper databaseHelper = null;

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_fragment_patient, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvAllPatients);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setPatients();

        fabAdd = (FloatingActionButton) view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPatientActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        return view;
    }

    private void setPatients() {
        List<Patient> patients = null;

        try {
            patients = getHelper().getPatientDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AllPatientsAdapter allPatientsAdapter = new AllPatientsAdapter(patients, getContext());
        recyclerView.setAdapter(allPatientsAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        setPatients();
        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.pager);
        FragmentStatePagerAdapter a = (FragmentStatePagerAdapter) pager.getAdapter();
        DiseaseFragment diseaseFragment = (DiseaseFragment) a.instantiateItem(pager, 1);
        diseaseFragment.refreshSpinner();
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
