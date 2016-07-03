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
    private Button buttonMenuMedicamentOutOfDate;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_fragment_medicament, container, false);

        buttonMenuMedicamentScan = (Button) view.findViewById(R.id.buttonMenuMedicamentScan);
        buttonMenuMedicamentAll = (Button) view.findViewById(R.id.buttonMenuMedicamentAll);
        buttonMenuMedicamentActive = (Button) view.findViewById(R.id.buttonMenuMedicamentActive);
        buttonMenuMedicamentArchive = (Button) view.findViewById(R.id.buttonMenuMedicamentArchive);
        buttonMenuMedicamentAdd = (Button) view.findViewById(R.id.buttonMenuMedicamentAdd);
        buttonMenuMedicamentOutOfDate = (Button) view.findViewById(R.id.buttonMenuMedicamentOutOfDate);

        buttonMenuMedicamentAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonMenuMedicamentAll.setEnabled(false);
            }
        });

        buttonMenuMedicamentActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonMenuMedicamentActive.setEnabled(false);
                Intent intent = new Intent(getActivity(), MedicamentsActivity.class);
                intent.putExtra("medicaments", MedicamentsActivity.ACTIVE_MEDICAMENTS);
                getActivity().startActivity(intent);
            }
        });

        buttonMenuMedicamentArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonMenuMedicamentArchive.setEnabled(false);
                Intent intent = new Intent(getActivity(), MedicamentsActivity.class);
                intent.putExtra("medicaments", MedicamentsActivity.ARCHIVE_MEDICAMENTS);
                getActivity().startActivity(intent);
            }
        });

        buttonMenuMedicamentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonMenuMedicamentAdd.setEnabled(false);
                Intent intent = new Intent(getActivity(), AddMedicamentActivity.class);
                getActivity().startActivity(intent);
            }
        });

        buttonMenuMedicamentScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonMenuMedicamentScan.setEnabled(false);
                Intent intent = new Intent(getActivity(), AddMedicamentActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("scan", true);
                intent.putExtras(b);
                getActivity().startActivity(intent);
            }
        });

        buttonMenuMedicamentOutOfDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonMenuMedicamentOutOfDate.setEnabled(false);
                Intent intent = new Intent(getActivity(), MedicamentsActivity.class);
                intent.putExtra("medicaments", MedicamentsActivity.OUT_OF_DATE_MEDICAMENTS);
                getActivity().startActivity(intent);
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
        buttonMenuMedicamentOutOfDate.setEnabled(true);

    }
}
