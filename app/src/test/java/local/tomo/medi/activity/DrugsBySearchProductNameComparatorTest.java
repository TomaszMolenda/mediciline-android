package local.tomo.medi.activity;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import local.tomo.medi.ormlite.data.Drug;

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

        Drug drug1 = createDrug("def1");
        Drug drug2 = createDrug("def2");


        DrugsBySearchProductNameComparator drugsBySearchProductNameComparator = new DrugsBySearchProductNameComparator(searchText);

        //when
        int compare = drugsBySearchProductNameComparator.compare(drug1, drug2);

        //then
        Assertions.assertThat(compare).isLessThan(0);
    }

    @Test
    public void shouldCompareDifferentProductNamePrefixAsSearchText() {

        //given
        String searchText = "def";

        Drug drug1 = createDrug("ant");
        Drug drug2 = createDrug("defg");


        DrugsBySearchProductNameComparator drugsBySearchProductNameComparator = new DrugsBySearchProductNameComparator(searchText);

        //when
        int compare = drugsBySearchProductNameComparator.compare(drug1, drug2);

        //then
        Assertions.assertThat(compare).isGreaterThan(0);
    }

    @Test
    public void shouldCompareTheSameProductName() {

        //given
        String searchText = "def";

        Drug drug1 = createDrug("abc");
        Drug drug2 = createDrug("abc");


        DrugsBySearchProductNameComparator drugsBySearchProductNameComparator = new DrugsBySearchProductNameComparator(searchText);

        //when
        int compare = drugsBySearchProductNameComparator.compare(drug1, drug2);

        //then
        Assertions.assertThat(compare).isEqualTo(0);
    }

    @Test
    public void shouldSortSearchTestFirst() {

        //given
        String searchText = "kol";

        Drug drug1 = createDrug("Mukolina");
        Drug drug2 = createDrug("Kolpexin \"O\"");
        Drug drug3 = createDrug("Kolpexin \"T\"");
        Drug drug4 = createDrug("Oekolp® forte");
        Drug drug5 = createDrug("Syrop z sulfogwajakolem Aflofarm");
        Drug drug6 = createDrug("Syrop z sulfogwajakolem Gemi");
        Drug drug7 = createDrug("Syrop z sulfogwajakolem Galena");
        Drug drug8 = createDrug("Ortokol");
        Drug drug9 = createDrug("Oekolp®");
        Drug drug10 = createDrug("Kolzym");
        Drug drug11 = createDrug("Zielnik Świata Ruszczyk kolczasty");
        Drug drug12 = createDrug("Bimakolan");
        Drug drug13 = createDrug("Bimakolan");
        Drug drug14 = createDrug("Alpikol");
        Drug drug15 = createDrug("Srebro koloidalne argentum");
        Drug drug16 = createDrug("Ruskolina");
        Drug drug17 = createDrug("Ruskolina Plus");
        Drug drug18 = createDrug("Sterokolin");

        ArrayList<Drug> drugs = Lists.newArrayList(
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
        List<Drug> sortedDrugs = drugs.stream()
                .sorted(drugsBySearchProductNameComparator)
                .collect(Collectors.toList());

        //then
        Assertions.assertThat(sortedDrugs).containsExactly(
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

    private Drug createDrug(String productName) {

        Drug drug = new Drug();
        drug.setName(productName);
        drug.setPackageID(idGenerator.getAndIncrement());

        return drug;
    }
}
