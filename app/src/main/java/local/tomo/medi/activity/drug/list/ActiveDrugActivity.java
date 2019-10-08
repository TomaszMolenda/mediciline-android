package local.tomo.medi.activity.drug.list;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import local.tomo.medi.R;
import local.tomo.medi.activity.DatabaseAccessActivity;
import local.tomo.medi.ormlite.data.Drug;
import local.tomo.medi.ormlite.data.UserDrug;
import lombok.SneakyThrows;

import static local.tomo.medi.ormlite.data.Drug.D_NAME;

public class ActiveDrugActivity extends DatabaseAccessActivity {

    @BindView(R.id.editTextSearch)
    EditText editTextSearch;

    @BindView(R.id.listView)
    ListView listView;

    @SneakyThrows
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_drugs);
        ButterKnife.bind(this);

        Dao<UserDrug, Integer> userDrugsDataAccess = getHelper().getUserDrugsDataAccess();
        List<UserDrug> userDrugs = userDrugsDataAccess.queryForAll();
        ListDrugAdapter adapter = new ListDrugAdapter(getApplicationContext(), userDrugs);
        listView.setAdapter(adapter);
    }

    @OnTextChanged(R.id.editTextSearch)
    @SneakyThrows
    void search(CharSequence charSequence) {

        String searchText = charSequence.toString();

        if (searchText.length() != 0) {
            editTextSearch.setTypeface(null, Typeface.BOLD);
        } else {
            editTextSearch.setTypeface(null, Typeface.NORMAL);
        }

        if(searchText.length() >= 3) {
            Dao<Drug, Integer> drugsDataAccess = getHelper().getDrugsDataAccess();
            Dao<UserDrug, Integer> userDrugsDataAccess = getHelper().getUserDrugsDataAccess();

            QueryBuilder<Drug, Integer> drugsQueryBuilder = drugsDataAccess.queryBuilder();
            QueryBuilder<UserDrug, Integer> userDrugsQueryBuilder = userDrugsDataAccess.queryBuilder();

            drugsQueryBuilder
                    .where()
                    .like(D_NAME, "%"+ searchText +"%");

            userDrugsQueryBuilder.join(drugsQueryBuilder);

            PreparedQuery<UserDrug> prepare = userDrugsQueryBuilder.prepare();

            List<UserDrug> userDrugs = userDrugsDataAccess.query(prepare);


            if (userDrugs.isEmpty()) {
                listView.setAdapter(null);
            } else {
                ListDrugAdapter adapter = new ListDrugAdapter(getApplicationContext(), userDrugs);
                listView.setAdapter(adapter);
            }
        } else if (searchText.length() == 0) {

            Dao<UserDrug, Integer> userDrugsDataAccess = getHelper().getUserDrugsDataAccess();
            List<UserDrug> userDrugs = userDrugsDataAccess.queryForAll();
            ListDrugAdapter adapter = new ListDrugAdapter(getApplicationContext(), userDrugs);
            listView.setAdapter(adapter);
        }
        else {
            listView.setAdapter(null);
        }
    }
}
