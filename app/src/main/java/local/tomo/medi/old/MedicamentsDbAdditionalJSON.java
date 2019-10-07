package local.tomo.medi.old;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.InputStream;
import java.sql.SQLException;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.MedicamentAdditional;
import lombok.SneakyThrows;

import static com.fasterxml.jackson.core.JsonToken.END_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.END_OBJECT;


public class MedicamentsDbAdditionalJSON {

    public static int count = 6473;

    private Context context;

    private DatabaseHelper databaseHelper;

    private Resources resources;

    private ProgressDialog progressDialog;

    public MedicamentsDbAdditionalJSON(Resources resources, Context applicationContext, ProgressDialog progressDialog) {
        this.resources = resources;
        this.context = applicationContext;
        this.progressDialog = progressDialog;
    }

    @SneakyThrows
    public void getMedicamentAdditionalFromFile() {
        InputStream inputStream = resources.openRawResource(R.raw.drugs_additional);

        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(inputStream);

        readMessagesArray(jsonParser);
        releaseHelper();
    }

    @SneakyThrows
    private void readMessagesArray(JsonParser jsonParser) {
        int i = 1;
        while (jsonParser.nextToken() != END_ARRAY) {
            MedicamentAdditional medicamentAdditional = readMedicamentAdditional(jsonParser);
            progressDialog.setProgress(i);
            i++;
            saveMedicamentAdditional(medicamentAdditional);
        }
        jsonParser.close();
    }

    @SneakyThrows
    private MedicamentAdditional readMedicamentAdditional(JsonParser jsonParser) {

        int productLineID = -1;
        String composition = null;
        String effects = null;
        String indications = null;
        String contraindications = null;
        String precaution = null;
        String pregnancy = null;
        String sideeffects = null;
        String interactions = null;
        String dosage = null;
        String remark = null;

        while (jsonParser.nextToken() != END_OBJECT) {

            String fieldname = jsonParser.getCurrentName();

            if (fieldname != null) {

                if (fieldname.equalsIgnoreCase("productLineID")) productLineID = jsonParser.getValueAsInt();
                else if (fieldname.equalsIgnoreCase("composition")) composition = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("effects")) effects = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("indications")) indications = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("contraindications")) contraindications = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("precaution")) precaution = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("pregnancy")) pregnancy = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("sideeffects")) sideeffects = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("interactions")) interactions = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("dosage")) dosage = jsonParser.getValueAsString();
                else if (fieldname.equalsIgnoreCase("remark")) remark = jsonParser.getValueAsString();
            }
        }

        return new MedicamentAdditional(productLineID, composition, effects, indications, contraindications, precaution, pregnancy, sideeffects, interactions, dosage, remark);

    }

    private void saveMedicamentAdditional(MedicamentAdditional medicamentAdditional) throws SQLException {
//        Dao<MedicamentAdditional, Integer> medicamentAdditionalsDao = getHelper().getMedicamentAdditionalsDao();
//        medicamentAdditionalsDao.create(medicamentAdditional);
    }

    private void releaseHelper() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

}
