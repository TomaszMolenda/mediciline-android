package local.tomo.medi.disease;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.medicament.MedicamentsActivity;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;

/**
 * Created by tomo on 2016-05-27.
 */
public class MedicamentsListAdapter extends ArrayAdapter<Medicament> {

    private static final String TAG = "meditomo";

    private ArrayList<Medicament> medicaments;

    private final List<String> months = Months.getMonths();

    private MedicamentsListActivity medicamentsListActivity;

    private Context mContext;

    private DatabaseHelper databaseHelper = null;

    private ListView mListView;



    public MedicamentsListAdapter(ListView listView, MedicamentsListActivity medicamentsListActivity, Context context, int textViewResourceId, ArrayList<Medicament> medicaments) {
        super(context, textViewResourceId, medicaments);
        this.medicaments = medicaments;
        this.medicamentsListActivity = medicamentsListActivity;
        mContext = context;
        mListView = listView;

    }



    public List<Medicament> getMedicaments() {
        return medicaments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_add_medicament_to_disease_list_row, null);
        }

        final Medicament medicament = getItem(position);

        if(medicament!=null) {
            TextView textViewName = (TextView) v.findViewById(R.id.textViewName);
            TextView textViewProducer = (TextView) v.findViewById(R.id.textViewProducer);
            TextView textViewPack = (TextView) v.findViewById(R.id.textViewPack);
            TextView textViewKind = (TextView) v.findViewById(R.id.textViewKind);
            TextView textViewPrice = (TextView) v.findViewById(R.id.textViewPrice);
            TextView textViewDate = (TextView) v.findViewById(R.id.textViewDate);

            CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        medicament.setChecked(true);
                    else
                        medicament.setChecked(false);
                }
            });



            textViewName.setText(medicament.getName());
            textViewProducer.setText("Producent: " + medicament.getProducent());
            textViewPack.setText("Opakowanie: " + medicament.getPack());
            textViewKind.setText("Rodzaj: " + medicament.getKind());
            textViewPrice.setText("Cena: " + medicament.getPrice());

            long date = medicament.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            textViewDate.setText("Data: " + months.get(month) + " " + year);
        }
        return  v;
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(mContext,DatabaseHelper.class);
        }
        return databaseHelper;

    }
}
