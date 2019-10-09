package local.tomo.medi.activity.drug;

import java.util.Comparator;
import java.util.function.Function;

public class BySearchProductNameComparator<E> implements Comparator<E> {

    private final String searchText;
    private final Function<E, String> productNameProvider;

    public BySearchProductNameComparator(String searchText, Function<E, String> productNameProvider) {
        this.searchText = searchText.toLowerCase();
        this.productNameProvider = productNameProvider;
    }

    @Override
    public int compare(E object1, E object2) {

        String drug1ProductName = productNameProvider.apply(object1).toLowerCase();
        String drug2ProductName = productNameProvider.apply(object2).toLowerCase();

        if (drug1ProductName.startsWith(searchText) && drug2ProductName.startsWith(searchText)) {

            return drug1ProductName.compareTo(drug2ProductName);
        }

        if (drug1ProductName.startsWith(searchText)) {

            return -1;
        }

        if (drug2ProductName.startsWith(searchText)) {

            return 1;
        }

        return drug1ProductName.compareTo(drug2ProductName);
    }
}
