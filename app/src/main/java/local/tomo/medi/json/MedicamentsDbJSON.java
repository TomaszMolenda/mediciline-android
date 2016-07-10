package local.tomo.medi.json;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;


import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.DbMedicament;
import local.tomo.medi.ormlite.data.MedicamentAdditional;


public class MedicamentsDbJSON {

    public static int count = 11575;
    private Context context;
    private DatabaseHelper databaseHelper;
    private Resources resources;
    private ProgressDialog progressDialog;

    public MedicamentsDbJSON(Resources resources, Context context, ProgressDialog progressDialog) {
        this.resources = resources;
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public void getMedicamentsDbFromFile() {
        InputStream inputStream = resources.openRawResource(R.raw.medi);
        try {
            readJsonStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        releaseHelper();
    }

    public void readJsonStream(InputStream in) throws IOException, SQLException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public void readMessagesArray(JsonReader reader) throws IOException, SQLException {
        reader.beginArray();
        int i = MedicamentsDbAdditionalJSON.count;
        while (reader.hasNext()) {
            DbMedicament dbMedicament = readDbMedicament(reader);
            progressDialog.setProgress(i);
            i++;
            saveDbMedicament(dbMedicament);
        }
        reader.endArray();
    }

    private DbMedicament readDbMedicament(JsonReader reader) throws IOException, SQLException {

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

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            JsonToken peek = reader.peek();
            if (peek == JsonToken.NULL) reader.skipValue();
            else if (name.equals("packageID")) packageID = reader.nextInt();
            else if (name.equals("activeSubstance")) activeSubstance = reader.nextString();
            else if (name.equals("distributorID")) distributorID = reader.nextInt();
            else if (name.equals("dosage")) dosage = reader.nextString();
            else if (name.equals("driving")) driving = reader.nextInt();
            else if (name.equals("drivingInfo")) drivingInfo = reader.nextString();
            else if (name.equals("drugCardLimit")) drugCardLimit = reader.nextInt();
            else if (name.equals("drugPromoID")) drugPromoID = reader.nextInt();
            else if (name.equals("ean")) ean = reader.nextString();
            else if (name.equals("finalSort")) finalSort = reader.nextInt();
            else if (name.equals("form")) form = reader.nextString();
            else if (name.equals("isAlco")) isAlco = reader.nextInt();
            else if (name.equals("isAlcoInfo")) isAlcoInfo = reader.nextString();
            else if (name.equals("isNarcPsych")) isNarcPsych = reader.nextInt();
            else if (name.equals("isNarcPsychInfo")) isNarcPsychInfo = reader.nextString();
            else if (name.equals("isReimbursed")) isReimbursed = reader.nextInt();
            else if (name.equals("lactatio")) lactatio = reader.nextInt();
            else if (name.equals("lactatioInfo")) lactatioInfo = reader.nextString();
            else if (name.equals("pack")) pack = reader.nextString();
            else if (name.equals("pregnancy")) pregnancy = reader.nextInt();
            else if (name.equals("pregnancyInfo")) pregnancyInfo = reader.nextString();
            else if (name.equals("prescriptionID")) prescriptionID = reader.nextInt();
            else if (name.equals("prescriptionName")) prescriptionName = reader.nextString();
            else if (name.equals("prescriptionShortName")) prescriptionShortName = reader.nextString();
            else if (name.equals("price")) price = reader.nextDouble();
            else if (name.equals("producer")) producer = reader.nextString();
            else if (name.equals("producerID")) producerID = reader.nextInt();
            else if (name.equals("productLineID")) productLineID = reader.nextInt();
            else if (name.equals("productLineName")) productLineName = reader.nextString();
            else if (name.equals("productName")) productName = reader.nextString();
            else if (name.equals("productTypeID")) productTypeID = reader.nextInt();
            else if (name.equals("productTypeName")) productTypeName = reader.nextString();
            else if (name.equals("productTypeShortName")) productTypeShortName = reader.nextString();
            else if (name.equals("regNo")) regNo = reader.nextString();
            else if (name.equals("therapeuticClass")) therapeuticClass = reader.nextString();
            else if (name.equals("trimester1")) trimester1 = reader.nextInt();
            else if (name.equals("trimester1Info")) trimester1Info = reader.nextString();
            else if (name.equals("trimester2")) trimester2 = reader.nextInt();
            else if (name.equals("trimester2Info")) trimester2Info = reader.nextString();
            else if (name.equals("trimester3")) trimester3 = reader.nextInt();
            else if (name.equals("trimester3Info")) trimester3Info = reader.nextString();
            else if (name.equals("isFavorite")) isFavorite = reader.nextInt();
            else if (name.equals("oi")) oi = reader.nextInt();
            else reader.skipValue();
        }
        reader.endObject();
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
