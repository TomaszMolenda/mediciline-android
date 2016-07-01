package local.tomo.medi.swipe;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import local.tomo.medi.AddMedicamentActivity;
import local.tomo.medi.MedicamentActivity;
import local.tomo.medi.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicamentFragment extends Fragment {

    private Button buttonMenuMedicamentAll;
    private Button buttonMenuMedicamentAdd;
    private Button buttonMenuMedicamentActive;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicament, container, false);
        buttonMenuMedicamentAll = (Button) view.findViewById(R.id.buttonMenuMedicamentAll);
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
        buttonMenuMedicamentActive = (Button) view.findViewById(R.id.buttonMenuMedicamentActive);
        buttonMenuMedicamentActive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonMenuMedicamentAll.setEnabled(false);
                Intent intent = new Intent(getActivity(), MedicamentActivity.class);
                intent.putExtra("medicaments", MedicamentActivity.ACTIVE_MEDICAMENTS);
                getActivity().startActivity(intent);
                return false;
            }
        });
        buttonMenuMedicamentAdd = (Button) view.findViewById(R.id.buttonMenuMedicamentAdd);
        buttonMenuMedicamentAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonMenuMedicamentAdd.setEnabled(false);
                Intent intent = new Intent(getActivity(), AddMedicamentActivity.class);
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
    }
}
