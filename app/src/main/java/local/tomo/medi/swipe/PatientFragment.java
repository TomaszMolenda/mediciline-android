package local.tomo.medi.swipe;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import local.tomo.medi.R;
import local.tomo.medi.RecyclerItemClickListener;
import local.tomo.medi.disease.DiseasesListActivity;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Patient;
import local.tomo.medi.patient.AddPatientActivity;
import local.tomo.medi.patient.AllPatientsAdapter;
import lombok.SneakyThrows;

public class PatientFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    private DatabaseHelper databaseHelper;

    private List<Patient> patients;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_fragment_patient, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setPatients();

        //http://stackoverflow.com/a/26196831
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView,new RecyclerItemClickListener.OnItemClickListener() {
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

    @OnClick(R.id.fabAdd)
    void add() {
        Intent intent = new Intent(getActivity(), AddPatientActivity.class);
        startActivityForResult(intent, 1);
    }

    @SneakyThrows
    private void setPatients() {
        QueryBuilder<Patient, Integer> queryBuilder = getHelper().getPatientDao().queryBuilder();
        patients = queryBuilder.query();
        AllPatientsAdapter allPatientsAdapter = new AllPatientsAdapter(patients, getContext());
        recyclerView.setAdapter(allPatientsAdapter);
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
