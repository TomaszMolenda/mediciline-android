package local.tomo.medi.disease;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.dosage.DosagesActivity;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.data.Medicament;

/**
 * Created by tomo on 2016-05-27.
 */
public class MedicamentsListAdapter extends ArrayAdapter<Medicament> {

    private static final String TAG = "meditomo";

    private List<Medicament> medicaments;

    private ListView mListView;

    private CheckBox checkBox;

    MedicamentsListActivity medicamentsListActivity;



    public MedicamentsListAdapter(ListView listView, MedicamentsListActivity medicamentsListActivity, Context context, int textViewResourceId, List<Medicament> medicaments) {
        super(context, textViewResourceId, medicaments);
        this.medicaments = medicaments;
        mListView = listView;
        this.medicamentsListActivity = medicamentsListActivity;
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
            TextView textViewInfo1 = (TextView) v.findViewById(R.id.textViewInfo1);
            TextView textViewPrice = (TextView) v.findViewById(R.id.textViewPrice);
            TextView textViewDate = (TextView) v.findViewById(R.id.textViewDate);
            TextView textViewDosageCount = (TextView) v.findViewById(R.id.textViewDosageCount);
            ImageView imageViewDosage = (ImageView) v.findViewById(R.id.imageViewDosage);

            checkBox = (CheckBox) v.findViewById(R.id.checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        medicament.setChecked(true);
                    else
                        medicament.setChecked(false);
                }
            });

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
            
            imageViewDosage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DosagesActivity.class);
                    intent.putExtra("diseaseMedicamentId", medicament.getDiseaseMedicamentId());
                    Log.d(TAG, "onClick: " +  medicament.getDiseaseMedicamentId());
                    medicamentsListActivity.startActivityForResult(intent, 1);
                }
            });

            textViewName.setText(medicament.getName());
            textViewProducer.setText(medicament.getProducent());
            textViewInfo1.setText(medicament.getPack() + " - " + medicament.getKind());
            textViewPrice.setText(medicament.getPrice() + " zł");
            textViewDate.setText(Months.createDate(medicament.getDate()));
            textViewDosageCount.setText("(" + medicament.getDosageCount() + ")");
        }
        return  v;
    }
}
