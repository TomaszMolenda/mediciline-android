package local.tomo.medi.activity.drug.list;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.google.common.collect.Lists;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import local.tomo.medi.Logger;
import local.tomo.medi.R;
import local.tomo.medi.activity.AdapterSetStrategy;
import local.tomo.medi.activity.DatabaseAccessActivity;
import local.tomo.medi.ormlite.data.UserDrug;
import lombok.SneakyThrows;

public class ActiveDrugActivity extends DatabaseAccessActivity {

    private List<AdapterSetStrategy> adapterSetStrategies;

    @BindView(R.id.editTextSearch)
    EditText editTextSearch;

    @BindView(R.id.listView)
    ListView listView;

    public ActiveDrugActivity() {
        this.adapterSetStrategies = Lists.newArrayList(

        );
    }


    @SneakyThrows
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_drugs);
        ButterKnife.bind(this);

        List<UserDrug> userDrugs = getHelper().getUserDrugQuery().listAll();
        ListDrugAdapter adapter = new ListDrugAdapter(getApplicationContext(), userDrugs);
        listView.setAdapter(adapter);
    }

    @OnTextChanged(R.id.editTextSearch)
    @SneakyThrows
    void search(CharSequence charSequence) {

        Logger.logger(adapterSetStrategies.size() + "");

        String searchText = charSequence.toString();

        if (searchText.length() != 0) {
            editTextSearch.setTypeface(null, Typeface.BOLD);
        } else {
            editTextSearch.setTypeface(null, Typeface.NORMAL);
        }

        ListDrugAdapter listDrugAdapter = adapterSetStrategies.stream()
                .filter(adapterSetStrategy -> adapterSetStrategy.isApplicable(searchText))
                .findFirst()
                .map(AdapterSetStrategy::createAdapter)
                .orElseThrow(IllegalArgumentException::new);

        listView.setAdapter(listDrugAdapter);

        if(searchText.length() >= 3) {

            List<UserDrug> userDrugs = getHelper().getUserDrugQuery().listByName(searchText);


            if (userDrugs.isEmpty()) {
                listView.setAdapter(null);
            } else {
                ListDrugAdapter adapter = new ListDrugAdapter(getApplicationContext(), userDrugs);
                listView.setAdapter(adapter);
            }
        } else if (searchText.length() == 0) {

            List<UserDrug> userDrugs = getHelper().getUserDrugQuery().listAll();

            ListDrugAdapter adapter = new ListDrugAdapter(getApplicationContext(), userDrugs);
            listView.setAdapter(adapter);
        }
        else {
            listView.setAdapter(null);
        }
    }
}
