package local.tomo.medi.activity.drug.list;

import local.tomo.medi.AdapterFactory;
import local.tomo.medi.activity.SearchActivity;
import local.tomo.medi.ormlite.data.UserDrug;

public class ArchiveUserDrugActivity extends SearchActivity<UserDrugAdapter> implements ButtonsShowable, Action {

    private ArchiveUserDrugAdapterFactory adapterFactory;

    @Override
    public UserDrugAdapter adapterOnCreate() {
        return adapterFactory.createAdapter();
    }

    @Override
    public AdapterFactory provideAdapter() {

        adapterFactory = new ArchiveUserDrugAdapterFactory(ArchiveUserDrugActivity.this, this, this);

        return adapterFactory;
    }

    @Override
    public boolean showArchiveButton() {
        return false;
    }

    @Override
    public void archiveUserDrug(UserDrug userDrug) {

    }
}
