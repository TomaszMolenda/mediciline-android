package local.tomo.medi.swipe;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;

import local.tomo.medi.medicament.AddMedicamentActivity;
import local.tomo.medi.medicament.MedicamentsActivity;
import local.tomo.medi.R;
import local.tomo.medi.medicament.MedicamentsDbActivity;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicamentFragment extends Fragment {

    private DatabaseHelper databaseHelper;

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

        prepareButtonsCount();

        buttonMenuMedicamentAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonMenuMedicamentAll.setEnabled(false);
                Intent intent = new Intent(getActivity(), MedicamentsDbActivity.class);
                getActivity().startActivity(intent);
            }
        });

        buttonMenuMedicamentActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonMenuMedicamentActive.setEnabled(false);
                Intent intent = new Intent(getActivity(), MedicamentsActivity.class);
                intent.putExtra("medicaments", MedicamentsActivity.ACTIVE_MEDICAMENTS);
                getActivity().startActivityForResult(intent, 1);
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

    private void prepareButtonsCount() {
        try {
            QueryBuilder<Medicament, Integer> queryBuilder = getHelper().getMedicamentDao().queryBuilder();
            queryBuilder.where().eq("archive", false);
            buttonMenuMedicamentActive.setText("W apteczce\n" + queryBuilder.countOf());
            queryBuilder.reset();
            queryBuilder.where().eq("archive", true);
            buttonMenuMedicamentArchive.setText("Zużyte\n" + queryBuilder.countOf());
            queryBuilder.reset();
            queryBuilder.where().eq("overdue", true).and().eq("archive", false);
            buttonMenuMedicamentOutOfDate.setText("Przeterminowane\n" + queryBuilder.countOf());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getContext(),DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onDestroy() {
        super.onDestroyView();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
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
        prepareButtonsCount();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        prepareButtonsCount();
    }
}
