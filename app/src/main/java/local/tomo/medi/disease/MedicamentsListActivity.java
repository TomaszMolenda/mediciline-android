package local.tomo.medi.disease;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.medicament.AllMedicamentAdapter;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Medicament_Disease;

public class MedicamentsListActivity extends AppCompatActivity {

    private static final String TAG = "meditomo";

    private DatabaseHelper databaseHelper = null;

    private List<Medicament> medicaments;

    private MedicamentsListAdapter medicamentsListAdapter;

    private ListView listView;
    private Button buttonAdd;

    private Disease disease;
    private int diseaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaments_list);

        Bundle bundle = getIntent().getExtras();
        diseaseId = bundle.getInt("diseaseId");

        listView = (ListView) findViewById(android.R.id.list);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        try {
            disease = getHelper().getDiseaseDao().queryForId(diseaseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Medicament medicament : medicaments) {
                    if (medicament.isChecked()) {
                        Medicament_Disease medicament_disease = new Medicament_Disease(medicament, disease);
                        try {
                            Dao<Medicament_Disease, Integer> medicament_diseaseDao = getHelper().getMedicament_DiseaseDao();
                            medicament_diseaseDao.create(medicament_disease);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                finish();
            }
        });


        setActiveMedicaments();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public void setActiveMedicaments() {
        medicaments = new ArrayList<Medicament>();
        try {
            QueryBuilder<Medicament, Integer> queryBuilderMedicament = getHelper().getMedicamentDao().queryBuilder();
            queryBuilderMedicament.orderBy("id", false).where().eq("archive", false);
            List<Medicament> list = queryBuilderMedicament.query();
            medicaments = new ArrayList<Medicament>(list);
            for (Medicament medicament : list) {
                ForeignCollection<Medicament_Disease> medicament_diseases = medicament.getMedicament_diseases();
                for (Medicament_Disease medicament_disease : medicament_diseases) {
                    int id = medicament_disease.getDisease().getId();
                    if (diseaseId == id)
                        medicaments.remove(medicament);

                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        medicamentsListAdapter = new MedicamentsListAdapter(listView, MedicamentsListActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) medicaments);
        listView.setAdapter(medicamentsListAdapter);
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
