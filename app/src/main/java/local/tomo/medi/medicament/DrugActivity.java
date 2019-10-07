package local.tomo.medi.medicament;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import local.tomo.medi.R;

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
    }

    @OnClick(R.id.buttonAdd)
    void add() {
        buttonAdd.setEnabled(false);
        Intent intent = new Intent(this, SearchMedicamentActivity.class);
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
    }
}
