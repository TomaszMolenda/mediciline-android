package local.tomo.medi.disease;


import android.content.Context;
import android.content.Intent;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.dosage.DosagesActivity;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.data.Medicament;

public class MedicamentsListAdapter extends ArrayAdapter<Medicament> {

    private ListView listView;

    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewProducer)
    TextView textViewProducer;
    @BindView(R.id.textViewInfo1)
    TextView textViewInfo1;
    @BindView(R.id.textViewPrice)
    TextView textViewPrice;
    @BindView(R.id.textViewDate)
    TextView textViewDate;
    @BindView(R.id.textViewDosageCount)
    TextView textViewDosageCount;
    @BindView(R.id.imageViewDosage)
    ImageView imageViewDosage;

    MedicamentsListActivity medicamentsListActivity;



    public MedicamentsListAdapter(ListView listView, MedicamentsListActivity medicamentsListActivity, Context context, int textViewResourceId, List<Medicament> medicaments) {
        super(context, textViewResourceId, medicaments);
        this.listView = listView;
        this.medicamentsListActivity = medicamentsListActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.adapter_add_medicament_to_disease_list_row, null);
            ButterKnife.bind(this, view);
        }

        final Medicament medicament = getItem(position);
        if(medicament!=null) {
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) medicament.setChecked(true);
                    else medicament.setChecked(false);
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                    if(checkBox.isChecked()) checkBox.setChecked(false);
                    else checkBox.setChecked(true);
                }
            });
            imageViewDosage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DosagesActivity.class);
                    intent.putExtra("diseaseMedicamentId", medicament.getDiseaseMedicamentId());
                    medicamentsListActivity.startActivityForResult(intent, 1);
                }
            });
            String name = medicament.getName();
            if(name.length() > 30) name = name.substring(0,30);
            textViewName.setText(name);
            String producent = medicament.getProducent();
            if(producent.length() > 15) producent = producent.substring(0,15);
            textViewProducer.setText(producent);
            textViewInfo1.setText(medicament.getPack() + " - " + medicament.getKind());
            textViewPrice.setText(medicament.getPrice() + " " + getContext().getString(R.string.currency));
            textViewDate.setText(Months.createDate(medicament.getDate()));
            textViewDosageCount.setText("(" + medicament.getDosageCount() + ")");
        }
        return  view;
    }
}
