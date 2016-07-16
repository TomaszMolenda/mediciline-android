package local.tomo.medi.disease;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Patient;

/**
 * Created by tomo on 2016-07-15.
 */
public class DiseasesAdapter extends RecyclerView.Adapter<DiseasesAdapter.DiseaseViewHolder> {

    private List<Disease> diseases;
    private Context context;

    public DiseasesAdapter(List<Disease> diseases, Context context) {
        this.diseases = diseases;
        this.context = context;
    }

    @Override
    public DiseasesAdapter.DiseaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_diseases_cardview, null);
        DiseaseViewHolder diseaseViewHolder = new DiseaseViewHolder(view);
        return diseaseViewHolder;
    }

    @Override
    public void onBindViewHolder(DiseasesAdapter.DiseaseViewHolder holder, int position) {
        Disease disease = diseases.get(position);
        holder.tvDiseaseName.setText(disease.getName());
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }

    public static class DiseaseViewHolder extends RecyclerView.ViewHolder {

        TextView tvDiseaseName;

        public DiseaseViewHolder(View itemView) {
            super(itemView);
            tvDiseaseName = (TextView) itemView.findViewById(R.id.tvDiseaseName);
        }
    }
}
