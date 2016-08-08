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


public class AddMedicamentAdapter extends ArrayAdapter<DbMedicament> {

    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewProducer)
    TextView textViewProducer;
    @BindView(R.id.textViewPack)
    TextView textViewPack;
    @BindView(R.id.textViewKind)
    TextView textViewKind;

    public AddMedicamentAdapter(Context context, int resource, ArrayList<DbMedicament> dbMedicaments) {
        super(context, resource, dbMedicaments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.adapter_add_medicament_list_row, null);
            ButterKnife.bind(this, view);
        }

        DbMedicament dbMedicament = getItem(position);

        if(dbMedicament !=null) {
            textViewName.setText(dbMedicament.getProductName());
            textViewProducer.setText(getContext().getString(R.string.producent, dbMedicament.getProducer()));
            textViewPack.setText(getContext().getString(R.string.pack, dbMedicament.getPack()));
            textViewKind.setText(getContext().getString(R.string.kind, dbMedicament.getForm()));
        }
        return  view;
    }
}
