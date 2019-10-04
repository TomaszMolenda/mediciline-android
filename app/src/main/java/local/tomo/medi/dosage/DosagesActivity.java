package local.tomo.medi.dosage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Dosage;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Medicament_Disease;
import local.tomo.medi.ormlite.data.Patient;
import lombok.SneakyThrows;

public class DosagesActivity extends AppCompatActivity {

    private static final int START_HOUR = 12;
    private static final int START_MINUTE = 0;
    private static final int START_DOSE = 1;


    private DatabaseHelper databaseHelper;

    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textViewPatientName)
    TextView textViewPatientName;
    @BindView(R.id.textViewDisease)
    TextView textViewDisease;
    @BindView(R.id.textViewMedicament)
    TextView textViewMedicament;

    private int diseaseMedicamentId;

    private Medicament medicament;
    private Disease disease;
    private Patient patient;
    private List<Dosage> dosages;

    private DosageAdapter dosageAdapter;


    private int hour = START_HOUR;
    private int minute = START_MINUTE;
    private int dose = START_DOSE;
    private ArrayList<Integer> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosages);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        diseaseMedicamentId = bundle.getInt("diseaseMedicamentId");

        setDosages();

        byte[] photo = disease.getPatient().getPhoto();
        if(photo != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            imageView.setImageBitmap(bitmap);
        }
        textViewPatientName.setText(patient.getName());
        textViewDisease.setText(getString(R.string.diseaseInDosages, disease.getName(), Months.createDate(disease.getStartLong())));
        textViewMedicament.setText(getString(R.string.medicamentInDosages, medicament.getName(), medicament.getPack(), medicament.getKind(), Months.createDate(medicament.getDate())));
    }

    @OnClick(R.id.fabAdd)
    void add() {
        Intent intent = new Intent(getApplicationContext(), AddDosageActivity.class);
        intent.putExtra("diseaseMedicamentId", diseaseMedicamentId);
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        intent.putExtra("dose", dose);
        intent.putIntegerArrayListExtra("times", times);
        startActivityForResult(intent, 1);
    }

    @SneakyThrows
    void setDosages() {
        Medicament_Disease medicament_disease = getHelper().getMedicament_DiseaseDao().queryForId(diseaseMedicamentId);
        medicament = medicament_disease.getMedicament();
        disease = medicament_disease.getDisease();
        patient = disease.getPatient();
        dosages = new ArrayList<>(medicament_disease.getDosages());
        Collections.sort(dosages, new Comparator<Dosage>() {
            @Override
            public int compare(Dosage lhs, Dosage rhs) {
                Date lTakeTime = lhs.getTakeTime();
                Date rTakeTime = rhs.getTakeTime();
                return lTakeTime.compareTo(rTakeTime);
            }
        });
        times = new ArrayList<>();
        for (Dosage dosage : dosages) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dosage.getTakeTime());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            times.add(hour * 100 + minute);
        }

        dosageAdapter = new DosageAdapter(this, getApplicationContext(), R.layout.adapter_dosages, dosages);
        listView.setAdapter(dosageAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            hour = (int) bundle.get("hour");
            minute = (int) bundle.get("minute");
            dose = (int) bundle.get("dose");
        }
        setDosages();
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
