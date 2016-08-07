package local.tomo.medi.dosage;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.disease.MedicamentsListActivity;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Dosage;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Patient;

/**
 * Created by tomo on 2016-05-27.
 */
public class DosageAdapter extends ArrayAdapter<Dosage> {

    private static final String TAG = "meditomo";

    private DatabaseHelper databaseHelper;

    private DosagesActivity dosagesActivity;

    private Context context;


    public DosageAdapter(DosagesActivity dosagesActivity, Context context, int textViewResourceId, List<Dosage> dosages) {
        super(context, textViewResourceId, dosages);
        this.dosagesActivity = dosagesActivity;
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_dosages, null);
        }

        final Dosage dosage = getItem(position);

        if(dosage!=null) {
            TextView textViewTime = (TextView) v.findViewById(R.id.textViewTime);
            TextView textViewDose = (TextView) v.findViewById(R.id.textViewDose);
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

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        getHelper().getDosageDao().delete(dosage);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dosagesActivity.setDosages();
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
