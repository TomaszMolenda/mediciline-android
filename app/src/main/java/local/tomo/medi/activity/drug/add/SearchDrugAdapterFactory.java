package local.tomo.medi.activity.drug.add;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;
import java.util.stream.Collectors;

import local.tomo.medi.activity.drug.BySearchProductNameComparator;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Drug;

class SearchDrugAdapterFactory {

    private final DatabaseHelper databaseHelper;
    private final Context context;

    SearchDrugAdapterFactory(Context context) {
        this.databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        this.context = context;
    }

    SearchDrugAdapter createAdapter(String searchText) {

        if (searchText.length() < 3) {

            return null;
        }

        BySearchProductNameComparator<Drug> comparator = new BySearchProductNameComparator<>(searchText, Drug::getName);

        List<Drug> drugs = databaseHelper.getDrugQuery()
                .listByName(searchText)
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        if (drugs.isEmpty()) {

            return null;
        }

        return new SearchDrugAdapter(context, drugs, searchText);
    }

    void closeDatabaseConnection() {
        OpenHelperManager.releaseHelper();
    }
}
