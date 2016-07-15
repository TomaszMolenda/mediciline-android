package local.tomo.medi.patient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import local.tomo.medi.R;
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
        holder.tvPatientName.setText(patient.getName().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(patient.getBirthdayLong());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        holder.tvBirthdate.setText(sdf.format(calendar.getTime()).toString());
        holder.tvId.setText(patient.getId()+"");
        holder.tvIdServer.setText(patient.getIdServer()+"");


    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {

        //ImageView ivProfilePic;
        TextView tvPatientName;
        TextView tvBirthdate;
        TextView tvId;
        TextView tvIdServer;
        public PatientViewHolder(View itemView) {
            super(itemView);
            //ivProfilePic = (ImageView) itemView.findViewById(R.id.ivProfilePic);
            tvPatientName = (TextView) itemView.findViewById(R.id.tvPatientName);
            tvBirthdate = (TextView) itemView.findViewById(R.id.tvBirthdate);
            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvIdServer = (TextView) itemView.findViewById(R.id.tvIdServer);

        }
    }
}
