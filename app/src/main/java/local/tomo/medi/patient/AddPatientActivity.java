package local.tomo.medi.patient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import local.tomo.medi.R;
import local.tomo.medi.network.RestIntefrace;
import local.tomo.medi.network.RetrofitBuilder;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPatientActivity extends Activity {

    private DatabaseHelper databaseHelper = null;

    private EditText editTextName;
    private EditText editTextBirthday;
    private Button buttonSave;

    private DatePickerDialog datePickerDialog;

    private SimpleDateFormat dateFormatter;

    private Calendar chooseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        final Calendar calendar = Calendar.getInstance();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        chooseDate = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                chooseDate.set(year, monthOfYear, dayOfMonth);
                editTextBirthday.setText(dateFormatter.format(chooseDate.getTime()));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        editTextBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                final Patient patient = new Patient(name, chooseDate.getTime());
                try {
                    Dao<Patient, Integer> patientDao = getHelper().getPatientDao();
                    patientDao.create(patient);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                RestIntefrace restIntefrace = RetrofitBuilder.getRestIntefrace();
                Call<Patient> call = restIntefrace.savePatient(patient);
                call.enqueue(new Callback<Patient>() {
                    @Override
                    public void onResponse(Call<Patient> call, Response<Patient> response) {
                        Toast.makeText(getApplicationContext(), "Osobę  " + response.body().getName() + " wysłano na serwer", Toast.LENGTH_SHORT).show();
                        try {
                            getHelper().getPatientDao().update(response.body());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<Patient> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Błąd wysłania leku  " + patient.getName() + "  na serwer", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
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
}
