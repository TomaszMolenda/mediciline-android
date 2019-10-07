package local.tomo.medi.medicament;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.stmt.QueryBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.Medicament;
import lombok.SneakyThrows;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugs);
        ButterKnife.bind(this);
        prepareButtonsCount();
    }

    @OnClick(R.id.buttonAdd)
    void add() {
        buttonAdd.setEnabled(false);
        Intent intent = new Intent(this, SearchMedicamentActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.buttonScan)
    void scan() {
        buttonScan.setEnabled(false);
        Intent intent = new Intent(this, AddMedicamentActivity.class);
        Bundle b = new Bundle();
        b.putBoolean("scan", true);
        intent.putExtras(b);
        this.startActivity(intent);
    }

    @OnClick(R.id.buttonActive)
    void showActive() {
        buttonActive.setEnabled(false);
        Intent intent = new Intent(this, MedicamentsActivity.class);
        intent.putExtra("medicaments", MedicamentsActivity.ACTIVE_MEDICAMENTS);
        this.startActivityForResult(intent, 1);
    }

    @OnClick(R.id.buttonArchive)
    void showArchive() {
        buttonArchive.setEnabled(false);
        Intent intent = new Intent(this, MedicamentsActivity.class);
        intent.putExtra("medicaments", MedicamentsActivity.ARCHIVE_MEDICAMENTS);
        this.startActivity(intent);
    }

    @OnClick(R.id.buttonAll)
    void showAllInDatabase() {
        buttonAll.setEnabled(false);
        Intent intent = new Intent(this, MedicamentsDbActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.buttonOverdue)
    void showOverdue() {
        buttonOverdue.setEnabled(false);
        Intent intent = new Intent(this, MedicamentsActivity.class);
        intent.putExtra("medicaments", MedicamentsActivity.OUT_OF_DATE_MEDICAMENTS);
        this.startActivity(intent);
    }

    @SneakyThrows
    private void prepareButtonsCount() {
        QueryBuilder<Medicament, Integer> queryBuilder = getHelper().getMedicamentDao().queryBuilder();
        queryBuilder.where().eq("archive", false);
        textViewActive.setText(getString(R.string.text_view_count_active_medicament, queryBuilder.countOf()));
        queryBuilder.reset();
        queryBuilder.where().eq("archive", true);
        textViewArchive.setText(getString(R.string.text_view_count_archive_medicament, queryBuilder.countOf()));
        queryBuilder.reset();
        queryBuilder.where().eq("overdue", true).and().eq("archive", false);
        textViewOverdue.setText(getString(R.string.text_view_count_overdue_medicament, queryBuilder.countOf()));
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
        prepareButtonsCount();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        prepareButtonsCount();
    }

}
