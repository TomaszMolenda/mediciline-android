package local.tomo.medi.medicament;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.DbMedicament;
import lombok.SneakyThrows;

public class MedicamentDbDetailsActivity extends AppCompatActivity {

    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewActiveSubstance)
    TextView textViewActiveSubstance;
    @BindView(R.id.textViewForm)
    TextView textViewForm;
    @BindView(R.id.textViewDosage)
    TextView textViewDosage;
    @BindView(R.id.textViewPack)
    TextView textViewPack;
    @BindView(R.id.textViewProducer)
    TextView textViewProducer;
    @BindView(R.id.textViewPrescription)
    TextView textViewPrescription;
    @BindView(R.id.textViewProductType)
    TextView textViewProductType;
    @BindView(R.id.textViewDrivingInfo)
    TextView textViewDrivingInfo;
    @BindView(R.id.textViewLactationInfo)
    TextView textViewLactationInfo;
    @BindView(R.id.textViewTrimestr1Info)
    TextView textViewTrimestr1Info;
    @BindView(R.id.textViewTrimestr2Info)
    TextView textViewTrimestr2Info;
    @BindView(R.id.textViewTrimestr3Info)
    TextView textViewTrimestr3Info;
    @BindView(R.id.textViewAlcoholInfo)
    TextView textViewAlcoholInfo;
    @BindView(R.id.textViewComposition)
    TextView textViewComposition;
    @BindView(R.id.textViewEffects)
    TextView textViewEffects;
    @BindView(R.id.textViewIndications)
    TextView textViewIndications;
    @BindView(R.id.textViewContraindications)
    TextView textViewContraindications;
    @BindView(R.id.textViewPrecaution)
    TextView textViewPrecaution;
    @BindView(R.id.textViewPregnancy)
    TextView textViewPregnancy;
    @BindView(R.id.textViewSideeffects)
    TextView textViewSideeffects;
    @BindView(R.id.textViewInteractions)
    TextView textViewInteractions;
    @BindView(R.id.textViewDosageInfo)
    TextView textViewDosageInfo;
    @BindView(R.id.textViewRemark)
    TextView textViewRemark;

    private DatabaseHelper databaseHelper;

    @Override
    @SneakyThrows
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament_db_details);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        int packageID = bundle.getInt("packageID");

        Dao<DbMedicament, Integer> medicamentDbDao = getHelper().getMedicamentDbDao();
        DbMedicament dbMedicament = medicamentDbDao.queryForId(packageID);


        textViewName.setText(dbMedicament.getProductName());
        textViewActiveSubstance.setText(dbMedicament.getActiveSubstance());
        textViewForm.setText(dbMedicament.getForm());
        textViewDosage.setText(dbMedicament.getDosage());
        textViewPack.setText(dbMedicament.getPack());
        textViewProducer.setText(dbMedicament.getProducer());
        textViewPrescription.setText(dbMedicament.getPrescriptionName());
        textViewProductType.setText(dbMedicament.getProductTypeName());
        textViewDrivingInfo.setText(dbMedicament.getDrivingInfo());
        textViewLactationInfo.setText(dbMedicament.getLactatioInfo());
        textViewTrimestr1Info.setText(dbMedicament.getTrimester1Info());
        textViewTrimestr2Info.setText(dbMedicament.getTrimester2Info());
        textViewTrimestr3Info.setText(dbMedicament.getTrimester3Info());
        textViewAlcoholInfo.setText(dbMedicament.getIsAlcoInfo());
        textViewComposition.setText(dbMedicament.getMedicamentAdditional().getComposition());
        textViewEffects.setText(dbMedicament.getMedicamentAdditional().getEffects());
        textViewIndications.setText(dbMedicament.getMedicamentAdditional().getIndications());
        textViewContraindications.setText(dbMedicament.getMedicamentAdditional().getContraindications());
        textViewPrecaution.setText(dbMedicament.getMedicamentAdditional().getPrecaution());
        textViewPregnancy.setText(dbMedicament.getMedicamentAdditional().getPregnancy());
        textViewSideeffects.setText(dbMedicament.getMedicamentAdditional().getSideeffects());
        textViewInteractions.setText(dbMedicament.getMedicamentAdditional().getInteractions());
        textViewDosageInfo.setText(dbMedicament.getMedicamentAdditional().getDosage());
        textViewRemark.setText(dbMedicament.getMedicamentAdditional().getRemark());

    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
