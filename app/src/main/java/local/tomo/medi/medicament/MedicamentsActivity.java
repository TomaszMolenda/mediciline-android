package local.tomo.medi.medicament;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;

public class MedicamentsActivity extends Activity {

    public static final int OUT_OF_DATE_MEDICAMENTS = 1;
    public static final int ACTIVE_MEDICAMENTS = 2;
    public static final int ARCHIVE_MEDICAMENTS = 3;

    private boolean showPopUpMenu;

    private DatabaseHelper databaseHelper = null;

    private List<Medicament> medicaments;

    private AllMedicamentAdapter allMedicamentAdapter;
    private ListView listView;
    private EditText editTextMedicamentSearch;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fabAddMedicament;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaments);

        listView = (ListView) findViewById(android.R.id.list);
        fabAddMedicament = (FloatingActionButton) findViewById(R.id.fabAddMedicament);
        editTextMedicamentSearch = (EditText) findViewById(R.id.EditTextMedicamentSearch);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        Bundle bundle = getIntent().getExtras();
        switch (bundle.getInt("medicaments")) {
            case ACTIVE_MEDICAMENTS:
                showPopUpMenu = true;
                fabAddMedicament.setVisibility(View.VISIBLE);
                setActiveMedicaments();
                break;
            case ARCHIVE_MEDICAMENTS:
                fabAddMedicament.setVisibility(View.INVISIBLE);
                setArchiveMedicaments();
                break;
            case OUT_OF_DATE_MEDICAMENTS:
                showPopUpMenu = true;
                fabAddMedicament.setVisibility(View.INVISIBLE);
                setOutOfDateMedicaments();
                break;
        }

        editTextMedicamentSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = editTextMedicamentSearch.getText().toString().toLowerCase();
                List<Medicament> searchedMedicaments = new ArrayList<Medicament>();
                for (Medicament medicament : medicaments) {
                    if(medicament.getName().toLowerCase().contains(text) || medicament.getProducent().toLowerCase().contains(text))
                        searchedMedicaments.add(medicament);
                }
                allMedicamentAdapter = new AllMedicamentAdapter(listView, MedicamentsActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) searchedMedicaments);
                listView.setAdapter(allMedicamentAdapter);
                allMedicamentAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

        fabAddMedicament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMedicamentActivity.class);
                startActivityForResult(intent, 1);

            }
        });

    }

    private void setOutOfDateMedicaments() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
            QueryBuilder<Medicament, Integer> queryBuilder = medicamentDao.queryBuilder();
            queryBuilder.orderBy("date", false).where().eq("archive", false);
            PreparedQuery<Medicament> prepare = queryBuilder.prepare();
            List<Medicament> list = medicamentDao.query(prepare);
            medicaments = new ArrayList<>();
            for (Medicament medicament : list) {
                calendar.setTimeInMillis(medicament.getDate());
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                if(mYear < year) medicaments.add(medicament);
                if(mYear == year && mMonth < month) medicaments.add(medicament);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        allMedicamentAdapter = new AllMedicamentAdapter(listView, MedicamentsActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) medicaments);
        listView.setAdapter(allMedicamentAdapter);
    }

    private void setArchiveMedicaments() {
        try {
            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
            QueryBuilder<Medicament, Integer> queryBuilder = medicamentDao.queryBuilder();
            queryBuilder.orderBy("id", false).where().eq("archive", true);
            PreparedQuery<Medicament> prepare = queryBuilder.prepare();
            medicaments = medicamentDao.query(prepare);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allMedicamentAdapter = new AllMedicamentAdapter(listView, MedicamentsActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) medicaments);
        listView.setAdapter(allMedicamentAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setActiveMedicaments();
    }

    public void setActiveMedicaments() {
        try {
            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
            QueryBuilder<Medicament, Integer> queryBuilder = medicamentDao.queryBuilder();
            queryBuilder.orderBy("id", false).where().eq("archive", false);
            PreparedQuery<Medicament> prepare = queryBuilder.prepare();
            medicaments = medicamentDao.query(prepare);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allMedicamentAdapter = new AllMedicamentAdapter(listView, MedicamentsActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) medicaments);
        listView.setAdapter(allMedicamentAdapter);
    }

    private void refreshList() {
        try {
            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
            List<Medicament> medicaments = medicamentDao.queryForAll();
            allMedicamentAdapter = new AllMedicamentAdapter(listView, MedicamentsActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_list_row, (ArrayList<Medicament>) medicaments);
            listView.setAdapter(allMedicamentAdapter);
            allMedicamentAdapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public boolean showPopUpMenu() {
        return showPopUpMenu;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }


    public AllMedicamentAdapter getListAdapter() {
        return allMedicamentAdapter;
    }
}
