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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import local.tomo.medi.R;
import local.tomo.medi.dosage.DosagesInDiseaseActivity;
import local.tomo.medi.file.FilesActivity;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Medicament_Disease;
import lombok.SneakyThrows;

public class DiseaseDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewStart)
    TextView textViewStart;
    @BindView(R.id.textViewFinish)
    TextView textViewFinish;
    @BindView(R.id.textViewDescription)
    TextView textViewDescription;
    @BindView(R.id.buttonFiles)
    Button buttonFiles;
    @BindView(R.id.buttonMedicaments)
    Button buttonMedicaments;
    @BindView(R.id.buttonDosages)
    Button buttonDosages;
    @BindView(R.id.buttonFinish)
    Button buttonFinish;

    private DatabaseHelper databaseHelper;

    private Disease disease;

    private DatePickerDialog datePickerDialog;
    private Calendar chooseDate;

    private long startLong;
    private long stopLong;
    private int diseaseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_details);
        ButterKnife.bind(this);

        final Bundle bundle = getIntent().getExtras();
        diseaseId = bundle.getInt("diseaseId");

        prepareData(diseaseId);
        textViewName.append(disease.getName());
        textViewStart.append(Months.createDate(startLong));
        if(stopLong != 0) textViewFinish.append(Months.createDate(stopLong));
            else textViewFinish.append(getString(R.string.unfinish));
        textViewDescription.append(disease.getDescription());

        final Calendar calendar = Calendar.getInstance();
        chooseDate = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    @SneakyThrows
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        chooseDate.set(year, monthOfYear, dayOfMonth);
        long stopLong = chooseDate.getTimeInMillis();
        if(stopLong < startLong)
            Toast.makeText(getApplicationContext(), getString(R.string.DateStopLessThanDateStart), Toast.LENGTH_LONG).show();
        else {
            disease.setArchive(true);
            disease.setStopLong(stopLong);
            getHelper().getDiseaseDao().update(disease);
            textViewFinish.setText(getString(R.string.Ended, Months.createDate(stopLong)));
            buttonFinish.setOnClickListener(null);
        }
    }

    @OnClick(R.id.buttonFinish)
    void finishDisease() {
        if(stopLong != 0)
            return;
        else {
            datePickerDialog.setMessage(getString(R.string.date_of_finish_disease));
            datePickerDialog.show();
        }
    }

    @OnClick(R.id.buttonMedicaments)
    void showMedicaments() {
        Intent intent = new Intent(getApplicationContext(), MedicamentsListActivity.class);
        intent.putExtra("diseaseId", diseaseId);
        startActivityForResult(intent, diseaseId);
    }

    @OnClick(R.id.buttonFiles)
    void showFiles() {
        Intent intent = new Intent(getApplicationContext(), FilesActivity.class);
        intent.putExtra("diseaseId", diseaseId);
        startActivityForResult(intent, diseaseId);
    }

    @OnClick(R.id.buttonDosages)
    void showDoseges() {
        Intent intent = new Intent(getApplicationContext(), DosagesInDiseaseActivity.class);
        intent.putExtra("diseaseId", diseaseId);
        startActivityForResult(intent, diseaseId);
    }

    @SneakyThrows
    private void prepareData(int diseaseId) {
        disease = getHelper().getDiseaseDao().queryForId(diseaseId);
        startLong = disease.getStartLong();
        stopLong = disease.getStopLong();
        ForeignCollection<Medicament_Disease> medicament_diseases = disease.getMedicament_diseases();

        buttonMedicaments.setText(getString(R.string.MedicamentsCount, medicament_diseases.size()));
        buttonFiles.setText(getString(R.string.FilesCount, disease.getFiles().size()));
        buttonDosages.setText(getString(R.string.DosagesCount, dosagesCountOfDisease(medicament_diseases)));
    }

    private int dosagesCountOfDisease(ForeignCollection<Medicament_Disease> medicament_diseases) {
        int dosagesCount = 0;
        for (Medicament_Disease medicament_disease : medicament_diseases) {
            dosagesCount += medicament_disease.getDosages().size();
        }
        return dosagesCount;
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
