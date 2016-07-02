package local.tomo.medi.swipe;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import local.tomo.medi.AddMedicamentActivity;
import local.tomo.medi.MedicamentActivity;
import local.tomo.medi.R;
import local.tomo.medi.ScanActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicamentFragment extends Fragment {

    private Button buttonMenuMedicamentAll;
    private Button buttonMenuMedicamentAdd;
    private Button buttonMenuMedicamentActive;
    private Button buttonMenuMedicamentScan;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicament, container, false);

        buttonMenuMedicamentScan = (Button) view.findViewById(R.id.buttonMenuMedicamentScan);
        buttonMenuMedicamentAll = (Button) view.findViewById(R.id.buttonMenuMedicamentAll);
        buttonMenuMedicamentActive = (Button) view.findViewById(R.id.buttonMenuMedicamentActive);
        buttonMenuMedicamentAdd = (Button) view.findViewById(R.id.buttonMenuMedicamentAdd);

        buttonMenuMedicamentAll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonMenuMedicamentAll.setEnabled(false);
                Intent intent = new Intent(getActivity(), MedicamentActivity.class);
                intent.putExtra("medicaments", MedicamentActivity.ALL_MEDICAMENTS);
                getActivity().startActivity(intent);
                return false;
            }
        });

        buttonMenuMedicamentActive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonMenuMedicamentActive.setEnabled(false);
                Intent intent = new Intent(getActivity(), MedicamentActivity.class);
                intent.putExtra("medicaments", MedicamentActivity.ACTIVE_MEDICAMENTS);
                getActivity().startActivity(intent);
                return false;
            }
        });

        buttonMenuMedicamentAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonMenuMedicamentAdd.setEnabled(false);
                Intent intent = new Intent(getActivity(), AddMedicamentActivity.class);
                getActivity().startActivity(intent);
                return false;
            }
        });
        buttonMenuMedicamentScan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonMenuMedicamentScan.setEnabled(false);
                Intent intent = new Intent(getActivity(), AddMedicamentActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("scan", true);
                intent.putExtras(b);
                getActivity().startActivity(intent);
                return false;
            }
        });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        buttonMenuMedicamentAll.setEnabled(true);
        buttonMenuMedicamentAdd.setEnabled(true);
        buttonMenuMedicamentActive.setEnabled(true);
        buttonMenuMedicamentScan.setEnabled(true);


    }
}
