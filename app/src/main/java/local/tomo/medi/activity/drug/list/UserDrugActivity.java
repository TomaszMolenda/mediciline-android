package local.tomo.medi.activity.drug.list;

import local.tomo.medi.AdapterFactory;
import local.tomo.medi.R;
import local.tomo.medi.activity.SearchActivity;
import local.tomo.medi.ormlite.data.UserDrug;

public class UserDrugActivity extends SearchActivity<UserDrugAdapter> implements ButtonsShowable, Action {

    private UserDrugAdapterFactory userDrugAdapterFactory;

    @Override
    public UserDrugAdapter adapterOnCreate() {

        UserDrugAdapter adapter = userDrugAdapterFactory.createAdapter();
        setEmptyListInfo(adapter);

        return adapter;
    }

    @Override
    public AdapterFactory provideAdapter() {

        this.userDrugAdapterFactory = new UserDrugAdapterFactory(UserDrugActivity.this, this, this);

        return userDrugAdapterFactory;
    }

    private void setEmptyListInfo(UserDrugAdapter adapter) {

        if (adapter.isEmpty()) {

            setEmptyListInfo(UserDrugActivity.this.getString(R.string.empty_user_drug_list));
        }
    }

    @Override
    public void archiveUserDrug(UserDrug userDrug) {
        userDrug.markAsArchive();
        getHelper().getUserDrugQuery().save(userDrug);
        UserDrugAdapter adapter = userDrugAdapterFactory.createAdapter();
        setEmptyListInfo(adapter);
        applyAdapter(adapter);
    }

    @Override
    public boolean showArchiveButton() {
        return true;
    }
}
