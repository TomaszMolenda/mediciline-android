package local.tomo.medi.patient;

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
import local.tomo.medi.ormlite.data.Patient;

/**
 * Created by tomo on 2016-07-15.
 */
public class AllPatientsAdapter extends RecyclerView.Adapter<AllPatientsAdapter.PatientViewHolder> {

    private List<Patient> patients;
    private Context context;

    public AllPatientsAdapter(List<Patient> patients, Context context) {
        this.patients = patients;
        this.context = context;
    }

    @Override
    public AllPatientsAdapter.PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_all_patients_cardview, null);
        PatientViewHolder userViewHolder = new PatientViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(AllPatientsAdapter.PatientViewHolder holder, int position) {
        Patient patient = patients.get(position);
        byte[] photo = patient.getPhoto();
        if(photo != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            holder.imageViewProfilePic.setImageBitmap(bitmap);
        }
        holder.textViewPatientName.setText(patient.getName().toString());
        holder.textViewBirthdate.setText(Months.createDate(patient.getBirthdayLong()));
        holder.textViewDiseasesCount.append(patient.getDiseases().size()+"");
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewProfilePic;
        TextView textViewPatientName;
        TextView textViewBirthdate;
        TextView textViewDiseasesCount;
        public PatientViewHolder(View itemView) {
            super(itemView);
            imageViewProfilePic = (ImageView) itemView.findViewById(R.id.imageViewProfilePic);
            textViewPatientName = (TextView) itemView.findViewById(R.id.textViewPatientName);
            textViewBirthdate = (TextView) itemView.findViewById(R.id.textViewBirthdate);
            textViewDiseasesCount = (TextView) itemView.findViewById(R.id.textViewDiseasesCount);
        }
    }
}
