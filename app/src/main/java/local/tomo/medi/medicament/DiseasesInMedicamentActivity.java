package local.tomo.medi.medicament;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Medicament_Disease;
import lombok.SneakyThrows;

public class DiseasesInMedicamentActivity extends AppCompatActivity {

    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewProducer)
    TextView textViewProducer;
    @BindView(R.id.textViewPack)
    TextView textViewPack;
    @BindView(R.id.textViewKind)
    TextView textViewKind;
    @BindView(R.id.recyclerViewDiseases)
    RecyclerView recyclerViewDiseases;
    @BindView(R.id.textViewNoDiseases)
    TextView textViewNoDiseases;

    private DatabaseHelper databaseHelper;

    @Override
    @SneakyThrows
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases_in_medicament);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        int medicamentId = bundle.getInt("medicamentId");
        Medicament medicament = getHelper().getMedicamentDao().queryForId(medicamentId);

        textViewName.setText(medicament.getName());
        textViewProducer.setText(getString(R.string.producent, medicament.getProducent()));
        textViewPack.setText(getString(R.string.pack, medicament.getPack()));
        textViewKind.setText(getString(R.string.kind, medicament.getKind()));

        recyclerViewDiseases.setLayoutManager(new LinearLayoutManager(this));
        setDiseases(medicamentId);
    }

    @SneakyThrows
    private void setDiseases(int medicamentId) {
        Medicament medicament = getHelper().getMedicamentDao().queryForId(medicamentId);
        ForeignCollection<Medicament_Disease> medicament_diseases = medicament.getMedicament_diseases();
        List<Disease> diseases = new ArrayList<>();
        for (Medicament_Disease medicament_disease : medicament_diseases) {
            diseases.add(medicament_disease.getDisease());
        }
        if(diseases.isEmpty())
            textViewNoDiseases.setVisibility(View.VISIBLE);
        DiseasesInMedicamentAdapter diseasesInMedicamentAdapter = new DiseasesInMedicamentAdapter(diseases, this);
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
