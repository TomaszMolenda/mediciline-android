package local.tomo.medi.disease;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Medicament_Disease;
import lombok.SneakyThrows;

public class AllMedicamentsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @BindView(R.id.editTextSearch)
    EditText editTextSearch;
    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.buttonSave)
    Button buttonSave;

    private ArrayList<Medicament> medicaments;

    private int diseaseId;
    private Disease disease;

    private AllMedicamentsAdapter allMedicamentsAdapter;

    @Override
    @SneakyThrows
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_medicaments);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        diseaseId = bundle.getInt("diseaseId");

        disease = getHelper().getDiseaseDao().queryForId(diseaseId);

        setMedicaments();
    }

    @SneakyThrows
    private void setMedicaments() {
        QueryBuilder<Medicament, Integer> queryBuilder = getHelper().getMedicamentDao().queryBuilder();
        queryBuilder.orderBy("id", false).where().eq("archive", false);
        List<Medicament> list = queryBuilder.query();
        medicaments = new ArrayList<>(list);
        for (Medicament medicament : list) {
            ForeignCollection<Medicament_Disease> medicament_diseases = medicament.getMedicament_diseases();
            for (Medicament_Disease medicament_disease : medicament_diseases) {
                int id = medicament_disease.getDisease().getId();
                if(diseaseId == id)
                    medicaments.remove(medicament);
            }
        }
        allMedicamentsAdapter = new AllMedicamentsAdapter(listView, getApplicationContext(), R.layout.adapter_all_medicaments, medicaments);
        listView.setAdapter(allMedicamentsAdapter);
    }

    @OnClick(R.id.buttonSave)
    @SneakyThrows
    void assignMedicament() {
        for (Medicament medicament : medicaments) {
            if(medicament.isChecked()) {
                Medicament_Disease medicament_disease = new Medicament_Disease(medicament, disease);
                Dao<Medicament_Disease, Integer> medicament_diseaseDao = getHelper().getMedicament_DiseaseDao();
                medicament_diseaseDao.create(medicament_disease);
            }
        }
        finish();
    }

    @OnTextChanged(R.id.editTextSearch)
    void search() {
        String text = editTextSearch.getText().toString().toLowerCase();
        List<Medicament> searchedMedicaments = new ArrayList<>();
        for (Medicament medicament : medicaments) {
            if(medicament.getName().toLowerCase().contains(text) || medicament.getProducent().toLowerCase().contains(text))
                searchedMedicaments.add(medicament);
        }
        allMedicamentsAdapter = new AllMedicamentsAdapter(listView, getApplicationContext(), R.layout.adapter_all_medicaments, (ArrayList<Medicament>) searchedMedicaments);
        listView.setAdapter(allMedicamentsAdapter);
        allMedicamentsAdapter.notifyDataSetChanged();
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
