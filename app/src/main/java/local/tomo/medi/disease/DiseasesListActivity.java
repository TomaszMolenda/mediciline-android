package local.tomo.medi.disease;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import local.tomo.medi.R;
import local.tomo.medi.RecyclerItemClickListener;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Patient;
import lombok.SneakyThrows;

public class DiseasesListActivity extends AppCompatActivity {

    @BindView(R.id.imageViewProfilePic)
    ImageView imageViewProfilePic;
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewBirthday)
    TextView textViewBirthday;
    @BindView(R.id.textViewActiveDiseasesCount)
    TextView textViewActiveDiseasesCount;
    @BindView(R.id.textViewEndedDiseasesCount)
    TextView textViewEndedDiseasesCount;
    @BindView(R.id.recyclerViewDiseases)
    RecyclerView recyclerViewDiseases;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    private DatabaseHelper databaseHelper;

    private int patientId;

    private List<Disease> diseases;


    @Override
    @SneakyThrows
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases_list);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        patientId = bundle.getInt("patientId");
        Patient patient = getHelper().getPatientDao().queryForId(patientId);

        byte[] photo = patient.getPhoto();
        if(photo != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            imageViewProfilePic.setImageBitmap(bitmap);
        }
        textViewName.setText(patient.getName());
        textViewBirthday.setText(getString(R.string.birthday, Months.createDate(patient.getBirthdayLong())));

        recyclerViewDiseases.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDiseases.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerViewDiseases,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), DiseaseDetailsActivity.class);
                        intent.putExtra("diseaseId", diseases.get(position).getId());
                        startActivityForResult(intent, 1);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );
        setDiseases();
    }

    @OnClick(R.id.fabAdd)
    void add() {
        Intent intent = new Intent(getApplicationContext(), AddDiseaseActivity.class);
        intent.putExtra("patientId", patientId);
        startActivityForResult(intent, 1);
    }


    @SneakyThrows
    private void setDiseases() {
        QueryBuilder<Disease, Integer> queryBuilder = getHelper().getDiseaseDao().queryBuilder();
        queryBuilder.where().eq("patient_id", patientId);
        queryBuilder.orderBy("stopLong", true);
        queryBuilder.orderBy("startLong", false);
        diseases = queryBuilder.query();
        textViewActiveDiseasesCount.setText(getString(R.string.Diseases_duration, getActiveCount(diseases)));
        textViewEndedDiseasesCount.setText(getString(R.string.Diseases_ended, getArchiveCount(diseases)));
        DiseasesListAdapter diseasesListAdapter = new DiseasesListAdapter(diseases, this);
        recyclerViewDiseases.setAdapter(diseasesListAdapter);
    }

    private int getActiveCount(List<Disease> diseases) {
        int i = 0;
        for (Disease disease : diseases) {
            if(!disease.isArchive())
                i++;
        }
        return i;
    }

    private int getArchiveCount(List<Disease> diseases) {
        int i = 0;
        for (Disease disease : diseases) {
            if(disease.isArchive())
                i++;
        }
        return i;
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setDiseases();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
