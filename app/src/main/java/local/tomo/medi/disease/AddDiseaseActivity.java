package local.tomo.medi.disease;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Patient;

public class AddDiseaseActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper = null;

    private EditText editTextName;
    private EditText editTextStartDate;
    private EditText editTextDescription;
    private Button buttonSave;

    private DatePickerDialog datePickerDialog;

    private Calendar chooseDate;

    private Patient patient;
    private Disease disease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disease);

        Bundle bundle = getIntent().getExtras();
        int patientId = bundle.getInt("patientId");
        try {
            Dao<Patient, Integer> patientDao = getHelper().getPatientDao();
            patient = patientDao.queryForId(patientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final Calendar calendar = Calendar.getInstance();

        chooseDate = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                chooseDate.set(year, monthOfYear, dayOfMonth);
                editTextStartDate.setText(Months.createDate(chooseDate.getTimeInMillis()));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextStartDate = (EditText) findViewById(R.id.editTextStartDate);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidate()) {
                    String name = editTextName.getText().toString().trim();
                    String description = editTextDescription.getText().toString();
                    disease = new Disease(name, chooseDate.getTime(), description);
                    disease.setPatient(patient);
                    try {
                        getHelper().getDiseaseDao().create(disease);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    //sendPatientToServer(patient);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }



            }
        });




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

    public boolean isValidate() {
        if(editTextName.getText().toString().trim().length() < 4) {
            Toast.makeText(this, "Nazwa musi mieć min 4 znaki", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(editTextStartDate.getText().length() == 0) {
            Toast.makeText(this, "Podaj datę", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
