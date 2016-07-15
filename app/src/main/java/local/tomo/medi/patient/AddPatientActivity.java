package local.tomo.medi.patient;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import local.tomo.medi.MainActivity;
import local.tomo.medi.R;
import local.tomo.medi.network.RestIntefrace;
import local.tomo.medi.network.RetrofitBuilder;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPatientActivity extends Activity {

    public static final int REQUEST_CAMERA = 1;

    private DatabaseHelper databaseHelper = null;

    private EditText editTextName;
    private EditText editTextBirthday;
    private Button buttonSave;
    private ImageView imageViewPhoto;
    private Uri outputFileUri;
    private String photoUrl;

    private DatePickerDialog datePickerDialog;

    private SimpleDateFormat dateFormatter;

    private Calendar chooseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        final Calendar calendar = Calendar.getInstance();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        chooseDate = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                chooseDate.set(year, monthOfYear, dayOfMonth);
                editTextBirthday.setText(dateFormatter.format(chooseDate.getTime()));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);


        editTextBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });


        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                startActivityForResult(takePicture, 0);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                final Patient patient = new Patient(name, chooseDate.getTime(), photoUrl);
                try {
                    Dao<Patient, Integer> patientDao = getHelper().getPatientDao();
                    patientDao.create(patient);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                RestIntefrace restIntefrace = RetrofitBuilder.getRestIntefrace();
                Call<Patient> call = restIntefrace.savePatient(patient);
                call.enqueue(new Callback<Patient>() {
                    @Override
                    public void onResponse(Call<Patient> call, Response<Patient> response) {
                        Patient body = response.body();
                        Toast.makeText(getApplicationContext(), "Osobę  " + body.getName() + " wysłano na serwer", Toast.LENGTH_SHORT).show();
                        try {
                            UpdateBuilder<Patient, Integer> updateBuilder = getHelper().getPatientDao().updateBuilder();
                            updateBuilder.updateColumnValue("idServer", body.getIdServer());
                            updateBuilder.where().eq("id", body.getId());
                            updateBuilder.update();
                            //getHelper().getPatientDao().update(body);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<Patient> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Błąd wysłania osoby  " + patient.getName() + "  na serwer", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();


            }
        });
    }

    private Uri setImageUri() {
        File directory = Environment.getExternalStorageDirectory();
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(directory,System.currentTimeMillis() + ".png");


        outputFileUri = Uri.fromFile(file);
        this.photoUrl = outputFileUri.getPath().toString();
        return outputFileUri;
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    imageViewPhoto.setImageURI(outputFileUri);
                    imageViewPhoto.setRotation(270);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    imageViewPhoto.setImageURI(outputFileUri);
                    imageViewPhoto.setRotation(270);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //photoFile.delete();
    }
}
