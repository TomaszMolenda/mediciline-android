package local.tomo.medi.activity.drug.list;

import java.util.function.Consumer;

import local.tomo.medi.AdapterFactory;
import local.tomo.medi.R;
import local.tomo.medi.activity.SearchActivity;
import local.tomo.medi.ormlite.data.UserDrug;

public class UserDrugActivity extends SearchActivity<UserDrugAdapter> {

    private UserDrugAdapterFactory userDrugAdapterFactory;
    private Consumer<UserDrug> userDrugArchiver = this::archive;

    @Override
    public UserDrugAdapter adapterOnCreate() {

        UserDrugAdapter adapter = userDrugAdapterFactory.createAdapter();
        setEmptyListInfo(adapter);

        return adapter;
    }

    @Override
    public AdapterFactory provideAdapter() {

        this.userDrugAdapterFactory = new UserDrugAdapterFactory(UserDrugActivity.this, userDrugArchiver);

        return new UserDrugAdapterFactory(getApplicationContext(), userDrugArchiver);
    }

    void archive(UserDrug userDrug) {

        userDrug.markAsArchive();
        getHelper().getUserDrugQuery().save(userDrug);
        UserDrugAdapter adapter = userDrugAdapterFactory.createAdapter();
        setEmptyListInfo(adapter);
        applyAdapter(adapter);
    }

    private void setEmptyListInfo(UserDrugAdapter adapter) {

        if (adapter.isEmpty()) {

            setEmptyListInfo(UserDrugActivity.this.getString(R.string.empty_user_drug_list));
        }
    }
}
