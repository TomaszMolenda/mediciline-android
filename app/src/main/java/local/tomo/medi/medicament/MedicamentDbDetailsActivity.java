package local.tomo.medi.medicament;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.DbMedicament;

public class MedicamentDbDetailsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament_db_details);

        Bundle bundle = getIntent().getExtras();
        int packageID = bundle.getInt("packageID");

        TextView textViewName = (TextView) findViewById(R.id.textViewName);
        TextView textViewActiveSubstance = (TextView) findViewById(R.id.textViewActiveSubstance);
        TextView textViewForm = (TextView) findViewById(R.id.textViewForm);
        TextView textViewDosage = (TextView) findViewById(R.id.textViewDosage);
        TextView textViewPack = (TextView) findViewById(R.id.textViewPack);
        TextView textViewProducer = (TextView) findViewById(R.id.textViewProducer);
        TextView textViewPrescription = (TextView) findViewById(R.id.textViewPrescription);
        TextView textViewProductType = (TextView) findViewById(R.id.textViewProductType);
        TextView textViewDrivingInfo = (TextView) findViewById(R.id.textViewDrivingInfo);
        TextView textViewLactationInfo = (TextView) findViewById(R.id.textViewLactationInfo);
        TextView textViewTrimestr1Info = (TextView) findViewById(R.id.textViewTrimestr1Info);
        TextView textViewTrimestr2Info = (TextView) findViewById(R.id.textViewTrimestr2Info);
        TextView textViewTrimestr3Info = (TextView) findViewById(R.id.textViewTrimestr3Info);
        TextView textViewAlcoholInfo = (TextView) findViewById(R.id.textViewAlcoholInfo);
        TextView textViewComposition = (TextView) findViewById(R.id.textViewComposition);
        TextView textViewEffects = (TextView) findViewById(R.id.textViewEffects);
        TextView textViewIndications = (TextView) findViewById(R.id.textViewIndications);
        TextView textViewContraindications = (TextView) findViewById(R.id.textViewContraindications);
        TextView textViewPrecaution = (TextView) findViewById(R.id.textViewPrecaution);
        TextView textViewPregnancy = (TextView) findViewById(R.id.textViewPregnancy);
        TextView textViewSideeffects = (TextView) findViewById(R.id.textViewSideeffects);
        TextView textViewInteractions = (TextView) findViewById(R.id.textViewInteractions);
        TextView textViewDosageInfo = (TextView) findViewById(R.id.textViewDosageInfo);
        TextView textViewRemark = (TextView) findViewById(R.id.textViewRemark);







        DbMedicament dbMedicament = null;

        try {
            Dao<DbMedicament, Integer> medicamentDbDao = getHelper().getMedicamentDbDao();
            dbMedicament = medicamentDbDao.queryForId(packageID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
