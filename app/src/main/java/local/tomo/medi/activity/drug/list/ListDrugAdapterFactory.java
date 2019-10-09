package local.tomo.medi.activity.drug.list;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;
import java.util.stream.Collectors;

import local.tomo.medi.activity.drug.BySearchProductNameComparator;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.UserDrugQuery;
import local.tomo.medi.ormlite.data.UserDrug;

class ListDrugAdapterFactory {

    private final DatabaseHelper databaseHelper;
    private final Context context;

    ListDrugAdapterFactory(Context context) {

        this.databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        this.context = context;
    }

    ListDrugAdapter createAdapter() {

        return createAdapter("");
    }

    ListDrugAdapter createAdapter(String searchText) {

        BySearchProductNameComparator<UserDrug> comparator = new BySearchProductNameComparator<>(searchText, userDrug -> userDrug.getDrug().getName());

        UserDrugQuery userDrugQuery = databaseHelper.getUserDrugQuery();

        if (searchText.length() == 0) {

            List<UserDrug> allUserDrugs = userDrugQuery.listAll().stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());

            return new ListDrugAdapter(context, allUserDrugs);
        }

        List<UserDrug> userDrugs = userDrugQuery.listByName(searchText).stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        if (userDrugs.isEmpty()) {

            return null;
        }

        return new ListDrugAdapter(context, userDrugs);
    }

    void closeDatabaseConnection() {
        OpenHelperManager.releaseHelper();
    }
}
