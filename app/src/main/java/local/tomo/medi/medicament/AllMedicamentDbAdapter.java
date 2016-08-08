package local.tomo.medi.medicament;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.DbMedicament;

public class AllMedicamentDbAdapter extends ArrayAdapter<DbMedicament> {

    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewProducer)
    TextView textViewProducer;
    @BindView(R.id.textViewKind)
    TextView textViewKind;
    @BindView(R.id.textViewPack)
    TextView textViewPack;

    public AllMedicamentDbAdapter(Context context, int textViewResourceId, ArrayList<DbMedicament> medicaments) {
        super(context, textViewResourceId, medicaments);
     }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.adapter_all_medicament_db_list_row, null);
            ButterKnife.bind(this, view);
        }

        final DbMedicament dbMedicament = getItem(position);

        if(dbMedicament!=null) {
            TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
            TextView textViewProducer = (TextView) view.findViewById(R.id.textViewProducer);
            TextView textViewKind = (TextView) view.findViewById(R.id.textViewKind);
            TextView textViewPack = (TextView) view.findViewById(R.id.textViewPack);

            textViewName.setText(dbMedicament.getProductName());
            textViewProducer.setText(getContext().getString(R.string.producent, dbMedicament.getProducer()));
            textViewKind.setText(getContext().getString(R.string.kind, dbMedicament.getForm()));
            textViewPack.setText(getContext().getString(R.string.pack, dbMedicament.getPack()));
        }
        return  view;
    }
}
