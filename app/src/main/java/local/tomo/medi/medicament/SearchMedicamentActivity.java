package local.tomo.medi.medicament;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.DbMedicament;
import local.tomo.medi.utills.Utill;
import lombok.SneakyThrows;

public class SearchMedicamentActivity extends DatabaseAccessActivity {

    private List<DbMedicament> drugs = new ArrayList<>();

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
            Dao<DbMedicament, Integer> medicamentDbDao = getHelper().getMedicamentDbDao();
            QueryBuilder<DbMedicament, Integer> queryBuilder = medicamentDbDao.queryBuilder();
            queryBuilder.where().like("productName", "%"+ searchText +"%");
            PreparedQuery<DbMedicament> prepare = queryBuilder.prepare();

            DrugsBySearchProductNameComparator comparator = new DrugsBySearchProductNameComparator(searchText);

            drugs = medicamentDbDao.query(prepare).stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());

            SearchMedicamentAdapter adapter = new SearchMedicamentAdapter(getApplicationContext(), drugs, searchText);

            listView.setAdapter(adapter);
        }
        else {
            listView.setAdapter(null);
            drugs = new ArrayList<>();
        }

        Log.d(Utill.TAG, searchText);
    }
}
