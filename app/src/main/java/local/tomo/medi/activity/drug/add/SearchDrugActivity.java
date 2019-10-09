package local.tomo.medi.activity.drug.add;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.Drug;
import lombok.SneakyThrows;

import static local.tomo.medi.ormlite.data.Drug.D_ID;

public class SearchDrugActivity extends AppCompatActivity {

    @BindView(R.id.editTextSearch)
    EditText editTextSearch;

    @BindView(R.id.listView)
    ListView listView;

    private SearchDrugAdapterFactory searchDrugAdapterFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_drugs);
        ButterKnife.bind(this);

        searchDrugAdapterFactory = new SearchDrugAdapterFactory(getApplicationContext());
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

        SearchDrugAdapter adapter = searchDrugAdapterFactory.createAdapter(searchText);

        listView.setAdapter(adapter);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchDrugAdapterFactory.closeDatabaseConnection();
    }
}
