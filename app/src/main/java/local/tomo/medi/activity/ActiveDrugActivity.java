package local.tomo.medi.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.j256.ormlite.dao.Dao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.DatabaseAccessActivity;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.UserDrug;
import lombok.SneakyThrows;

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

        Dao<UserDrug, Integer> drugsDataAccess = getHelper().getUserDrugsDataAccess();

        List<UserDrug> userDrugs = drugsDataAccess.queryForAll();

        ListDrugAdapter adapter = new ListDrugAdapter(getApplicationContext(), userDrugs);

        listView.setAdapter(adapter);
    }
}
