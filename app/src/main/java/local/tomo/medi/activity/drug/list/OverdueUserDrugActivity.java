package local.tomo.medi.activity.drug.list;

import local.tomo.medi.AdapterFactory;
import local.tomo.medi.ormlite.data.UserDrug;

public class OverdueUserDrugActivity extends HideKeyboardActivity<UserDrugAdapter> implements ButtonsShowable, Action {

    private OverdueUserDrugAdapterFactory adapterFactory;

    @Override
    public UserDrugAdapter adapterOnCreate() {
        return adapterFactory.createAdapter();
    }

    @Override
    public AdapterFactory provideAdapter() {

        adapterFactory = new OverdueUserDrugAdapterFactory(OverdueUserDrugActivity.this, this, this);

        return adapterFactory;
    }

    @Override
    public boolean showArchiveButton() {
        return true;
    }

    @Override
    public void archiveUserDrug(UserDrug userDrug) {
        userDrug.markAsArchive();
        getHelper().getUserDrugQuery().save(userDrug);
        UserDrugAdapter adapter = adapterFactory.createAdapter();
        applyAdapter(adapter);
    }
}
