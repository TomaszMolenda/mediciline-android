package local.tomo.medi.patient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import local.tomo.medi.R;
import local.tomo.medi.network.RestIntefrace;
import local.tomo.medi.network.RetrofitBuilder;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPatientActivity extends Activity {

    private final int CAMERA_CAPTURE = 1;
    private final int CROP_PIC = 2;

    private DatabaseHelper databaseHelper = null;

    private EditText editTextName;
    private EditText editTextBirthday;
    private Button buttonSave;
    private ImageView imageViewPhoto;
    private Uri outputFileUri;
    private byte[] photo;

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
                //Intent takePicture = new Intent(getApplicationContext(), LiveCameraActivity.class);
                startActivityForResult(takePicture, CAMERA_CAPTURE);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                final Patient patient = new Patient(name, chooseDate.getTime(), photo);
                try {
                    Dao<Patient, Integer> patientDao = getHelper().getPatientDao();
                    patientDao.create(patient);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //sendPatientToServer(patient);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();


            }
        });
    }

    private void sendPatientToServer(final Patient patient) {
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
    }


    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == CAMERA_CAPTURE) {
                outputFileUri = data.getData();
                performCrop();
            }
            else if (requestCode == CROP_PIC) {
                Bundle extras = data.getExtras();
                Bitmap thePic = extras.getParcelable("data");
                photo = getBitmapAsByteArray(thePic);
                imageViewPhoto.setImageBitmap(thePic);

            }
        }

    }

    private static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    private void performCrop() {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(outputFileUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, CROP_PIC);
        }
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
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
}
