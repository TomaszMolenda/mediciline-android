package local.tomo.medi.dosage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Dosage;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Patient;
import local.tomo.medi.swipe.DosageFragment;
import lombok.SneakyThrows;

public class AllDosageAdapter extends ArrayAdapter<Dosage> {

    private DatabaseHelper databaseHelper;

    @BindView(R.id.textViewTime)
    TextView textViewTime;
    @BindView(R.id.textViewDose)
    TextView textViewDose;
    @BindView(R.id.textViewMedicament)
    TextView textViewMedicament;
    @BindView(R.id.textViewDisease)
    TextView textViewDisease;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageViewDelete)
    ImageView imageViewDelete;

    private DosageFragment dosageFragment;
    private Context context;


    public AllDosageAdapter(DosageFragment dosageFragment, Context context, int textViewResourceId, List<Dosage> dosages) {
        super(context, textViewResourceId, dosages);
        this.dosageFragment = dosageFragment;
        this.context = context;
    }



    @Override
    @SneakyThrows
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.adapter_all_dosages, null);
            ButterKnife.bind(this, view);
        }

        final Dosage dosage = getItem(position);

        if(dosage!=null) {

            Disease disease = dosage.getMedicament_disease().getDisease();
            Medicament medicament = dosage.getMedicament_disease().getMedicament();
            Patient patient = dosage.getPatient();
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
            byte[] photo = patient.getPhoto();
            if(photo != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                imageView.setImageBitmap(bitmap);
            }
            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                @SneakyThrows
                public void onClick(View v) {
                    getHelper().getDosageDao().delete(dosage);
                    dosageFragment.setDosages();
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
