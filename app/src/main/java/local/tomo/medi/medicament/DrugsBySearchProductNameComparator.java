package local.tomo.medi.medicament;

import java.util.Comparator;

import local.tomo.medi.ormlite.data.Drug;

class DrugsBySearchProductNameComparator implements Comparator<Drug> {

    private final String searchText;

    DrugsBySearchProductNameComparator(String searchText) {
        this.searchText = searchText.toLowerCase();
    }

    @Override
    public int compare(Drug drug1, Drug drug2) {

        String drug1ProductName = drug1.getName().toLowerCase();
        String drug2ProductName = drug2.getName().toLowerCase();

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
