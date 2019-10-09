package local.tomo.medi.activity.drug.add;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import butterknife.OnItemClick;
import local.tomo.medi.AdapterFactory;
import local.tomo.medi.R;
import local.tomo.medi.activity.SearchActivity;
import local.tomo.medi.ormlite.data.Drug;

import static local.tomo.medi.ormlite.data.Drug.D_ID;

public class SearchDrugActivity extends SearchActivity<SearchDrugAdapter> {

    @Override
    public AdapterFactory provideAdapter() {
        return new SearchDrugAdapterFactory(getApplicationContext());
    }

    @Override
    public SearchDrugAdapter adapterOnCreate() {
        return null;
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
