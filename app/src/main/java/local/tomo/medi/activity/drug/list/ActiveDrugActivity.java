package local.tomo.medi.activity.drug.list;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import local.tomo.medi.R;
import lombok.SneakyThrows;

public class ActiveDrugActivity extends AppCompatActivity {

    @BindView(R.id.editTextSearch)
    EditText editTextSearch;

    @BindView(R.id.listView)
    ListView listView;

    private ListDrugAdapterFactory listDrugAdapterFactory;

    @SneakyThrows
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_drugs);
        ButterKnife.bind(this);
        listDrugAdapterFactory = new ListDrugAdapterFactory(getApplicationContext());

        ListDrugAdapter adapter = listDrugAdapterFactory.createAdapter();
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

        ListDrugAdapter adapter = listDrugAdapterFactory.createAdapter(searchText);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listDrugAdapterFactory.closeDatabaseConnection();
    }
}
