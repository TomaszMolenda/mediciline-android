package local.tomo.medi.activity.drug.add;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.Optional;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import local.tomo.medi.R;
import local.tomo.medi.activity.DatabaseAccessActivity;
import local.tomo.medi.activity.drug.DrugActivity;
import local.tomo.medi.activity.drug.Months;
import local.tomo.medi.ormlite.data.Drug;
import local.tomo.medi.ormlite.data.UserDrug;
import lombok.SneakyThrows;

import static local.tomo.medi.ormlite.data.Drug.D_ID;

public class SetOverdueActivity extends DatabaseAccessActivity {

    private Integer drugId;
    private LocalDate overdueDate;

    @BindView(R.id.buttonMonthUp)
    Button buttonMonthUp;
    @BindView(R.id.buttonYearUp)
    Button buttonYearUp;
    @BindView(R.id.buttonMonthDown)
    Button buttonMonthDown;
    @BindView(R.id.buttonYearDown)
    Button buttonYearDown;
    @BindView(R.id.buttonSave)
    Button buttonSave;

    @BindView(R.id.textViewMonth)
    TextView textViewMonth;
    @BindView(R.id.textViewYear)
    TextView textViewYear;

    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewProducer)
    TextView textViewProducer;
    @BindView(R.id.textViewPackage)
    TextView textViewPackage;
    @BindView(R.id.textViewForm)
    TextView textViewForm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_overdue);
        ButterKnife.bind(this);

        overdueDate = LocalDate.now();

        prepareDrugId();

        Drug drug = getDrug();

        renderDate();

        textViewName.setText(drug.getName());
        textViewProducer.setText(drug.getProducer());
        textViewPackage.setText(drug.getPack());
        textViewForm.setText(drug.getForm());
    }

    private void renderDate() {
        renderMonth();
        renderYear();
    }

    private void renderMonth() {

        textViewMonth.setText(getMonth());
    }

    private void renderYear() {

        textViewYear.setText(getYear());
    }

    @SneakyThrows
    private Drug getDrug() {
        return getHelper().getDrugQuery()
                .getById(drugId);
    }

    private String getMonth() {

        Resources resources = getResources();

        return resources.getString(resources.getIdentifier(Months.valueOf(overdueDate.getMonthOfYear()).name(), "string", getPackageName()));
    }

    private String getYear() {

        return Integer.toString(overdueDate.getYear());
    }

    private void prepareDrugId() {

        Optional<Integer> drugId = findDrugId();

        if (drugId.isPresent()) {

            this.drugId = drugId.get();

        } else {

            finish();
        }
    }

    private Optional<Integer> findDrugId() {

        return Optional.ofNullable(getIntent().getExtras())
                .map(bundle -> bundle.getInt(D_ID));
    }

    @OnClick(R.id.buttonMonthUp)
    void monthUp() {

        overdueDate = overdueDate.plusMonths(1);
        renderDate();
    }

    @OnClick(R.id.buttonMonthDown)
    void monthDown() {

        overdueDate = overdueDate.minusMonths(1);
        renderDate();
    }

    @OnClick(R.id.buttonYearUp)
    void yearUp() {

        overdueDate = overdueDate.plusYears(1);
        renderDate();
    }

    @OnClick(R.id.buttonYearDown)
    void yearDown() {

        overdueDate = overdueDate.minusYears(1);
        renderDate();
    }

    @SneakyThrows
    @OnClick(R.id.buttonSave)
    void save() {

        Drug drug = getHelper().getDrugQuery().getById(drugId);
        Date date = overdueDate.toDate();

        UserDrug userDrug = new UserDrug(drug, date);

        getHelper().getUserDrugQuery().save(userDrug);

        startActivity(new Intent(getApplicationContext(), DrugActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), DrugActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
