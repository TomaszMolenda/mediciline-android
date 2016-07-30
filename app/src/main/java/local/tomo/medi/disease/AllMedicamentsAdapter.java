package local.tomo.medi.disease;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.data.Medicament;

/**
 * Created by tomo on 2016-05-27.
 */
public class AllMedicamentsAdapter extends ArrayAdapter<Medicament> {

    private static final String TAG = "meditomo";

    private ArrayList<Medicament> medicaments;

    private ListView mListView;


    public AllMedicamentsAdapter(ListView listView, Context context, int textViewResourceId, ArrayList<Medicament> medicaments) {
        super(context, textViewResourceId, medicaments);
        this.medicaments = medicaments;
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
            v = vi.inflate(R.layout.adapter_all_medicaments, null);
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
            textViewProducer.setText(medicament.getProducent());
            textViewPack.setText(medicament.getPack());
            textViewKind.setText(medicament.getKind());
            textViewPrice.setText(medicament.getPrice()+ " zł");
            textViewDate.setText(Months.createDate(medicament.getDate()));

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                    if(checkBox.isChecked())
                        checkBox.setChecked(false);
                    else
                        checkBox.setChecked(true);
                }
            });
        }
        return  v;
    }
}
