package local.tomo.medi.disease;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Medicament_Disease;

public class MedicamentsListActivity extends AppCompatActivity {

    private static final String TAG = "meditomo";

    private DatabaseHelper databaseHelper = null;

    private List<Medicament> medicaments;

    private MedicamentsListAdapter medicamentsListAdapter;

    private EditText editTextSearch;
    private ListView listView;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabRemove;


    private Disease disease;
    private int diseaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaments_list);

        Bundle bundle = getIntent().getExtras();
        diseaseId = bundle.getInt("diseaseId");

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        listView = (ListView) findViewById(android.R.id.list);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabRemove = (FloatingActionButton) findViewById(R.id.fabRemove);


        try {
            disease = getHelper().getDiseaseDao().queryForId(diseaseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setMedicaments();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllMedicamentsActivity.class);
                intent.putExtra("diseaseId", diseaseId);
                startActivityForResult(intent, 1);

            }
        });

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



    }





    private void setMedicaments() {
        ForeignCollection<Medicament_Disease> medicament_diseases = disease.getMedicament_diseases();
        medicaments = new ArrayList<Medicament>();
        for (Medicament_Disease medicament_disease : medicament_diseases) {
            medicaments.add(medicament_disease.getMedicament());
        }
        medicamentsListAdapter = new MedicamentsListAdapter(listView, MedicamentsListActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) medicaments);
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
