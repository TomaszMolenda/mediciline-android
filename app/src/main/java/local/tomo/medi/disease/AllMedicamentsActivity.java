package local.tomo.medi.disease;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Medicament_Disease;

public class AllMedicamentsActivity extends AppCompatActivity {

    private static final String TAG = "meditomo";

    private DatabaseHelper databaseHelper;

    private ArrayList<Medicament> medicaments;

    private int diseaseId;
    private Disease disease;

    private AllMedicamentsAdapter allMedicamentsAdapter;
    private ListView listView;
    private EditText editTextSearch;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_medicaments);

        Bundle bundle = getIntent().getExtras();
        diseaseId = bundle.getInt("diseaseId");

        listView = (ListView) findViewById(android.R.id.list);
        editTextSearch = (EditText) findViewById(R.id.EditTextSearch);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        button = (Button) findViewById(R.id.button);

        try {
            disease = getHelper().getDiseaseDao().queryForId(diseaseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setMedicaments();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Medicament medicament : medicaments) {
                    if(medicament.isChecked()) {
                        Medicament_Disease medicament_disease = new Medicament_Disease(medicament, disease);
                        try {
                            Dao<Medicament_Disease, Integer> medicament_diseaseDao = getHelper().getMedicament_DiseaseDao();
                            medicament_diseaseDao.create(medicament_disease);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                finish();
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

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = editTextSearch.getText().toString().toLowerCase();
                List<Medicament> searchedMedicaments = new ArrayList<Medicament>();
                for (Medicament medicament : medicaments) {
                    if(medicament.getName().toLowerCase().contains(text) || medicament.getProducent().toLowerCase().contains(text))
                        searchedMedicaments.add(medicament);
                }
                allMedicamentsAdapter = new AllMedicamentsAdapter(listView, getApplicationContext(), R.layout.adapter_all_medicaments, (ArrayList<Medicament>) searchedMedicaments);
                listView.setAdapter(allMedicamentsAdapter);
                allMedicamentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setMedicaments() {
        try {
            QueryBuilder<Medicament, Integer> queryBuilder = getHelper().getMedicamentDao().queryBuilder();
            queryBuilder.orderBy("id", false).where().eq("archive", false);
            List<Medicament> list = queryBuilder.query();
            medicaments = new ArrayList<>(list);
            for (Medicament medicament : list) {
                ForeignCollection<Medicament_Disease> medicament_diseases = medicament.getMedicament_diseases();
                for (Medicament_Disease medicament_disease : medicament_diseases) {
                    int id = medicament_disease.getDisease().getId();
                    if(diseaseId == id)
                        medicaments.remove(medicament);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allMedicamentsAdapter = new AllMedicamentsAdapter(listView, getApplicationContext(), R.layout.adapter_all_medicaments, medicaments);
        listView.setAdapter(allMedicamentsAdapter);
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
