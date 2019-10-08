package local.tomo.medi.activity.drug.add;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import local.tomo.medi.activity.DatabaseAccessActivity;
import local.tomo.medi.R;
import local.tomo.medi.activity.drug.DrugsBySearchProductNameComparator;
import local.tomo.medi.ormlite.data.Drug;
import lombok.SneakyThrows;

import static local.tomo.medi.ormlite.data.Drug.D_ID;
import static local.tomo.medi.ormlite.data.Drug.D_NAME;

public class SearchDrugActivity extends DatabaseAccessActivity {

    @BindView(R.id.editTextSearch)
    EditText editTextSearch;

    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_drugs);
        ButterKnife.bind(this);
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
            QueryBuilder<Drug, Integer> queryBuilder = drugsDataAccess.queryBuilder();
            queryBuilder.where().like(D_NAME, "%"+ searchText +"%");
            PreparedQuery<Drug> prepare = queryBuilder.prepare();

            DrugsBySearchProductNameComparator comparator = new DrugsBySearchProductNameComparator(searchText);

            List<Drug> drugs = drugsDataAccess.query(prepare).stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());

            SearchDrugAdapter adapter = new SearchDrugAdapter(getApplicationContext(), drugs, searchText);

            listView.setAdapter(adapter);
        }
        else {
            listView.setAdapter(null);
        }
    }

    @OnClick(R.id.imageButtonBack)
    void back() {
        finish();
    }

    @OnClick(R.id.imageButtonClear)
    void clearSearchText() {
        editTextSearch.setText("");
        listView.setAdapter(null);
    }

    @OnItemClick(R.id.listView)
    void chooseDrug(AdapterView<?> parent, View view, int position, long id) {

        Drug drug = (Drug) parent.getItemAtPosition(position);

        Intent intent = new Intent(this, SetOverdueActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt(D_ID, drug.getId());

        intent.putExtras(bundle);
        startActivity(intent);
    }


}
