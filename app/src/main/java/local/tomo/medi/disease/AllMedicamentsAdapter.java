package local.tomo.medi.disease;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.data.Medicament;

import static android.widget.CompoundButton.OnCheckedChangeListener;

public class AllMedicamentsAdapter extends ArrayAdapter<Medicament> {

    private static final String TAG = "meditomo";

    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewProducer)
    TextView textViewProducer;
    @BindView(R.id.textViewPack)
    TextView textViewPack;
    @BindView(R.id.textViewKind)
    TextView textViewKind;
    @BindView(R.id.textViewPrice)
    TextView textViewPrice;
    @BindView(R.id.textViewDate)
    TextView textViewDate;
    @BindView(R.id.checkBox)
    CheckBox checkBox;

    private ListView listView;

    public AllMedicamentsAdapter(ListView listView, Context context, int textViewResourceId, ArrayList<Medicament> medicaments) {
        super(context, textViewResourceId, medicaments);
        this.listView = listView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.adapter_all_medicaments, null);
            ButterKnife.bind(this, view);
        }


        final Medicament medicament = getItem(position);
        if(medicament!=null) {
            checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        medicament.setChecked(true);
                    else
                        medicament.setChecked(false);
                }
            });
            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                    if(checkBox.isChecked())
                        checkBox.setChecked(false);
                    else
                        checkBox.setChecked(true);
                }
            });
            String name = medicament.getName();
            if(name.length() > 30)
                name = name.substring(0,30);
            textViewName.setText(name);
            String producent = medicament.getProducent();
            if(producent.length() > 15)
                producent = producent.substring(0,15);
            textViewProducer.setText(producent);
            textViewPack.setText(medicament.getPack());
            textViewKind.setText(medicament.getKind());
            textViewPrice.setText(medicament.getPrice()+ " " + getContext().getResources().getString(R.string.currency));
            textViewDate.setText(Months.createDate(medicament.getDate()));
        }
        return  view;
    }
}
