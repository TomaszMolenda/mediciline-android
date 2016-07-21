package local.tomo.medi.medicament;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.DbMedicament;


public class AddMedicamentAdapter extends ArrayAdapter<DbMedicament> {

    private ArrayList<DbMedicament> dbMedicaments;

    public AddMedicamentAdapter(Context context, int resource, ArrayList<DbMedicament> dbMedicaments) {
        super(context, resource, dbMedicaments);
        this.dbMedicaments = dbMedicaments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_add_medicament_list_row, null);
        }

        DbMedicament dbMedicament = getItem(position);

        if(dbMedicament !=null) {
            TextView rowAddMedicamentName = (TextView) v.findViewById(R.id.rowAddMedicamentName);
            TextView rowAddMedicamentProducer = (TextView) v.findViewById(R.id.rowAddMedicamentProducer);
            TextView rowAddMedicamentPack = (TextView) v.findViewById(R.id.rowAddMedicamentPack);
            TextView rowAddMedicamentKind = (TextView) v.findViewById(R.id.rowAddMedicamentKind);
            rowAddMedicamentName.setText(dbMedicament.getProductName());
            rowAddMedicamentProducer.setText("Producent: " + dbMedicament.getProducer());
            rowAddMedicamentPack.setText("Opakowanie: " + dbMedicament.getPack());
            rowAddMedicamentKind.setText("Rodzaj: " + dbMedicament.getForm());
        }
        return  v;
    }
}
