package local.tomo.medi.disease;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.RecyclerItemClickListener;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Patient;

public class DiseasesListActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private int patientId;

    private List<Disease> diseases;

    private RecyclerView recyclerViewDiseases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases_list);

        Bundle bundle = getIntent().getExtras();
        patientId = bundle.getInt("patientId");
        Patient patient = null;
        try {
            patient = getHelper().getPatientDao().queryForId(patientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ImageView imageViewProfilePic = (ImageView) findViewById(R.id.imageViewProfilePic);
        TextView textViewName = (TextView) findViewById(R.id.textViewName);
        TextView textViewBirthday = (TextView) findViewById(R.id.textViewBirthday);
        TextView textViewActiveDiseasesCount = (TextView) findViewById(R.id.textViewActiveDiseasesCount);
        TextView textViewEndedDiseasesCount = (TextView) findViewById(R.id.textViewEndedDiseasesCount);
        recyclerViewDiseases = (RecyclerView) findViewById(R.id.recyclerViewDiseases);
        FloatingActionButton floatingActionButtonAdd = (FloatingActionButton) findViewById(R.id.floatingActionButtonAdd);
        byte[] photo = patient.getPhoto();
        if(photo != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            imageViewProfilePic.setImageBitmap(bitmap);
        }
        textViewName.setText(patient.getName());
        textViewBirthday.setText("ur. " + Months.createDate(patient.getBirthdayLong()));


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


        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddDiseaseActivity.class);
                intent.putExtra("patientId", patientId);
                startActivityForResult(intent, 1);
            }
        });

        setDiseases();
    }



    private void setDiseases() {
        try {
            QueryBuilder<Disease, Integer> queryBuilder = getHelper().getDiseaseDao().queryBuilder();
            queryBuilder.where().eq("patient_id", patientId);
            queryBuilder.orderBy("stopLong", true);
            diseases = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DiseasesListAdapter diseasesListAdapter = new DiseasesListAdapter(diseases, this);
        recyclerViewDiseases.setAdapter(diseasesListAdapter);
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
