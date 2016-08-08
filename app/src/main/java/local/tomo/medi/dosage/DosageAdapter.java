package local.tomo.medi.dosage;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Dosage;
import lombok.SneakyThrows;

public class DosageAdapter extends ArrayAdapter<Dosage> {

    private DatabaseHelper databaseHelper;

    @BindView(R.id.textViewTime)
    TextView textViewTime;
    @BindView(R.id.textViewDose)
    TextView textViewDose;
    @BindView(R.id.imageViewDelete)
    ImageView imageViewDelete;

    private DosagesActivity dosagesActivity;

    private Context context;


    public DosageAdapter(DosagesActivity dosagesActivity, Context context, int textViewResourceId, List<Dosage> dosages) {
        super(context, textViewResourceId, dosages);
        this.dosagesActivity = dosagesActivity;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.adapter_dosages, null);
            ButterKnife.bind(this, view);
        }

        final Dosage dosage = getItem(position);

        if(dosage!=null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dosage.getTakeTime());
            String hour;
            if(calendar.get(Calendar.HOUR_OF_DAY) < 10) hour = "0" + calendar.get(Calendar.HOUR_OF_DAY);
            else hour = "" + calendar.get(Calendar.HOUR_OF_DAY);
            String minute;
            if(calendar.get(Calendar.MINUTE) < 10) minute = "0" + calendar.get(Calendar.MINUTE);
            else minute = "" + calendar.get(Calendar.MINUTE);
            textViewTime.setText(hour + ":" + minute);
            textViewDose.setText("(" + dosage.getDose()+")");

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                @SneakyThrows
                public void onClick(View v) {
                    getHelper().getDosageDao().delete(dosage);
                    dosagesActivity.setDosages();
                }
            });
        }
        return  view;
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context,DatabaseHelper.class);
        }
        return databaseHelper;

    }
}
