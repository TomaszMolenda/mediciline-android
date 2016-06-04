package local.tomo.login;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import local.tomo.login.model.Medicament;
import local.tomo.login.model.MedicamentDb;

/**
 * Created by tomo on 2016-05-27.
 */
public class AllMedicamentAdapter extends ArrayAdapter<Medicament> {

    private ArrayList<Medicament> medicaments;

    private final String[] months = {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec",
            "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"};
    private final List<String> monthsList = Arrays.asList(months);

    public AllMedicamentAdapter(Context context, int textViewResourceId, ArrayList<Medicament> medicaments) {
        super(context, textViewResourceId, medicaments);
        this.medicaments = medicaments;
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


            rowAllMedicamentName.setText(medicament.getName());
            rowAllMedicamentProducer.setText("Producent: " + medicament.getProducent());

            rowAllMedicamentId.setText("id: "+medicament.getId());
            int idServer = medicament.getIdServer();
            if (idServer == 0) rowAllMedicamentNoSynchro.setVisibility(View.VISIBLE);
            rowAllMedicamentIdServer.setText("idServer: "+ idServer);
            rowAllMedicamentProductLineID.setText("ProductLineId: "+medicament.getProductLineID());
            rowAllMedicamentPackageID.setText("PackageId: "+medicament.getPackageID());
            rowAllMedicamentPrice.setText("Cena: " + medicament.getPrice());
            rowAllMedicamentPack.setText("Rodzaj: " + medicament.getKind());

            Date dateFormatExpiration = medicament.getDateFormatExpiration();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormatExpiration);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            rowAllMedicamentDate.setText("Data: " + monthsList.get(month) + " " + year);
        }
        return  v;
    }
}
