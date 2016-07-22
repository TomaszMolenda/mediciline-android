package local.tomo.medi.medicament;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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

public class DiseasesInMedicamentActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private int medicamentId;

    private List<Disease> diseases;

    Medicament medicament;

    private RecyclerView recyclerViewDiseases;
    private TextView textViewNoDiseases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases_in_medicament);

        Bundle bundle = getIntent().getExtras();
        medicamentId = bundle.getInt("medicamentId");
        Medicament medicament = null;
        try {
            medicament = getHelper().getMedicamentDao().queryForId(medicamentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TextView textViewName = (TextView) findViewById(R.id.textViewName);
        TextView textViewProducer = (TextView) findViewById(R.id.textViewProducer);
        TextView textViewPack = (TextView) findViewById(R.id.textViewPack);
        TextView textViewKind = (TextView) findViewById(R.id.textViewKind);
        textViewNoDiseases = (TextView) findViewById(R.id.textViewNoDiseases);

        textViewName.setText(medicament.getName());
        textViewProducer.setText("Producent: " + medicament.getProducent());
        textViewPack.setText("Opakowanie: " + medicament.getPack());
        textViewKind.setText("Rodzaj: " + medicament.getKind());


        recyclerViewDiseases = (RecyclerView) findViewById(R.id.recyclerViewDiseases);
        recyclerViewDiseases.setLayoutManager(new LinearLayoutManager(this));
        setDiseases();

    }

    private void setDiseases() {
        diseases = new ArrayList<>();
        try {
            medicament = getHelper().getMedicamentDao().queryForId(medicamentId);
            ForeignCollection<Medicament_Disease> medicament_diseases = medicament.getMedicament_diseases();
            for (Medicament_Disease medicament_disease : medicament_diseases) {
                diseases.add(medicament_disease.getDisease());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(diseases.isEmpty())
            textViewNoDiseases.setVisibility(View.VISIBLE);
        DiseasesInMedicamentAdapter diseasesInMedicamentAdapter = new DiseasesInMedicamentAdapter(diseases, medicament, this);
        recyclerViewDiseases.setAdapter(diseasesInMedicamentAdapter);
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
