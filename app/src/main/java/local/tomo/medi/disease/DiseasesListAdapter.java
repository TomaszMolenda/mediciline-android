package local.tomo.medi.disease;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.data.Disease;

/**
 * Created by tomo on 2016-07-15.
 */
public class DiseasesListAdapter extends RecyclerView.Adapter<DiseasesListAdapter.DiseaseViewHolder> {

    private List<Disease> diseases;
    private Context context;

    public DiseasesListAdapter(List<Disease> diseases, Context context) {
        this.diseases = diseases;
        this.context = context;
    }

    @Override
    public DiseasesListAdapter.DiseaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_diseases_cardview, null);
        DiseaseViewHolder diseaseViewHolder = new DiseaseViewHolder(view);
        return diseaseViewHolder;
    }

    @Override
    public void onBindViewHolder(DiseasesListAdapter.DiseaseViewHolder holder, int position) {
        Disease disease = diseases.get(position);
        holder.textViewName.setText(disease.getName());
        holder.textViewStartDate.append(Months.createDate(disease.getStartLong()));
        if(disease.getStopLong() == 0)
            holder.textViewStopDate.append("nie zakończona");
        else {
            holder.textViewStopDate.append(Months.createDate(disease.getStopLong()));
            holder.textViewStopDate.setTextColor(Color.GRAY);
            holder.textViewName.setTextColor(Color.GRAY);
            holder.textViewStartDate.setTextColor(Color.GRAY);
        }

    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }

    public static class DiseaseViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewStartDate;
        TextView textViewStopDate;

        public DiseaseViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewStartDate = (TextView) itemView.findViewById(R.id.textViewStartDate);
            textViewStopDate = (TextView) itemView.findViewById(R.id.textViewStopDate);
        }
    }
}
