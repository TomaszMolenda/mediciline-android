package local.tomo.medi.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import local.tomo.medi.AdapterFactory;
import local.tomo.medi.R;
import lombok.SneakyThrows;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public abstract class SearchActivity<E extends ListAdapter> extends DatabaseAccessActivity {

    private AdapterFactory<E> adapterFactory;

    @BindView(R.id.editTextSearch)
    EditText editTextSearch;

    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.textViewEmptyList)
    TextView textViewEmptyList;

    public abstract E adapterOnCreate();
    public abstract AdapterFactory provideAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_drugs);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        this.adapterFactory = provideAdapter();
        E adapter = adapterOnCreate();
        applyAdapter(adapter);
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

        E adapter = adapterFactory.createAdapter(searchText);
        applyAdapter(adapter);
    }

    @OnClick(R.id.imageButtonClear)
    void clearSearchText() {
        String searchText = EMPTY;
        editTextSearch.setText(searchText);
        E adapter = adapterFactory.createAdapter(searchText);
        applyAdapter(adapter);
    }

    @OnClick(R.id.imageButtonBack)
    void back() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapterFactory.closeDatabaseConnection();
    }

    protected void applyAdapter(E adapter) {

        listView.setAdapter(adapter);
    }

    public void setEmptyListInfo(String info) {
        textViewEmptyList.setText(info);
    }
}
