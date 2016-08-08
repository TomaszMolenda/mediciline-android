package local.tomo.medi.dosage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Dosage;
import local.tomo.medi.ormlite.data.Medicament_Disease;
import local.tomo.medi.ormlite.data.Patient;
import lombok.SneakyThrows;

public class DosagesInDiseaseActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textViewPatientName)
    TextView textViewPatientName;
    @BindView(R.id.textViewDisease)
    TextView textViewDisease;
    @BindView(R.id.list)
    ListView listView;

    private int diseaseId;
    private Disease disease;
    private Patient patient;
    private ArrayList<Dosage> dosages;

    private DosagesInDiseaseAdapter dosagesInDiseaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosages_in_disease);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        diseaseId = bundle.getInt("diseaseId");

        setDosages();

        byte[] photo = disease.getPatient().getPhoto();
        if(photo != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            imageView.setImageBitmap(bitmap);
        }

        textViewPatientName.setText(patient.getName());
        textViewDisease.setText(getString(R.string.diseaseInDosages, disease.getName(), Months.createDate(disease.getStartLong())));
    }

    @SneakyThrows
    void setDosages() {
        disease = getHelper().getDiseaseDao().queryForId(diseaseId);
        patient = disease.getPatient();
        ForeignCollection<Medicament_Disease> medicament_diseases = disease.getMedicament_diseases();
        dosages = new ArrayList<>();
        for (Medicament_Disease medicament_disease : medicament_diseases) {
            dosages.addAll(medicament_disease.getDosages());
        }
        Collections.sort(dosages, new Comparator<Dosage>() {
            @Override
            public int compare(Dosage lhs, Dosage rhs) {
                Date lTakeTime = lhs.getTakeTime();
                Date rTakeTime = rhs.getTakeTime();
                return lTakeTime.compareTo(rTakeTime);
            }
        });
        dosagesInDiseaseAdapter = new DosagesInDiseaseAdapter(this, getApplicationContext(), R.layout.adapter_dosages_in_disease, dosages);
        listView.setAdapter(dosagesInDiseaseAdapter);
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
