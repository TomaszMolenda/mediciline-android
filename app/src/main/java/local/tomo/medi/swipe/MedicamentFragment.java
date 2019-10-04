package local.tomo.medi.swipe;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import local.tomo.medi.R;
import local.tomo.medi.medicament.AddMedicamentActivity;
import local.tomo.medi.medicament.MedicamentsActivity;
import local.tomo.medi.medicament.MedicamentsDbActivity;
import local.tomo.medi.medicament.SearchMedicamentActivity;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;
import lombok.SneakyThrows;

public class MedicamentFragment extends Fragment {

    @BindView(R.id.buttonScan)
    Button buttonScan;
    @BindView(R.id.buttonAdd)
    Button buttonAdd;
    @BindView(R.id.buttonActive)
    Button buttonActive;
    @BindView(R.id.buttonArchive)
    Button buttonArchive;
    @BindView(R.id.buttonAll)
    Button buttonAll;
    @BindView(R.id.buttonOverdue)
    Button buttonOverdue;

    @BindView(R.id.textActive)
    TextView textViewActive;
    @BindView(R.id.textArchive)
    TextView textViewArchive;
    @BindView(R.id.textOverdue)
    TextView textViewOverdue;



    private DatabaseHelper databaseHelper;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_fragment_medicament, container, false);
        ButterKnife.bind(this, view);
        prepareButtonsCount();
        return view;
    }


    @OnClick(R.id.buttonAdd)
    void add() {
        buttonAdd.setEnabled(false);
        Intent intent = new Intent(getActivity(), SearchMedicamentActivity.class);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.buttonScan)
    void scan() {
        buttonScan.setEnabled(false);
        Intent intent = new Intent(getActivity(), AddMedicamentActivity.class);
        Bundle b = new Bundle();
        b.putBoolean("scan", true);
        intent.putExtras(b);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.buttonActive)
    void showActive() {
        buttonActive.setEnabled(false);
        Intent intent = new Intent(getActivity(), MedicamentsActivity.class);
        intent.putExtra("medicaments", MedicamentsActivity.ACTIVE_MEDICAMENTS);
        getActivity().startActivityForResult(intent, 1);
    }

    @OnClick(R.id.buttonArchive)
    void showArchive() {
        buttonArchive.setEnabled(false);
        Intent intent = new Intent(getActivity(), MedicamentsActivity.class);
        intent.putExtra("medicaments", MedicamentsActivity.ARCHIVE_MEDICAMENTS);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.buttonAll)
    void showAllInDatabase() {
        buttonAll.setEnabled(false);
        Intent intent = new Intent(getActivity(), MedicamentsDbActivity.class);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.buttonOverdue)
    void showOverdue() {
        buttonOverdue.setEnabled(false);
        Intent intent = new Intent(getActivity(), MedicamentsActivity.class);
        intent.putExtra("medicaments", MedicamentsActivity.OUT_OF_DATE_MEDICAMENTS);
        getActivity().startActivity(intent);
    }

    @SneakyThrows
    private void prepareButtonsCount() {
        QueryBuilder<Medicament, Integer> queryBuilder = getHelper().getMedicamentDao().queryBuilder();
        queryBuilder.where().eq("archive", false);
        textViewActive.setText(getString(R.string.text_view_count_active_medicament, queryBuilder.countOf()));
        queryBuilder.reset();
        queryBuilder.where().eq("archive", true);
        textViewArchive.setText(getString(R.string.text_view_count_archive_medicament, queryBuilder.countOf()));
        queryBuilder.reset();
        queryBuilder.where().eq("overdue", true).and().eq("archive", false);
        textViewOverdue.setText(getString(R.string.text_view_count_overdue_medicament, queryBuilder.countOf()));
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
        buttonScan.setEnabled(true);
        buttonAdd.setEnabled(true);
        buttonActive.setEnabled(true);
        buttonAll.setEnabled(true);
        buttonArchive.setEnabled(true);
        buttonOverdue.setEnabled(true);
        prepareButtonsCount();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        prepareButtonsCount();
    }
}
