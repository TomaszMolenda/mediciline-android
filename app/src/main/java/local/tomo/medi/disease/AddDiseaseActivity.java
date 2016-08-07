package local.tomo.medi.disease;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Patient;
import lombok.SneakyThrows;

public class AddDiseaseActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @BindView(R.id.editTextName)
    TextView editTextName;
    @BindView(R.id.editTextStartDate)
    TextView editTextStartDate;
    @BindView(R.id.editTextDescription)
    TextView editTextDescription;
    @BindView(R.id.buttonSave)
    TextView buttonSave;

    private DatePickerDialog datePickerDialog;

    private Calendar chooseDate;

    private Patient patient;

    @Override
    @SneakyThrows
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disease);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        int patientId = bundle.getInt("patientId");

        Dao<Patient, Integer> patientDao = getHelper().getPatientDao();
        patient = patientDao.queryForId(patientId);

        final Calendar calendar = Calendar.getInstance();

        chooseDate = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                chooseDate.set(year, monthOfYear, dayOfMonth);
                editTextStartDate.setText(Months.createDate(chooseDate.getTimeInMillis()));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @OnClick(R.id.buttonSave)
    @SneakyThrows
    void saveDisease() {
        if(!isValidate()) return;
        String name = editTextName.getText().toString().trim();
        String description = editTextDescription.getText().toString();
        Disease disease = new Disease(name, chooseDate.getTime(), description);
        disease.setPatient(patient);
        getHelper().getDiseaseDao().create(disease);
        //sendPatientToServer(patient);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

    }

    @OnClick(R.id.editTextStartDate)
    void showDatePickerDialog() {
        datePickerDialog.show();
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
