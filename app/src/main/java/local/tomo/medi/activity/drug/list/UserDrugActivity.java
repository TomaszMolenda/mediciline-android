package local.tomo.medi.activity.drug.list;

import local.tomo.medi.AdapterFactory;
import local.tomo.medi.activity.SearchActivity;

public class UserDrugActivity extends SearchActivity<UserDrugAdapter> {

    private UserDrugAdapterFactory userDrugAdapterFactory;

    @Override
    public UserDrugAdapter adapterOnCreate() {

        return userDrugAdapterFactory.createAdapter();
    }

    @Override
    public AdapterFactory provideAdapter() {

        this.userDrugAdapterFactory = new UserDrugAdapterFactory(getApplicationContext());

        return new UserDrugAdapterFactory(getApplicationContext());
    }
}
