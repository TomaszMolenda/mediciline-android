package local.tomo.medi.medicament;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.DbMedicament;
import local.tomo.medi.ormlite.data.Medicament;

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
        this.medicaments = medicaments;
        this.medicamentsDbActivity = medicamentsDbActivity;
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
            TextView rowAllMedicamentDbName = (TextView) v.findViewById(R.id.rowAllMedicamentDbName);
            TextView rowAllMedicamentDbProducer = (TextView) v.findViewById(R.id.rowAllMedicamentDbProducer);
            TextView rowAllMedicamentDbId = (TextView) v.findViewById(R.id.rowAllMedicamentDbId);
            TextView rowAllMedicamentDbProductLineID = (TextView) v.findViewById(R.id.rowAllMedicamentDbProductLineID);
            TextView rowAllMedicamentDbPackageID = (TextView) v.findViewById(R.id.rowAllMedicamentDbPackageID);
            TextView rowAllMedicamentDbPrice = (TextView) v.findViewById(R.id.rowAllMedicamentDbPrice);
            TextView rowAllMedicamentDbPack = (TextView) v.findViewById(R.id.rowAllMedicamentDbPack);

            rowAllMedicamentDbName.setText(dbMedicament.getProductName());
            rowAllMedicamentDbProducer.setText("Producent: " + dbMedicament.getProducer());

            rowAllMedicamentDbId.setText(""+dbMedicament.getPackageID());
            rowAllMedicamentDbProductLineID.setText("ProductLineId: "+dbMedicament.getMedicamentAdditional().getProductLineID());
            rowAllMedicamentDbPackageID.setText("PackageId: "+dbMedicament.getPackageID());
            rowAllMedicamentDbPrice.setText("Cena: " + dbMedicament.getPrice());
            rowAllMedicamentDbPack.setText("Rodzaj: " + dbMedicament.getPack());
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
