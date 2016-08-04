package local.tomo.medi.file;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.File;
import lombok.SneakyThrows;

public class FilesActivity extends AppCompatActivity {

    public static final String TAG = "meditomo";

    private final int CAMERA_CAPTURE = 1;

    private DatabaseHelper databaseHelper;

    private int diseaseId;
    private Disease disease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);

        Bundle bundle = getIntent().getExtras();
        diseaseId = bundle.getInt("diseaseId");

        prepareData();


        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, CAMERA_CAPTURE);
            }
        });


        //http://stackoverflow.com/questions/15261088/gridview-with-two-columns-and-auto-resized-images
    }

    @Override
    @SneakyThrows
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == CAMERA_CAPTURE) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                final byte[] bytes = out.toByteArray();


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Opis zdjęcia");
                final EditText input = new EditText(this);
                builder.setView(input);
                builder.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    @SneakyThrows
                    public void onClick(DialogInterface dialog, int id) {
                        File file = new File();
                        file.setName(input.getText().toString());
                        file.setFile(bytes);
                        file.setDisease(disease);
                        getHelper().getFileDao().create(file);
                        prepareData();
                    }
                });

                builder.create();
                builder.show();
            }
        }

    }

    @SneakyThrows
    private void prepareData() {
        disease = getHelper().getDiseaseDao().queryForId(diseaseId);
        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new FilesAdapter(this, disease.getFiles()));
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
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
