package local.tomo.medi.disease;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.Calendar;

import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;

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

        try {
            disease = getHelper().getDiseaseDao().queryForId(diseaseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewStart = (TextView) findViewById(R.id.textViewStart);
        textViewFinish = (TextView) findViewById(R.id.textViewFinish);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);

        buttonMedicaments = (Button) findViewById(R.id.buttonMedicaments);
        buttonFiles = (Button) findViewById(R.id.buttonFiles);
        buttonDosages = (Button) findViewById(R.id.buttonDosages);
        buttonFinish = (Button) findViewById(R.id.buttonFinish);

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
                startActivity(intent);
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
