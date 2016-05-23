package local.tomo.login;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import local.tomo.login.model.Medicament;

/**
 * Created by tomo on 2016-05-03.
 */
public class MedicamentsAdapter extends RecyclerView.Adapter<MedicamentsAdapter.MedicamentsViewHolder> {

    private List<Medicament> medicaments;

    public class MedicamentsViewHolder extends RecyclerView.ViewHolder {
        public TextView name, producent, kind, price, date;

        public MedicamentsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            producent = (TextView) view.findViewById(R.id.producent);
            kind = (TextView) view.findViewById(R.id.kind);
            price = (TextView) view.findViewById(R.id.price);
            date = (TextView) view.findViewById(R.id.date);
        }
    }

    public MedicamentsAdapter(List<Medicament> medicaments) {
        this.medicaments = medicaments;
    }

    @Override
    public MedicamentsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.medicament_list_row, viewGroup, false);
        return new MedicamentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MedicamentsViewHolder medicamentsViewHolder, int i) {
        Medicament medicament = medicaments.get(i);
        medicamentsViewHolder.name.setText(medicament.getName());
        medicamentsViewHolder.producent.setText(medicament.getProducent());
        medicamentsViewHolder.kind.setText(medicament.getKind());
        medicamentsViewHolder.date.setText(medicament.getDateFormatExpiration().toString());
        medicamentsViewHolder.price.setText(Double.toString(medicament.getPrice()));
    }

    @Override
    public int getItemCount() {
        return medicaments.size();
    }
}
