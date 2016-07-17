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
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;

public class DiseaseDetailsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private Disease disease;

    private TextView textViewName;
    private TextView textViewStart;
    private TextView textViewFinish;
    private TextView textViewDescription;

    private Button buttonAddMedicament;
    private Button buttonMedicaments;


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

        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(disease.getStartLong());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewStart = (TextView) findViewById(R.id.textViewStart);
        textViewFinish = (TextView) findViewById(R.id.textViewFinish);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        buttonAddMedicament = (Button) findViewById(R.id.buttonAddMedicament);
        buttonMedicaments = (Button) findViewById(R.id.buttonMedicaments);

        textViewName.append(disease.getName());

        textViewStart.append(dateFormatter.format(startDate.getTime()));

        long stopLong = disease.getStopLong();
        if(stopLong != 0) {
            Calendar stopDate = Calendar.getInstance();
            stopDate.setTimeInMillis(stopLong);
            textViewFinish.append(dateFormatter.format(stopDate.getTime()));
        } else
            textViewFinish.append("niezakończona");

        textViewDescription.append(disease.getDescription());

        buttonAddMedicament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MedicamentsListActivity.class);
                intent.putExtra("diseaseId", diseaseId);
                intent.putExtra("action", MedicamentsListActivity.ADD);
                startActivity(intent);
            }
        });
        buttonMedicaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MedicamentsListActivity.class);
                intent.putExtra("diseaseId", diseaseId);
                intent.putExtra("action", MedicamentsListActivity.REMOVE);
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
