package local.tomo.medi.disease;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;

public class DiseaseDetailsActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_details);

        Bundle bundle = getIntent().getExtras();
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

        textViewStart.append(Months.createDate(disease.getStartLong()));

        long stopLong = disease.getStopLong();
        if(stopLong != 0) {
            textViewFinish.append(Months.createDate(stopLong));
        } else
            textViewFinish.append("niezakończona");

        textViewDescription.append(disease.getDescription());

        buttonMedicaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MedicamentsListActivity.class);
                intent.putExtra("diseaseId", diseaseId);
                startActivity(intent);
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
