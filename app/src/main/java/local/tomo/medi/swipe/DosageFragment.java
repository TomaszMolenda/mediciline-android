package local.tomo.medi.swipe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.dosage.AllDosageAdapter;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Dosage;
import local.tomo.medi.ormlite.data.Medicament_Disease;
import lombok.SneakyThrows;

public class DosageFragment extends Fragment {

    private DatabaseHelper databaseHelper;

    @BindView(R.id.list)
    ListView listView;

    private List<Dosage> dosages;
    private AllDosageAdapter allDosageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_fragment_dosage, container, false);
        ButterKnife.bind(this, view);
        setDosages();
        return view;
    }

    @SneakyThrows
    public void setDosages() {
        QueryBuilder<Dosage, Integer> dosageBuilder = getHelper().getDosageDao().queryBuilder();
        QueryBuilder<Medicament_Disease, Integer> medicament_diseaseBuilder = getHelper().getMedicament_DiseaseDao().queryBuilder();
        QueryBuilder<Disease, Integer> diseaseBuilder = getHelper().getDiseaseDao().queryBuilder();

        diseaseBuilder.where().eq("archive", false);
        dosageBuilder.join(medicament_diseaseBuilder);
        medicament_diseaseBuilder.join(diseaseBuilder);

        dosages = dosageBuilder.query();

        Collections.sort(dosages, new Comparator<Dosage>() {
            @Override
            public int compare(Dosage lhs, Dosage rhs) {
                Date lTakeTime = lhs.getTakeTime();
                Date rTakeTime = rhs.getTakeTime();
                return lTakeTime.compareTo(rTakeTime);
            }
        });
        allDosageAdapter = new AllDosageAdapter(this, getContext(), R.layout.adapter_all_dosages, dosages);
        listView.setAdapter(allDosageAdapter);
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getContext(),DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
