package local.tomo.medi.dosage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.disease.AllMedicamentsActivity;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Dosage;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Medicament_Disease;
import local.tomo.medi.ormlite.data.Patient;

public class DosagesActivity extends AppCompatActivity {

    private static final String TAG = "meditomo";

    private DatabaseHelper databaseHelper;

    private int diseaseMedicamentId;

    private Medicament medicament;
    private Disease disease;
    private Patient patient;
    private List<Dosage> dosages;

    private DosageAdapter dosageAdapter;

    private ListView listView;

    private FloatingActionButton fabAdd;
    private int hour = 12;
    private int minute = 0;
    private int dose = 1;
    private ArrayList<Integer> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosages);

        Bundle bundle = getIntent().getExtras();
        diseaseMedicamentId = bundle.getInt("diseaseMedicamentId");

        listView = (ListView) findViewById(android.R.id.list);

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);

        setDosages();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddDosageActivity.class);
                intent.putExtra("diseaseMedicamentId", diseaseMedicamentId);
                intent.putExtra("hour", hour);
                intent.putExtra("minute", minute);
                intent.putExtra("dose", dose);
                intent.putIntegerArrayListExtra("times", times);
                startActivityForResult(intent, 1);
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView textViewPatientName = (TextView) findViewById(R.id.textViewPatientName);
        TextView textViewDisease = (TextView) findViewById(R.id.textViewDisease);
        TextView textViewMedicament = (TextView) findViewById(R.id.textViewMedicament);

        byte[] photo = disease.getPatient().getPhoto();
        if(photo != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            imageView.setImageBitmap(bitmap);
        }

        textViewPatientName.setText(patient.getName());
        textViewDisease.setText(disease.getName() + " (rozp. " + Months.createDate(disease.getStartLong()) + ")");
        textViewMedicament.setText(medicament.getName() + " - " + medicament.getPack() + " " + medicament.getKind() + " (ważn. " + Months.createDate(medicament.getDate()) + ")");

    }

    void setDosages() {
        try {
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
                times.add(hour*100 + minute);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dosageAdapter = new DosageAdapter(listView, this, getApplicationContext(), R.layout.adapter_dosages, dosages);
        listView.setAdapter(dosageAdapter);
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
}
