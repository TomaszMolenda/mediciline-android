package local.tomo.medi.dosage;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Dosage;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Patient;
import lombok.SneakyThrows;

public class DosagesInDiseaseAdapter extends ArrayAdapter<Dosage> {

    private static final String TAG = "meditomo";

    private DatabaseHelper databaseHelper;

    private DosagesInDiseaseActivity dosagesInDiseaseActivity;

    private Context context;


    public DosagesInDiseaseAdapter(DosagesInDiseaseActivity dosagesInDiseaseActivity, Context context, int textViewResourceId, List<Dosage> dosages) {
        super(context, textViewResourceId, dosages);
        this.dosagesInDiseaseActivity = dosagesInDiseaseActivity;
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_dosages_in_disease, null);
        }

        final Dosage dosage = getItem(position);

        if(dosage!=null) {

            Disease disease = dosage.getMedicament_disease().getDisease();
            Medicament medicament = dosage.getMedicament_disease().getMedicament();

            TextView textViewTime = (TextView) v.findViewById(R.id.textViewTime);
            TextView textViewDose = (TextView) v.findViewById(R.id.textViewDose);
            TextView textViewMedicament = (TextView) v.findViewById(R.id.textViewMedicament);
            TextView textViewDisease = (TextView) v.findViewById(R.id.textViewDisease);
            ImageView imageViewDelete = (ImageView) v.findViewById(R.id.imageViewDelete);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dosage.getTakeTime());
            String hour;
            if(calendar.get(Calendar.HOUR_OF_DAY) < 10)
                hour = "0" + calendar.get(Calendar.HOUR_OF_DAY);
            else
                hour = "" + calendar.get(Calendar.HOUR_OF_DAY);
            String minute;
            if(calendar.get(Calendar.MINUTE) < 10)
                minute = "0" + calendar.get(Calendar.MINUTE);
            else
                minute = "" + calendar.get(Calendar.MINUTE);
            textViewTime.setText(hour + ":" + minute);
            textViewDose.setText("(" + dosage.getDose()+")");
            textViewDisease.setText(disease.getName());
            textViewMedicament.setText(medicament.getName());

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                @SneakyThrows
                public void onClick(View v) {
                    getHelper().getDosageDao().delete(dosage);
                    dosagesInDiseaseActivity.setDosages();
                }
            });
        }
        return  v;
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context,DatabaseHelper.class);
        }
        return databaseHelper;

    }
}
