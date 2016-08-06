package local.tomo.medi.file;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.ByteArrayOutputStream;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.File;
import lombok.SneakyThrows;

public class FilesActivity extends AppCompatActivity {

    public static final String TAG = "meditomo";
    private static final int WIDTH_REDUCE_PICTURE = 1024;
    private static final int MAX_PICTURE_SIZE = 100000;


    private final int CAMERA_CAPTURE = 1;

    private DatabaseHelper databaseHelper;

    private int diseaseId;
    private Disease disease;

    private Uri uri;

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
            @SneakyThrows
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                java.io.File photo = createTemporaryFile("picture", ".jpg");
                uri = Uri.fromFile(photo);
                photo.deleteOnExit();
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(takePicture, CAMERA_CAPTURE);
            }
        });


        //http://stackoverflow.com/questions/15261088/gridview-with-two-columns-and-auto-resized-images
    }

    private java.io.File createTemporaryFile(String part, String ext) throws Exception
    {
        java.io.File tempDir= Environment.getExternalStorageDirectory();
        tempDir=new java.io.File(tempDir.getAbsolutePath()+"/temp/");
        if(!tempDir.exists())
        {
            tempDir.mkdirs();
        }
        return java.io.File.createTempFile(part, ext, tempDir);
    }

    @Override
    @SneakyThrows
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            double height = bitmap.getHeight();
            double width = bitmap.getWidth();
            double h = WIDTH_REDUCE_PICTURE / (width/height);
            bitmap = Bitmap.createScaledBitmap(bitmap, WIDTH_REDUCE_PICTURE, (int) h, true);
            if(bitmap.getHeight() < bitmap.getWidth()) {
                Matrix rotateMatrix = new Matrix();
                rotateMatrix.postRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), rotateMatrix, false);
            }
            byte[] bytes;
            int compress = 90;
            do {
                bitmap.compress(Bitmap.CompressFormat.JPEG, compress, bos);
                bytes = bos.toByteArray();
                bos.reset();
                compress -=10;
            } while (bytes.length > MAX_PICTURE_SIZE);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Opis zdjęcia");
            final EditText input = new EditText(this);
            builder.setView(input);
            final byte[] finalBytes = bytes;
            builder.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                @SneakyThrows
                public void onClick(DialogInterface dialog, int id) {
                    File file = new File();
                    file.setName(input.getText().toString());
                    file.setFile(finalBytes);
                    file.setDisease(disease);
                    getHelper().getFileDao().create(file);
                    prepareData();
                }
            });

            builder.create();
            builder.show();
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


