package local.tomo.medi.disease;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.DeleteBuilder;
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
    public static final int ADD = 1;
    public static final int REMOVE = 2;

    private DatabaseHelper databaseHelper = null;

    private List<Medicament> medicaments;

    private MedicamentsListAdapter medicamentsListAdapter;

    private EditText editTextSearch;
    private ListView listView;
    private Button buttonAdd;

    private Disease disease;
    private int diseaseId;

    private int action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaments_list);

        Bundle bundle = getIntent().getExtras();
        diseaseId = bundle.getInt("diseaseId");
        action = bundle.getInt("action");

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        listView = (ListView) findViewById(android.R.id.list);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        try {
            disease = getHelper().getDiseaseDao().queryForId(diseaseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = editTextSearch.getText().toString().toLowerCase();
                List<Medicament> searchedMedicaments = new ArrayList<Medicament>();
                for (Medicament medicament : medicaments) {
                    if(medicament.getName().toLowerCase().contains(text) || medicament.getProducent().toLowerCase().contains(text))
                        searchedMedicaments.add(medicament);
                }
                medicamentsListAdapter = new MedicamentsListAdapter(listView, MedicamentsListActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) searchedMedicaments);
                listView.setAdapter(medicamentsListAdapter);
                medicamentsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        switch (action) {
            case ADD:
                prepareOnClickListenerAdd();
                setMedicamentsToAdd();
                break;
            case REMOVE:
                setMedicamentsToRemove();
                prepareOnClickListenerRemove();
                buttonAdd.setText("Usuń");
                break;
        }


    }

    private void prepareOnClickListenerAdd() {
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
    }

    private void prepareOnClickListenerRemove() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Medicament medicament : medicaments) {
                    if (medicament.isChecked()) {
                        try {
                            DeleteBuilder<Medicament_Disease, Integer> deleteBuilder = getHelper().getMedicament_DiseaseDao().deleteBuilder();
                            deleteBuilder.where().eq("disease_id", diseaseId).and().eq("medicament_id", medicament.getId());
                            deleteBuilder.delete();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                finish();
            }
        });
    }


    private void setMedicamentsToRemove() {
        ForeignCollection<Medicament_Disease> medicament_diseases = disease.getMedicament_diseases();

        medicaments = new ArrayList<Medicament>();

        for (Medicament_Disease medicament_disease : medicament_diseases) {
            medicaments.add(medicament_disease.getMedicament());
        }

        medicamentsListAdapter = new MedicamentsListAdapter(listView, MedicamentsListActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) medicaments);
        listView.setAdapter(medicamentsListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public void setMedicamentsToAdd() {
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
