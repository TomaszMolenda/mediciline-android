package local.tomo.medi.medicament;

import android.graphics.Typeface;
import android.os.Bundle;
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
import butterknife.OnTextChanged;
import local.tomo.medi.DatabaseAccessActivity;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.Drug;
import lombok.SneakyThrows;

import static local.tomo.medi.ormlite.data.Drug.D_NAME;

public class SearchMedicamentActivity extends DatabaseAccessActivity {

    @BindView(R.id.editTextSearch)
    EditText editTextSearch;

    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medicaments);
        ButterKnife.bind(this);
    }

    @OnTextChanged(R.id.editTextSearch)
    @SneakyThrows
    void search(CharSequence charSequence) {

        editTextSearch.setTypeface(null, Typeface.BOLD);

        String searchText = charSequence.toString();

        if(searchText.length() >= 3) {
            Dao<Drug, Integer> drugsDataAccess = getHelper().getDrugsDataAccess();
            QueryBuilder<Drug, Integer> queryBuilder = drugsDataAccess.queryBuilder();
            queryBuilder.where().like(D_NAME, "%"+ searchText +"%");
            PreparedQuery<Drug> prepare = queryBuilder.prepare();

            DrugsBySearchProductNameComparator comparator = new DrugsBySearchProductNameComparator(searchText);

            List<Drug> drugs = drugsDataAccess.query(prepare).stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());

            SearchMedicamentAdapter adapter = new SearchMedicamentAdapter(getApplicationContext(), drugs, searchText);

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
}
