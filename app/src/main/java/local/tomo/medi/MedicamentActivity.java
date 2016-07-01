package local.tomo.medi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.MedicamentDb;

public class MedicamentActivity extends AppCompatActivity {

    public static final int ALL_MEDICAMENTS = 1;
    public static final int ACTIVE_MEDICAMENTS = 2;

    private DatabaseHelper databaseHelper = null;

    private List<Medicament> medicaments;

    private AllMedicamentAdapter allMedicamentAdapter;
    private ListView listView;
    private EditText editTextMedicamentSearch;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament);

        listView = (ListView) findViewById(android.R.id.list);

        Bundle bundle = getIntent().getExtras();
        switch (bundle.getInt("medicaments")) {
            case ALL_MEDICAMENTS:
                setAllMedicaments();
                break;
            case ACTIVE_MEDICAMENTS:
                setActiveMedicaments();
                break;
        }


        editTextMedicamentSearch = (EditText) findViewById(R.id.EditTextMedicamentSearch);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);




        FloatingActionButton fabAddMedicament = (FloatingActionButton) findViewById(R.id.fabAddMedicament);

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
                allMedicamentAdapter = new AllMedicamentAdapter(getApplicationContext(), R.layout.all_medicament_list_row, (ArrayList<Medicament>) searchedMedicaments);
                listView.setAdapter(allMedicamentAdapter);
                allMedicamentAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                //refreshList();
                swipeRefreshLayout.setRefreshing(false);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setAllMedicaments();
    }

    private void setActiveMedicaments() {
        try {
            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
            QueryBuilder<Medicament, Integer> queryBuilder = medicamentDao.queryBuilder();
            queryBuilder.orderBy("id", false).where().eq("archive", false);
            PreparedQuery<Medicament> prepare = queryBuilder.prepare();
            medicaments = medicamentDao.query(prepare);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allMedicamentAdapter = new AllMedicamentAdapter(getApplicationContext(), R.layout.all_medicament_list_row, (ArrayList<Medicament>) medicaments);
        listView.setAdapter(allMedicamentAdapter);
    }

    private void setAllMedicaments() {
        try {
            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
            QueryBuilder<Medicament, Integer> queryBuilder = medicamentDao.queryBuilder();
            queryBuilder.orderBy("id", false);
            PreparedQuery<Medicament> prepare = queryBuilder.prepare();
            medicaments = medicamentDao.query(prepare);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allMedicamentAdapter = new AllMedicamentAdapter(getApplicationContext(), R.layout.all_medicament_list_row, (ArrayList<Medicament>) medicaments);
        listView.setAdapter(allMedicamentAdapter);
    }

    private void refreshList() {
        try {
            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
            List<Medicament> medicaments = medicamentDao.queryForAll();
            allMedicamentAdapter = new AllMedicamentAdapter(getApplicationContext(), R.layout.all_medicament_list_row, (ArrayList<Medicament>) medicaments);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
