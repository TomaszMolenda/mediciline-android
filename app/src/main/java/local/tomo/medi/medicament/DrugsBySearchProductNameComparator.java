package local.tomo.medi.medicament;

import java.util.Comparator;

import local.tomo.medi.ormlite.data.DbMedicament;

class DrugsBySearchProductNameComparator implements Comparator<DbMedicament> {

    private final String searchText;

    DrugsBySearchProductNameComparator(String searchText) {
        this.searchText = searchText.toLowerCase();
    }

    @Override
    public int compare(DbMedicament drug1, DbMedicament drug2) {

        String drug1ProductName = drug1.getProductName().toLowerCase();
        String drug2ProductName = drug2.getProductName().toLowerCase();

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
