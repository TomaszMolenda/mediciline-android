package local.tomo.medi.medicament;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.DbMedicament;

public class MedicamentsDbActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper = null;

    private List<DbMedicament> dbMedicaments = new ArrayList<DbMedicament>();

    private AllMedicamentDbAdapter allMedicamentDbAdapter;
    private ListView listView;
    private EditText editTextMedicamentDbSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaments_db);

        listView = (ListView) findViewById(android.R.id.list);
        editTextMedicamentDbSearch = (EditText) findViewById(R.id.editTextSearch);

        editTextMedicamentDbSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                if(searchText.length() >= 3) {
                    try {
                        Dao<DbMedicament, Integer> medicamentDbDao = getHelper().getMedicamentDbDao();
                        QueryBuilder<DbMedicament, Integer> queryBuilder = medicamentDbDao.queryBuilder();
                        queryBuilder.where().like("productName", "%"+searchText+"%");
                        PreparedQuery<DbMedicament> prepare = queryBuilder.prepare();
                        dbMedicaments = medicamentDbDao.query(prepare);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    allMedicamentDbAdapter = new AllMedicamentDbAdapter(MedicamentsDbActivity.this, getApplicationContext(), R.layout.adapter_all_medicament_db_list_row, (ArrayList<DbMedicament>) dbMedicaments);
                    listView.setAdapter(allMedicamentDbAdapter);
                }
                else {
                    listView.setAdapter(null);
                    dbMedicaments.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int packageID = ((DbMedicament) parent.getItemAtPosition(position)).getPackageID();
                Intent intent = new Intent(getApplicationContext(), MedicamentDbDetailsActivity.class);
                intent.putExtra("packageID", packageID);
                startActivity(intent);
            }
        });
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
