package local.tomo.medi.patient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Patient;
import lombok.SneakyThrows;

public class AddPatientActivity extends Activity {

    private final int CAMERA_CAPTURE = 1;
    private final int CROP_PIC = 2;

    @BindView(R.id.editTextName)
    EditText editTextName;
    @BindView(R.id.editTextBirthday)
    EditText editTextBirthday;
    @BindView(R.id.buttonSave)
    Button buttonSave;
    @BindView(R.id.imageViewPhoto)
    ImageView imageViewPhoto;

    private DatabaseHelper databaseHelper;

    private Uri outputFileUri;
    private byte[] photo;

    private DatePickerDialog datePickerDialog;

    private Calendar chooseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        ButterKnife.bind(this);

        final Calendar calendar = Calendar.getInstance();

        chooseDate = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                chooseDate.set(year, monthOfYear, dayOfMonth);
                editTextBirthday.setText(Months.createDate(chooseDate.getTimeInMillis()));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @OnClick(R.id.editTextBirthday)
    void chooseBirthday() {
        datePickerDialog.show();
    }

    @OnClick(R.id.imageViewPhoto)
    void choodePhoto() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, CAMERA_CAPTURE);
    }

    @OnClick(R.id.buttonSave)
    @SneakyThrows
    void save() {
        if(isValidate()){
            String name = editTextName.getText().toString().trim();
            final Patient patient = new Patient(name, chooseDate.getTime(), photo);
            Dao<Patient, Integer> patientDao = getHelper().getPatientDao();
            patientDao.create(patient);
            // TODO: 2016-08-08 Implement send patient to server
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
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
            Toast.makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT).show();
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

    public boolean isValidate() {
        if(editTextName.getText().toString().trim().length() < 4) {
            Toast.makeText(this, getString(R.string.Name_min_4_signs), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(editTextBirthday.getText().length() == 0) {
            Toast.makeText(this, getString(R.string.Put_birthday), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(photo == null) {
            Toast.makeText(this, getString(R.string.Put_photo), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
