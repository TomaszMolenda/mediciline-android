package local.tomo.medi.disease;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.Calendar;

import local.tomo.medi.R;
import local.tomo.medi.dosage.DosagesInDiseaseActivity;
import local.tomo.medi.file.FilesActivity;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Medicament_Disease;
import lombok.SneakyThrows;

public class DiseaseDetailsActivity extends AppCompatActivity {

    private static final String TAG = "meditomo";

    private DatabaseHelper databaseHelper;

    private Disease disease;

    private TextView textViewName;
    private TextView textViewStart;
    private TextView textViewFinish;
    private TextView textViewDescription;

    private Button buttonFiles;
    private Button buttonMedicaments;
    private Button buttonDosages;
    private Button buttonFinish;

    private DatePickerDialog datePickerDialog;
    private Calendar chooseDate;

    private long startLong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_details);

        final Bundle bundle = getIntent().getExtras();
        final int diseaseId = bundle.getInt("diseaseId");



        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewStart = (TextView) findViewById(R.id.textViewStart);
        textViewFinish = (TextView) findViewById(R.id.textViewFinish);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);

        buttonMedicaments = (Button) findViewById(R.id.buttonMedicaments);

        buttonFiles = (Button) findViewById(R.id.buttonFiles);
        buttonDosages = (Button) findViewById(R.id.buttonDosages);
        buttonFinish = (Button) findViewById(R.id.buttonFinish);
        prepareData(diseaseId);
        textViewName.append(disease.getName());

        startLong = disease.getStartLong();
        textViewStart.append(Months.createDate(startLong));



        long stopLong = disease.getStopLong();
        if(stopLong != 0) {
            textViewFinish.append(Months.createDate(stopLong));
            buttonFinish.setOnClickListener(null);
        } else {
            textViewFinish.append("niezakończona");
            buttonFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePickerDialog.setMessage("Data zakończenia choroby");
                    datePickerDialog.show();

                }
            });
        }


        textViewDescription.append(disease.getDescription());

        buttonMedicaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MedicamentsListActivity.class);
                intent.putExtra("diseaseId", diseaseId);
                startActivityForResult(intent, diseaseId);
            }
        });
        buttonFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FilesActivity.class);
                intent.putExtra("diseaseId", diseaseId);
                startActivityForResult(intent, diseaseId);
            }
        });
        buttonDosages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DosagesInDiseaseActivity.class);
                intent.putExtra("diseaseId", diseaseId);
                startActivityForResult(intent, diseaseId);
            }
        });
        final Calendar calendar = Calendar.getInstance();
        chooseDate = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                chooseDate.set(year, monthOfYear, dayOfMonth);
                long stopLong = chooseDate.getTimeInMillis();
                if(stopLong < startLong)
                    Toast.makeText(getApplicationContext(), "Data zakończenia nie może być wcześniejsza niż rozpoczęcia", Toast.LENGTH_LONG).show();
                else {
                    disease.setArchive(true);
                    disease.setStopLong(stopLong);
                    try {
                        getHelper().getDiseaseDao().update(disease);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    textViewFinish.setText("Zakończenie: " + Months.createDate(stopLong));
                    buttonFinish.setOnClickListener(null);
                }


            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));




    }

    @SneakyThrows
    private void prepareData(int diseaseId) {
        disease = getHelper().getDiseaseDao().queryForId(diseaseId);
        ForeignCollection<Medicament_Disease> medicament_diseases = disease.getMedicament_diseases();
        int medicamentsCount = medicament_diseases.size();
        int filesCount = disease.getFiles().size();
        int dosagesCount = 0;
        for (Medicament_Disease medicament_disease : medicament_diseases) {
            dosagesCount += medicament_disease.getDosages().size();
        }

        buttonMedicaments.setText("Leki (" + medicamentsCount + ")");
        buttonFiles.setText("Pliki (" + filesCount + ")");
        buttonDosages.setText("Dawki (" + dosagesCount + ")");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        prepareData(requestCode);
    }
}
