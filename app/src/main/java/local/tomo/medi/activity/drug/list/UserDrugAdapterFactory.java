package local.tomo.medi.activity.drug.list;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;
import java.util.stream.Collectors;

import local.tomo.medi.AdapterFactory;
import local.tomo.medi.activity.drug.BySearchProductNameComparator;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.UserDrugQuery;
import local.tomo.medi.ormlite.data.UserDrug;

class UserDrugAdapterFactory implements AdapterFactory<UserDrugAdapter> {

    private final DatabaseHelper databaseHelper;
    private final Context context;
    private final ButtonsShowable buttonsShowable;
    private final Action action;

    UserDrugAdapterFactory(Context context, ButtonsShowable buttonsShowable, Action action) {

        this.databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        this.context = context;
        this.buttonsShowable = buttonsShowable;
        this.action = action;
    }

    UserDrugAdapter createAdapter() {

        return createAdapter("");
    }

    @Override
    public UserDrugAdapter createAdapter(String searchText) {

        BySearchProductNameComparator<UserDrug> comparator = new BySearchProductNameComparator<>(searchText, userDrug -> userDrug.getDrug().getName());

        UserDrugQuery userDrugQuery = databaseHelper.getUserDrugQuery();

        if (searchText.length() == 0) {

            List<UserDrug> allUserDrugs = userDrugQuery.listAllActive().stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());

            return new UserDrugAdapter(context, allUserDrugs, action, buttonsShowable);
        }

        List<UserDrug> userDrugs = userDrugQuery.listActiveByName(searchText).stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        if (userDrugs.isEmpty()) {

            return null;
        }

        return new UserDrugAdapter(context, userDrugs, action, buttonsShowable);
    }

    @Override
    public void closeDatabaseConnection() {
        OpenHelperManager.releaseHelper();
    }
}
