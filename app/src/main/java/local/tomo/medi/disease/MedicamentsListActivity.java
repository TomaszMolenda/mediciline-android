package local.tomo.medi.disease;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Dosage;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Medicament_Disease;
import lombok.SneakyThrows;

public class MedicamentsListActivity extends AppCompatActivity {

    @BindView(R.id.editTextSearch)
    EditText editTextSearch;
    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    @BindView(R.id.fabRemove)
    FloatingActionButton fabRemove;

    private DatabaseHelper databaseHelper;

    private List<Medicament> medicaments;

    private MedicamentsListAdapter medicamentsListAdapter;

    private Disease disease;
    private int diseaseId;

    @Override
    @SneakyThrows
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaments_list);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        diseaseId = bundle.getInt("diseaseId");

        disease = getHelper().getDiseaseDao().queryForId(diseaseId);
        setMedicaments();
    }

    @OnClick(R.id.fabAdd)
    void addMedicament() {
        Intent intent = new Intent(getApplicationContext(), AllMedicamentsActivity.class);
        intent.putExtra("diseaseId", diseaseId);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.fabRemove)
    @SneakyThrows
    void removeMedicament() {
        for (Medicament medicament : medicaments) {
            if(medicament.isChecked()) {
                ForeignCollection<Medicament_Disease> medicament_diseases = disease.getMedicament_diseases();
                for (Medicament_Disease medicament_disease : medicament_diseases) {
                    if(medicament_disease.getMedicament().equals(medicament)) {
                        ForeignCollection<Dosage> dosages = medicament_disease.getDosages();
                        for (Dosage dosage : dosages) {
                            getHelper().getDosageDao().delete(dosage);
                        }
                        getHelper().getMedicament_DiseaseDao().delete(medicament_disease);
                    }
                }
            }
        }
        setMedicaments();
    }

    @OnTextChanged(R.id.editTextSearch)
    void search() {
        String text = editTextSearch.getText().toString().toLowerCase();
        List<Medicament> searchedMedicaments = new ArrayList<Medicament>();
        for (Medicament medicament : medicaments) {
            if(medicament.getName().toLowerCase().contains(text) || medicament.getProducent().toLowerCase().contains(text))
                searchedMedicaments.add(medicament);
        }
        medicamentsListAdapter = new MedicamentsListAdapter(listView, this, getApplicationContext(), R.layout.adapter_add_medicament_to_disease_list_row, (ArrayList<Medicament>) searchedMedicaments);
        listView.setAdapter(medicamentsListAdapter);
        medicamentsListAdapter.notifyDataSetChanged();
    }


    private void setMedicaments() {
        ForeignCollection<Medicament_Disease> medicament_diseases = disease.getMedicament_diseases();
        medicaments = new ArrayList<>();
        for (Medicament_Disease medicament_disease : medicament_diseases) {
            Medicament medicament = medicament_disease.getMedicament();
            medicament.setDiseaseMedicamentId(medicament_disease.getId());
            medicament.setDosageCount(medicament_disease.getDosages().size());
            medicaments.add(medicament);
        }
        medicamentsListAdapter = new MedicamentsListAdapter(listView, this, getApplicationContext(), R.layout.adapter_add_medicament_to_disease_list_row, (ArrayList<Medicament>) medicaments);
        listView.setAdapter(medicamentsListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setMedicaments();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
