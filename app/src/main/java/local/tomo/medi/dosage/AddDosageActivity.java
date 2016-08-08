package local.tomo.medi.dosage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Dosage;
import local.tomo.medi.ormlite.data.Medicament_Disease;
import lombok.SneakyThrows;

public class AddDosageActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private int diseaseMedicamentId;

    private int hour;
    private int minute;
    private int dose;

    @BindView(R.id.textViewHour)
    TextView textViewHour;
    @BindView(R.id.textViewMinute)
    TextView textViewMinute;
    @BindView(R.id.textViewDose)
    TextView textViewDose;
    @BindView(R.id.imageViewHourUp)
    ImageView imageViewHourUp;
    @BindView(R.id.imageViewHourDown)
    ImageView imageViewHourDown;
    @BindView(R.id.imageViewMinuteUp)
    ImageView imageViewMinuteUp;
    @BindView(R.id.imageViewMinuteDown)
    ImageView imageViewMinuteDown;
    @BindView(R.id.imageViewDoseDown)
    ImageView imageViewDoseDown;
    @BindView(R.id.imageViewDoseUp)
    ImageView imageViewDoseUp;
    @BindView(R.id.imageViewSave)
    ImageView imageViewSave;

    private ArrayList<Integer> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dosage);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        diseaseMedicamentId = bundle.getInt("diseaseMedicamentId");
        hour = bundle.getInt("hour");
        minute = bundle.getInt("minute");
        dose = bundle.getInt("dose");
        times = bundle.getIntegerArrayList("times");

        String sMinute;
        if(minute < 10) sMinute = "0" + minute;
        else sMinute = "" + minute;

        textViewHour.setText("" + hour);
        textViewMinute.setText(sMinute);
        textViewDose.setText("" + dose);
    }

    @OnClick(R.id.imageViewHourUp)
    void hourUp() {
        hour++;
        if(hour == 24)
            hour = 0;
        textViewHour.setText(hour+"");
    }

    @OnClick(R.id.imageViewHourDown)
    void hourDown() {
        hour--;
        if(hour == -1)
            hour = 23;
        textViewHour.setText(hour+"");
    }

    @OnClick(R.id.imageViewMinuteUp)
    void minuteUp() {
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

    @OnClick(R.id.imageViewMinuteDown)
    void minuteDown() {
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

    @OnClick(R.id.imageViewDoseUp)
    void dosageUp() {
        dose++;
        textViewDose.setText(dose + "");
    }

    @OnClick(R.id.imageViewDoseDown)
    void dosageDown() {
        dose--;
        if(dose < 1)
            dose = 1;
        textViewDose.setText(dose + "");
    }

    @OnLongClick(R.id.imageViewDoseUp)
    boolean dosageLongUp() {
        dose += 10;
        textViewDose.setText(dose + "");
        return true;
    }

    @OnLongClick(R.id.imageViewDoseDown)
    boolean dosageLongDown() {
        dose -= 10;
        textViewDose.setText(dose + "");
        return true;
    }

    @OnClick(R.id.imageViewSave)
    @SneakyThrows
    void save() {
        Dosage dosage = new Dosage();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        dosage.setTakeTime(calendar.getTime());
        dosage.setDose(dose);
        int time = hour*100 + minute;
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
