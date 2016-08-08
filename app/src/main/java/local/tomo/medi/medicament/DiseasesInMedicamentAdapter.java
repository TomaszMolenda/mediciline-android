package local.tomo.medi.medicament;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.data.Disease;

public class DiseasesInMedicamentAdapter extends RecyclerView.Adapter<DiseasesInMedicamentAdapter.DiseaseViewHolder> {

    private List<Disease> diseases;
    private Context context;


    public DiseasesInMedicamentAdapter(List<Disease> diseases, Context context) {
        this.diseases = diseases;
        this.context = context;
    }

    @Override
    public DiseasesInMedicamentAdapter.DiseaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_diseases_in_medicament, null);
        return new DiseaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiseasesInMedicamentAdapter.DiseaseViewHolder holder, int position) {

        Disease disease = diseases.get(position);
        byte[] photo = disease.getPatient().getPhoto();
        if(photo != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            holder.imageView.setImageBitmap(bitmap);
        }
        holder.textViewPatientName.setText(disease.getPatient().getName());
        holder.textViewName.setText(disease.getName());
        holder.textViewStartDate.append(Months.createDate(disease.getStartLong()));
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }

    public static class DiseaseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.textViewPatientName)
        TextView textViewPatientName;
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.textViewStartDate)
        TextView textViewStartDate;

        public DiseaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
