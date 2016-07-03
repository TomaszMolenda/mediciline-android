package local.tomo.medi.swipe;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import local.tomo.medi.medicament.AddMedicamentActivity;
import local.tomo.medi.medicament.MedicamentsActivity;
import local.tomo.medi.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicamentFragment extends Fragment {

    private Button buttonMenuMedicamentAll;
    private Button buttonMenuMedicamentAdd;
    private Button buttonMenuMedicamentActive;
    private Button buttonMenuMedicamentArchive;
    private Button buttonMenuMedicamentScan;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_fragment_medicament, container, false);

        buttonMenuMedicamentScan = (Button) view.findViewById(R.id.buttonMenuMedicamentScan);
        buttonMenuMedicamentAll = (Button) view.findViewById(R.id.buttonMenuMedicamentAll);
        buttonMenuMedicamentActive = (Button) view.findViewById(R.id.buttonMenuMedicamentActive);
        buttonMenuMedicamentArchive = (Button) view.findViewById(R.id.buttonMenuMedicamentArchive);
        buttonMenuMedicamentAdd = (Button) view.findViewById(R.id.buttonMenuMedicamentAdd);

        buttonMenuMedicamentAll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonMenuMedicamentAll.setEnabled(false);

                return false;
            }
        });

        buttonMenuMedicamentActive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonMenuMedicamentActive.setEnabled(false);
                Intent intent = new Intent(getActivity(), MedicamentsActivity.class);
                intent.putExtra("medicaments", MedicamentsActivity.ACTIVE_MEDICAMENTS);
                getActivity().startActivity(intent);
                return false;
            }
        });

        buttonMenuMedicamentArchive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonMenuMedicamentArchive.setEnabled(false);
                Intent intent = new Intent(getActivity(), MedicamentsActivity.class);
                intent.putExtra("medicaments", MedicamentsActivity.ARCHIVE_MEDICAMENTS);
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
                //getActivity().finish();
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
        buttonMenuMedicamentArchive.setEnabled(true);


    }
}
