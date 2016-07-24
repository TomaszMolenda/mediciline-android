package local.tomo.medi.medicament;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Medicament;

/**
 * Created by tomo on 2016-07-15.
 */
public class DiseasesInMedicamentAdapter extends RecyclerView.Adapter<DiseasesInMedicamentAdapter.DiseaseViewHolder> {

    private List<Disease> diseases;
    private Medicament medicament;
    private Context context;


    public DiseasesInMedicamentAdapter(List<Disease> diseases, Medicament medicament, Context context) {
        this.diseases = diseases;
        this.context = context;
        this.medicament = medicament;
    }

    @Override
    public DiseasesInMedicamentAdapter.DiseaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_diseases_in_medicament, null);
        DiseaseViewHolder diseaseViewHolder = new DiseaseViewHolder(view);
        return diseaseViewHolder;
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

        ImageView imageView;
        TextView textViewPatientName;
        TextView textViewName;
        TextView textViewStartDate;

        public DiseaseViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textViewPatientName = (TextView) itemView.findViewById(R.id.textViewPatientName);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewStartDate = (TextView) itemView.findViewById(R.id.textViewStartDate);

        }
    }
}
