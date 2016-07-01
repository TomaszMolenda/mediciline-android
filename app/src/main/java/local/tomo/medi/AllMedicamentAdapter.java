package local.tomo.medi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import local.tomo.OnMedicamentOverflowSelectedListener;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.data.Medicament;

/**
 * Created by tomo on 2016-05-27.
 */
public class AllMedicamentAdapter extends ArrayAdapter<Medicament> {

    private ArrayList<Medicament> medicaments;

    private final List<String> months = Months.getMonths();

    public AllMedicamentAdapter(Context context, int textViewResourceId, ArrayList<Medicament> medicaments) {
        super(context, textViewResourceId, medicaments);
        this.medicaments = medicaments;
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
            v = vi.inflate(R.layout.all_medicament_list_row, null);
        }

        Medicament medicament = getItem(position);

        if(medicament!=null) {
            TextView rowAllMedicamentName = (TextView) v.findViewById(R.id.rowAllMedicamentName);
            TextView rowAllMedicamentProducer = (TextView) v.findViewById(R.id.rowAllMedicamentProducer);
            TextView rowAllMedicamentId = (TextView) v.findViewById(R.id.rowAllMedicamentId);
            TextView rowAllMedicamentIdServer = (TextView) v.findViewById(R.id.rowAllMedicamentIdServer);
            TextView rowAllMedicamentProductLineID = (TextView) v.findViewById(R.id.rowAllMedicamentProductLineID);
            TextView rowAllMedicamentPackageID = (TextView) v.findViewById(R.id.rowAllMedicamentPackageID);
            TextView rowAllMedicamentPrice = (TextView) v.findViewById(R.id.rowAllMedicamentPrice);
            TextView rowAllMedicamentDate = (TextView) v.findViewById(R.id.rowAllMedicamentDate);
            TextView rowAllMedicamentPack = (TextView) v.findViewById(R.id.rowAllMedicamentPack);

            ImageView rowAllMedicamentNoSynchro = (ImageView) v.findViewById(R.id.rowAllMedicamentNoSynchro);
            View overflow = v.findViewById(R.id.rowAllMedicamentMenu);
            overflow.setOnClickListener(new OnMedicamentOverflowSelectedListener(medicament, getContext()));



            rowAllMedicamentName.setText(medicament.getName());
            rowAllMedicamentProducer.setText("Producent: " + medicament.getProducent());

            rowAllMedicamentId.setText(""+medicament.getId());
            int idServer = medicament.getIdServer();
            if (idServer == 0) rowAllMedicamentNoSynchro.setVisibility(ImageView.VISIBLE);
            else rowAllMedicamentNoSynchro.setVisibility(ImageView.INVISIBLE);
            rowAllMedicamentIdServer.setText("idServer: "+ idServer);
            rowAllMedicamentProductLineID.setText("ProductLineId: "+medicament.getProductLineID());
            rowAllMedicamentPackageID.setText("PackageId: "+medicament.getPackageID());
            rowAllMedicamentPrice.setText("Cena: " + medicament.getPrice());
            rowAllMedicamentPack.setText("Rodzaj: " + medicament.getKind());

            long date = medicament.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            rowAllMedicamentDate.setText("Data: " + months.get(month) + " " + year);
        }
        return  v;
    }


}
