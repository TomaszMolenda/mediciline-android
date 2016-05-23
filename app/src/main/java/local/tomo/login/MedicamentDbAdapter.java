package local.tomo.login;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import local.tomo.login.model.MedicamentDb;


public class MedicamentDbAdapter extends ArrayAdapter<MedicamentDb> {

    private ArrayList<MedicamentDb> medicamentDbs;

    public MedicamentDbAdapter(Context context, int resource, ArrayList<MedicamentDb> medicamentDbs) {
        super(context, resource, medicamentDbs);
        this.medicamentDbs = medicamentDbs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.add_medicament_list_row, null);
        }

        MedicamentDb medicamentDb = getItem(position);

        if(medicamentDb!=null) {
            TextView rowAddMedicamentName = (TextView) v.findViewById(R.id.rowAddMedicamentName);
            TextView rowAddMedicamentProducer = (TextView) v.findViewById(R.id.rowAddMedicamentProducer);
            rowAddMedicamentName.setText(medicamentDb.getProductName());
            rowAddMedicamentProducer.setText(medicamentDb.getProducer());
        }
        return  v;
    }
}
