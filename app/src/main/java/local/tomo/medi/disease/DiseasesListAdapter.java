package local.tomo.medi.disease;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.data.Disease;

public class DiseasesListAdapter extends RecyclerView.Adapter<DiseasesListAdapter.ViewHolder> {

    private List<Disease> diseases;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.textViewStartDate)
        TextView textViewStartDate;
        @BindView(R.id.textViewStopDate)
        TextView textViewStopDate;
        @BindView(R.id.textViewDisease)
        TextView textViewDisease;
        @BindView(R.id.textViewFiles)
        TextView textViewFiles;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public DiseasesListAdapter(List<Disease> diseases, Context context) {
        this.diseases = diseases;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_diseases_cardview, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Disease disease = diseases.get(position);
        holder.textViewName.setText(disease.getName());
        holder.textViewStartDate.append(Months.createDate(disease.getStartLong()));
        holder.textViewDisease.setText(context.getString(R.string.DiseasesCount, disease.getMedicament_diseases().size()));
        holder.textViewFiles.setText(context.getString(R.string.FilesCount, disease.getFiles().size()));

        if(disease.getStopLong() == 0)
            holder.textViewStopDate.append(context.getString(R.string.unfinish));
        else {
            holder.textViewStopDate.append(Months.createDate(disease.getStopLong()));
            holder.textViewStopDate.setTextColor(Color.GRAY);
            holder.textViewName.setTextColor(Color.GRAY);
            holder.textViewStartDate.setTextColor(Color.GRAY);
            holder.textViewDisease.setTextColor(Color.GRAY);
            holder.textViewFiles.setTextColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }


}
