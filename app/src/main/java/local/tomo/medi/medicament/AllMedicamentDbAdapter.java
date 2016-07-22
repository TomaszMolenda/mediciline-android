package local.tomo.medi.medicament;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.DbMedicament;

/**
 * Created by tomo on 2016-05-27.
 */
public class AllMedicamentDbAdapter extends ArrayAdapter<DbMedicament> {

    private ArrayList<DbMedicament> medicaments;

    private MedicamentsDbActivity medicamentsDbActivity;

    private Context mContext;

    private DatabaseHelper databaseHelper = null;




    public AllMedicamentDbAdapter(MedicamentsDbActivity medicamentsDbActivity, Context context, int textViewResourceId, ArrayList<DbMedicament> medicaments) {
        super(context, textViewResourceId, medicaments);
        mContext = context;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_all_medicament_db_list_row, null);
        }

        final DbMedicament dbMedicament = getItem(position);

        if(dbMedicament!=null) {
            TextView textViewName = (TextView) v.findViewById(R.id.textViewName);
            TextView textViewProducer = (TextView) v.findViewById(R.id.textViewProducer);
            TextView textViewKind = (TextView) v.findViewById(R.id.textViewKind);
            TextView textViewPack = (TextView) v.findViewById(R.id.textViewPack);

            textViewName.setText(dbMedicament.getProductName());
            textViewProducer.setText("Producent: " + dbMedicament.getProducer());
            textViewKind.setText("Rodzaj: " + dbMedicament.getForm());
            textViewPack.setText("Opakowanie: " + dbMedicament.getPack());
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
