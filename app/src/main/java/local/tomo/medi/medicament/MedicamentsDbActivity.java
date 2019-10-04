package local.tomo.medi.medicament;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.DbMedicament;
import lombok.SneakyThrows;

public class MedicamentsDbActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.editTextSearch)
    EditText editTextSearch;

    private DatabaseHelper databaseHelper;
    private List<DbMedicament> dbMedicaments = new ArrayList<DbMedicament>();
    private AllMedicamentDbAdapter allMedicamentDbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaments_db);
        ButterKnife.bind(this);
    }

    @OnTextChanged(R.id.editTextSearch)
    @SneakyThrows
    void search(CharSequence s, int start, int before, int count) {
        String searchText = s.toString();
        if(searchText.length() >= 3) {
            Dao<DbMedicament, Integer> medicamentDbDao = getHelper().getMedicamentDbDao();
            QueryBuilder<DbMedicament, Integer> queryBuilder = medicamentDbDao.queryBuilder();
            queryBuilder.where().like("productName", "%"+searchText+"%");
            PreparedQuery<DbMedicament> prepare = queryBuilder.prepare();
            dbMedicaments = medicamentDbDao.query(prepare);
            allMedicamentDbAdapter = new AllMedicamentDbAdapter(getApplicationContext(), R.layout.adapter_all_medicament_db_list_row, (ArrayList<DbMedicament>) dbMedicaments);
            listView.setAdapter(allMedicamentDbAdapter);
        }
        else {
            listView.setAdapter(null);
            dbMedicaments.clear();
        }
    }

    @OnItemClick(R.id.list)
    void showInfo(AdapterView<?> parent, View view, int position, long id) {
        int packageID = ((DbMedicament) parent.getItemAtPosition(position)).getPackageID();
        Intent intent = new Intent(getApplicationContext(), MedicamentDbDetailsActivity.class);
        intent.putExtra("packageID", packageID);
        startActivity(intent);
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
