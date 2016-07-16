package local.tomo.medi.disease;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_details);

        Bundle bundle = getIntent().getExtras();
        int diseaseId = bundle.getInt("diseaseId");

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
