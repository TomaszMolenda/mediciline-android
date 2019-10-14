package local.tomo.medi.activity.drug;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.joda.time.LocalDate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import local.tomo.medi.R;
import local.tomo.medi.activity.DatabaseAccessActivity;
import local.tomo.medi.activity.drug.add.SearchDrugActivity;
import local.tomo.medi.activity.drug.list.ArchiveUserDrugActivity;
import local.tomo.medi.activity.drug.list.OverdueUserDrugActivity;
import local.tomo.medi.activity.drug.list.UserDrugActivity;
import local.tomo.medi.activity.drug.scan.ScanActivity;
import local.tomo.medi.ormlite.DatabaseDataCreator;

public class DrugActivity extends DatabaseAccessActivity {

    @BindView(R.id.buttonScan)
    Button buttonScan;
    @BindView(R.id.buttonAdd)
    Button buttonAdd;
    @BindView(R.id.buttonActive)
    Button buttonActive;
    @BindView(R.id.buttonArchive)
    Button buttonArchive;
    @BindView(R.id.buttonAll)
    Button buttonAll;
    @BindView(R.id.buttonOverdue)
    Button buttonOverdue;

    @BindView(R.id.textActive)
    TextView textViewActive;
    @BindView(R.id.textArchive)
    TextView textViewArchive;
    @BindView(R.id.textOverdue)
    TextView textViewOverdue;
    @BindView(R.id.relativeLayoutOverdue)
    RelativeLayout relativeLayoutOverdue;

    OverdueAnimation overdueAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugs);
        ButterKnife.bind(this);

        DatabaseDataCreator databaseDataCreator = new DatabaseDataCreator(getResources(), getApplicationContext());
        databaseDataCreator.execute();

        overdueAnimation = new OverdueAnimation(DrugActivity.this, relativeLayoutOverdue);

        if (getHelper().getUserDrugQuery().isAnyDrugOverdue(LocalDate.now())) {

            overdueAnimation.start();
        }
    }

    @OnClick(R.id.buttonAdd)
    void add() {
        buttonAdd.setEnabled(false);
        Intent intent = new Intent(this, SearchDrugActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.buttonScan)
    void scan() {
        buttonScan.setEnabled(false);
        Intent intent = new Intent(this, ScanActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.buttonActive)
    void listActive() {
        buttonActive.setEnabled(false);
        Intent intent = new Intent(this, UserDrugActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.buttonArchive)
    void listArchive() {
        buttonArchive.setEnabled(false);
        Intent intent = new Intent(this, ArchiveUserDrugActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.buttonOverdue)
    void listOverdue() {
        buttonArchive.setEnabled(false);
        Intent intent = new Intent(this, OverdueUserDrugActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        buttonScan.setEnabled(true);
        buttonAdd.setEnabled(true);
        buttonActive.setEnabled(true);
        buttonAll.setEnabled(true);
        buttonArchive.setEnabled(true);
        buttonOverdue.setEnabled(true);
        if (getHelper().getUserDrugQuery().isAnyDrugOverdue(LocalDate.now())) {

            overdueAnimation.start();
        } else {
            overdueAnimation.stop();
        }
    }
}
