package local.tomo.medi.activity.drug.scan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.activity.DatabaseAccessActivity;
import local.tomo.medi.activity.drug.DrugActivity;
import local.tomo.medi.activity.drug.add.SetOverdueActivity;
import local.tomo.medi.ormlite.data.Drug;
import lombok.SneakyThrows;

import static local.tomo.medi.ormlite.data.Drug.D_ID;

public class ScanActivity extends DatabaseAccessActivity {

    private static final int PERMISSIONS_REQUEST_CAMERA = 1;

    private CameraSource cameraSource;

    @BindView(R.id.camera_preview)
    SurfaceView cameraView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);

        if (ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
        } else {

            runActivity();
        }
    }

    private void runActivity() {

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(ScanActivity.this).build();

        cameraSource = new CameraSource.Builder(ScanActivity.this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @SneakyThrows
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                cameraSource = new CameraSource
                        .Builder(ScanActivity.this, barcodeDetector)
                        .setAutoFocusEnabled(true)
                        .build();

                cameraSource.start(cameraView.getHolder());
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            @SneakyThrows
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> detectedItems = detections.getDetectedItems();

                if (detectedItems.size() > 0) {

                    String ean = detectedItems.valueAt(0).rawValue;

                    Drug drug = getHelper().getDrugQuery().getEan(ean);

                    if (Objects.isNull(drug)) {

                        runOnUiThread(() -> Toast.makeText(ScanActivity.this, getString(R.string.ean_not_found, ean), Toast.LENGTH_SHORT).show());

                    } else {
                        Intent intent = new Intent(ScanActivity.this, SetOverdueActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putInt(D_ID, drug.getId());

                        intent.putExtras(bundle);
                        startActivity(intent);

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(() -> cameraSource.stop());
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startActivity(new Intent(ScanActivity.this, ScanActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else {

            startActivity(new Intent(ScanActivity.this, DrugActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }
}
