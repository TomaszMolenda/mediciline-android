package local.tomo.medi.medicament;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import local.tomo.medi.ormlite.data.DbMedicament;

import static org.assertj.core.api.Assertions.assertThat;

public class DrugsBySearchProductNameComparatorTest {

    private AtomicInteger idGenerator;

    @Before
    public void setUp() {
        idGenerator = new AtomicInteger();
    }

    @Test
    public void shouldCompareTheSameProductNamePrefixAsSearchText() {

        //given
        String searchText = "def";

        DbMedicament drug1 = createDrug("def1");
        DbMedicament drug2 = createDrug("def2");


        DrugsBySearchProductNameComparator drugsBySearchProductNameComparator = new DrugsBySearchProductNameComparator(searchText);

        //when
        int compare = drugsBySearchProductNameComparator.compare(drug1, drug2);

        //then
        assertThat(compare).isLessThan(0);
    }

    @Test
    public void shouldCompareDifferentProductNamePrefixAsSearchText() {

        //given
        String searchText = "def";

        DbMedicament drug1 = createDrug("ant");
        DbMedicament drug2 = createDrug("defg");


        DrugsBySearchProductNameComparator drugsBySearchProductNameComparator = new DrugsBySearchProductNameComparator(searchText);

        //when
        int compare = drugsBySearchProductNameComparator.compare(drug1, drug2);

        //then
        assertThat(compare).isGreaterThan(0);
    }

    @Test
    public void shouldCompareTheSameProductName() {

        //given
        String searchText = "def";

        DbMedicament drug1 = createDrug("abc");
        DbMedicament drug2 = createDrug("abc");


        DrugsBySearchProductNameComparator drugsBySearchProductNameComparator = new DrugsBySearchProductNameComparator(searchText);

        //when
        int compare = drugsBySearchProductNameComparator.compare(drug1, drug2);

        //then
        assertThat(compare).isEqualTo(0);
    }

    @Test
    public void shouldSortSearchTestFirst() {

        //given
        String searchText = "kol";

        DbMedicament drug1 = createDrug("Mukolina");
        DbMedicament drug2 = createDrug("Kolpexin \"O\"");
        DbMedicament drug3 = createDrug("Kolpexin \"T\"");
        DbMedicament drug4 = createDrug("Oekolp® forte");
        DbMedicament drug5 = createDrug("Syrop z sulfogwajakolem Aflofarm");
        DbMedicament drug6 = createDrug("Syrop z sulfogwajakolem Gemi");
        DbMedicament drug7 = createDrug("Syrop z sulfogwajakolem Galena");
        DbMedicament drug8 = createDrug("Ortokol");
        DbMedicament drug9 = createDrug("Oekolp®");
        DbMedicament drug10 = createDrug("Kolzym");
        DbMedicament drug11 = createDrug("Zielnik Świata Ruszczyk kolczasty");
        DbMedicament drug12 = createDrug("Bimakolan");
        DbMedicament drug13 = createDrug("Bimakolan");
        DbMedicament drug14 = createDrug("Alpikol");
        DbMedicament drug15 = createDrug("Srebro koloidalne argentum");
        DbMedicament drug16 = createDrug("Ruskolina");
        DbMedicament drug17 = createDrug("Ruskolina Plus");
        DbMedicament drug18 = createDrug("Sterokolin");

        ArrayList<DbMedicament> drugs = Lists.newArrayList(
                drug1,
                drug2,
                drug3,
                drug4,
                drug5,
                drug6,
                drug7,
                drug8,
                drug9,
                drug10,
                drug11,
                drug12,
                drug13,
                drug14,
                drug15,
                drug16,
                drug17,
                drug18
                );

        DrugsBySearchProductNameComparator drugsBySearchProductNameComparator = new DrugsBySearchProductNameComparator(searchText);

        //when
        List<DbMedicament> sortedDrugs = drugs.stream()
                .sorted(drugsBySearchProductNameComparator)
                .collect(Collectors.toList());

        //then
        assertThat(sortedDrugs).containsExactly(
                drug2,
                drug3,
                drug10,
                drug14,
                drug12,
                drug13,
                drug1,
                drug9,
                drug4,
                drug8,
                drug16,
                drug17,
                drug15,
                drug18,
                drug5,
                drug7,
                drug6,
                drug11
        );
    }

    private DbMedicament createDrug(String productName) {

        DbMedicament drug = new DbMedicament();
        drug.setProductName(productName);
        drug.setPackageID(idGenerator.getAndIncrement());

        return drug;
    }
}
