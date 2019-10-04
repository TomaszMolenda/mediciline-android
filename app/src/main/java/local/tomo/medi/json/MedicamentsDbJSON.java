package local.tomo.medi.json;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.InputStream;
import java.sql.SQLException;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.DbMedicament;
import local.tomo.medi.ormlite.data.MedicamentAdditional;
import lombok.SneakyThrows;

import static com.fasterxml.jackson.core.JsonToken.END_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.END_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.START_ARRAY;


public class MedicamentsDbJSON {

    public static int count = 12922;
    private Context context;
    private DatabaseHelper databaseHelper;
    private Resources resources;
    private ProgressDialog progressDialog;

    public MedicamentsDbJSON(Resources resources, Context context, ProgressDialog progressDialog) {
        this.resources = resources;
        this.context = context;
        this.progressDialog = progressDialog;
    }

    @SneakyThrows
    public void getMedicamentsDbFromFile() {
        InputStream inputStream = resources.openRawResource(R.raw.drugs_info);

        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(inputStream);

        readMessagesArray(jsonParser);
        releaseHelper();
    }

    @SneakyThrows
    private void readMessagesArray(JsonParser jsonParser) {
        int i = MedicamentsDbAdditionalJSON.count;
        while (jsonParser.nextToken() != END_ARRAY) {
            DbMedicament dbMedicament = readDbMedicament(jsonParser);
            progressDialog.setProgress(i);
            i++;
            Log.i("tomo", dbMedicament.getPackageID() + "");
            saveDbMedicament(dbMedicament);
        }
        jsonParser.close();
    }

    @SneakyThrows
    private DbMedicament readDbMedicament(JsonParser jsonParser) {

        int packageID = -1;
        String activeSubstance = null;
        int distributorID = -1;
        String dosage = null;
        int driving = -1;
        String drivingInfo = null;
        int drugCardLimit = -1;
        int drugPromoID = -1;
        String ean = null;
        int finalSort = -1;
        String form = null;
        int isAlco = -1;
        String isAlcoInfo = null;
        int isNarcPsych = -1;
        String isNarcPsychInfo = null;
        int isReimbursed = -1;
        int lactatio = -1;
        String lactatioInfo = null;
        String pack = null;
        int pregnancy = -1;
        String pregnancyInfo = null;
        int prescriptionID = -1;
        String prescriptionName = null;
        String prescriptionShortName = null;
        double price = -1;
        String producer = null;
        int producerID = -1;
        int productID = -1;
        int productLineID = -1;
        String productLineName = null;
        String productName = null;
        int productTypeID = -1;
        String productTypeName = null;
        String productTypeShortName = null;
        String regNo = null;
        int sponsorID = -1;
        String therapeuticClass = null;
        int trimester1 = -1;
        String trimester1Info = null;
        int trimester2 = -1;
        String trimester2Info = null;
        int trimester3 = -1;
        String trimester3Info = null;
        int isFavorite = -1;
        int oi = -1;

        while (jsonParser.nextToken() != END_OBJECT) {

            if (jsonParser.getCurrentToken() == START_ARRAY) {

                while (jsonParser.nextToken() != END_ARRAY) {
                    jsonParser.nextToken();
                }
            }

            String fieldname = jsonParser.getCurrentName();


            if (fieldname != null) {

                if (fieldname.equalsIgnoreCase("packageID")) packageID = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("activeSubstance")) activeSubstance = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("distributorID")) distributorID = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("dosage")) dosage = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("driving")) driving = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("drivingInfo")) drivingInfo = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("drugCardLimit")) drugCardLimit = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("drugPromoID")) drugPromoID = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("ean")) ean = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("finalSort")) finalSort = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("form")) form = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("isAlco")) isAlco = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("isAlcoInfo")) isAlcoInfo = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("isNarcPsych")) isNarcPsych = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("isNarcPsychInfo")) isNarcPsychInfo = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("isReimbursed")) isReimbursed = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("lactatio")) lactatio = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("lactatioInfo")) lactatioInfo = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("pack")) pack = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("pregnancy")) pregnancy = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("pregnancyInfo")) pregnancyInfo = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("prescriptionID")) prescriptionID = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("prescriptionName")) prescriptionName = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("prescriptionShortName")) prescriptionShortName = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("price")) price = jsonParser.getValueAsDouble();
                else if (fieldname.equalsIgnoreCase("producer")) producer = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("producerID")) producerID = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("productLineID")) productLineID = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("productLineName")) productLineName = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("productName")) productName = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("productTypeID")) productTypeID = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("productTypeName")) productTypeName = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("productTypeShortName")) productTypeShortName = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("regNo")) regNo = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("therapeuticClass")) therapeuticClass = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("trimester1")) trimester1 = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("trimester1Info")) trimester1Info = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("trimester2")) trimester2 = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("trimester2Info")) trimester2Info = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("trimester3")) trimester3 = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("trimester3Info")) trimester3Info = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("isFavorite")) isFavorite = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("oi")) oi = jsonParser.getValueAsInt();
            }
        }

        Dao<MedicamentAdditional, Integer> medicamentAdditionalsDao = getHelper().getMedicamentAdditionalsDao();
        MedicamentAdditional medicamentAdditional = medicamentAdditionalsDao.queryForId(productLineID);
        return new DbMedicament(medicamentAdditional, packageID, distributorID, activeSubstance, distributorID, dosage, driving, drivingInfo, drugCardLimit, drugPromoID, ean, finalSort, form, isAlco, isAlcoInfo, isNarcPsych, isNarcPsychInfo, isReimbursed, lactatio, lactatioInfo, pack, pregnancy, pregnancyInfo, prescriptionID, prescriptionName, prescriptionShortName, price, producer, producerID, productID, productLineName, productName, productTypeID, productTypeName, productTypeShortName, regNo, sponsorID, therapeuticClass, trimester1, trimester1Info, trimester2, trimester2Info, trimester3, trimester3Info, isFavorite, oi);
    }

    private void saveDbMedicament(DbMedicament dbMedicament) throws SQLException {
        Dao<DbMedicament, Integer> medicamentDbDao = getHelper().getMedicamentDbDao();
        medicamentDbDao.create(dbMedicament);
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    private void releaseHelper() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
