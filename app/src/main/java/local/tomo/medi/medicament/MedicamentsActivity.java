package local.tomo.medi.medicament;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;
import lombok.SneakyThrows;

public class MedicamentsActivity extends Activity {

    public static final int OUT_OF_DATE_MEDICAMENTS = 1;
    public static final int ACTIVE_MEDICAMENTS = 2;
    public static final int ARCHIVE_MEDICAMENTS = 3;

    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.editTextSearch)
    EditText editTextSearch;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    private boolean hideArchive;
    private DatabaseHelper databaseHelper;
    private List<Medicament> medicaments;
    private AllMedicamentAdapter allMedicamentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaments);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        switch (bundle.getInt("medicaments")) {
            case ACTIVE_MEDICAMENTS:
                fabAdd.setVisibility(View.VISIBLE);
                setActiveMedicaments();
                break;
            case ARCHIVE_MEDICAMENTS:
                hideArchive = true;
                fabAdd.setVisibility(View.INVISIBLE);
                setArchiveMedicaments();
                break;
            case OUT_OF_DATE_MEDICAMENTS:
                fabAdd.setVisibility(View.INVISIBLE);
                setOutOfDateMedicaments();
                break;
        }

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                //// TODO: 2016-07-03 synchronize all
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(listView != null && listView.getChildCount() > 0){
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeRefreshLayout.setEnabled(enable);
            }
        });

    }

    @OnTextChanged(R.id.editTextSearch)
    void search(CharSequence s, int start, int before, int count) {
        String text = editTextSearch.getText().toString().toLowerCase();
        List<Medicament> searchedMedicaments = new ArrayList<>();
        for (Medicament medicament : medicaments) {
            if(medicament.getName().toLowerCase().contains(text) || medicament.getProducent().toLowerCase().contains(text))
                searchedMedicaments.add(medicament);
        }
        allMedicamentAdapter = new AllMedicamentAdapter(MedicamentsActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) searchedMedicaments);
        listView.setAdapter(allMedicamentAdapter);
        allMedicamentAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fabAdd)
    void add() {
        Intent intent = new Intent(getApplicationContext(), AddMedicamentActivity.class);
        startActivityForResult(intent, 1);
    }

    @SneakyThrows
    private void setOutOfDateMedicaments() {
        Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
        QueryBuilder<Medicament, Integer> queryBuilder = medicamentDao.queryBuilder();
        queryBuilder.orderBy("date", false).where().eq("overdue", true).and().eq("archive", false);
        queryBuilder.prepare();
        medicaments = queryBuilder.query();
        allMedicamentAdapter = new AllMedicamentAdapter(MedicamentsActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) medicaments);
        listView.setAdapter(allMedicamentAdapter);
    }

    @SneakyThrows
    private void setArchiveMedicaments() {
        Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
        QueryBuilder<Medicament, Integer> queryBuilder = medicamentDao.queryBuilder();
        queryBuilder.orderBy("id", false).where().eq("archive", true);
        PreparedQuery<Medicament> prepare = queryBuilder.prepare();
        medicaments = medicamentDao.query(prepare);
        allMedicamentAdapter = new AllMedicamentAdapter(MedicamentsActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) medicaments);
        listView.setAdapter(allMedicamentAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setActiveMedicaments();
    }

    @SneakyThrows
    public void setActiveMedicaments() {
        Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
        QueryBuilder<Medicament, Integer> queryBuilder = medicamentDao.queryBuilder();
        queryBuilder.orderBy("overdue", false).orderBy("id", false).where().eq("archive", false);
        PreparedQuery<Medicament> prepare = queryBuilder.prepare();
        medicaments = medicamentDao.query(prepare);
        allMedicamentAdapter = new AllMedicamentAdapter(MedicamentsActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) medicaments);
        listView.setAdapter(allMedicamentAdapter);
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public boolean isHideArchive() {
        return hideArchive;
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
