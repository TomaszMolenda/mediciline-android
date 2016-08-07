package local.tomo.medi.dosage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Dosage;
import local.tomo.medi.ormlite.data.Medicament_Disease;

public class AddDosageActivity extends AppCompatActivity {

    private static final String TAG = "meditomo";

    private DatabaseHelper databaseHelper;

    private int diseaseMedicamentId;

    private int hour;
    private int minute;
    private int dose;

    private TextView textViewHour;
    private TextView textViewMinute;
    private TextView textViewDose;
    private ArrayList<Integer> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dosage);

        Bundle bundle = getIntent().getExtras();
        diseaseMedicamentId = bundle.getInt("diseaseMedicamentId");
        hour = bundle.getInt("hour");
        minute = bundle.getInt("minute");
        dose = bundle.getInt("dose");
        times = bundle.getIntegerArrayList("times");

        ImageView imageViewHourUp = (ImageView) findViewById(R.id.imageViewHourUp);
        ImageView imageViewHourDown = (ImageView) findViewById(R.id.imageViewHourDown);
        ImageView imageViewMinuteUp = (ImageView) findViewById(R.id.imageViewMinuteUp);
        ImageView imageViewMinuteDown = (ImageView) findViewById(R.id.imageViewMinuteDown);
        ImageView imageViewDoseDown = (ImageView) findViewById(R.id.imageViewDoseDown);
        ImageView imageViewDoseUp = (ImageView) findViewById(R.id.imageViewDoseUp);
        ImageView imageViewSave = (ImageView) findViewById(R.id.imageViewSave);

        textViewHour = (TextView) findViewById(R.id.textViewHour);
        textViewMinute = (TextView) findViewById(R.id.textViewMinute);
        textViewDose = (TextView) findViewById(R.id.textViewDose);
        String sMinute;
        if(minute < 10)
            sMinute = "0" + minute;
        else
            sMinute = "" + minute;
        textViewHour.setText("" + hour);
        textViewMinute.setText(sMinute);
        textViewDose.setText("" + dose);

        imageViewHourUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour++;
                if(hour == 24)
                    hour = 0;
                textViewHour.setText(hour+"");

            }
        });
        imageViewHourDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour--;
                if(hour == -1)
                    hour = 23;
                textViewHour.setText(hour+"");
            }
        });
        imageViewMinuteUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minute += 5;
                if(minute == 60)
                    minute = 0;
                String sMinute;
                if(minute < 10)
                    sMinute = "0" + minute;
                else
                    sMinute = "" + minute;
                textViewMinute.setText(sMinute);
            }
        });

        imageViewMinuteDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minute -= 5;
                if(minute == -5)
                    minute = 55;
                String sMinute;
                if(minute < 10)
                    sMinute = "0" + minute;
                else
                    sMinute = "" + minute;
                textViewMinute.setText(sMinute);
            }
        });

        imageViewDoseDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dose--;
                if(dose < 1)
                    dose = 1;
                textViewDose.setText(dose + "");
            }
        });
        imageViewDoseUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dose++;
                textViewDose.setText(dose + "");
            }
        });
        imageViewDoseUp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dose += 10;
                textViewDose.setText(dose + "");
                return true;
            }
        });

        imageViewDoseDown.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dose -= 10;
                textViewDose.setText(dose + "");
                return true;
            }
        });

        imageViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dosage dosage = new Dosage();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(0);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                dosage.setTakeTime(calendar.getTime());
                dosage.setDose(dose);
                int time = hour*100 + minute;
                try {
                    Medicament_Disease medicament_disease = getHelper().getMedicament_DiseaseDao().queryForId(diseaseMedicamentId);
                    dosage.setPatient(medicament_disease.getDisease().getPatient());
                    dosage.setMedicament_disease(medicament_disease);
                    if(!times.contains(time))
                        getHelper().getDosageDao().create(dosage);
                    Intent intent = getIntent();
                    intent.putExtra("hour", hour);
                    intent.putExtra("minute", minute);
                    intent.putExtra("dose", dose);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });





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
}
