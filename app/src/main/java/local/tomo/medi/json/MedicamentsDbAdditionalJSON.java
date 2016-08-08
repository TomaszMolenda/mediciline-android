package local.tomo.medi.json;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.DbMedicament;
import local.tomo.medi.ormlite.data.MedicamentAdditional;
import lombok.SneakyThrows;


public class MedicamentsDbAdditionalJSON {

    public static int count = 5704;

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
        InputStream inputStream = resources.openRawResource(R.raw.additional);
        readJsonStream(inputStream);
        releaseHelper();
    }

    @SneakyThrows
    public void readJsonStream(InputStream in) throws IOException, SQLException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        readMessagesArray(reader);
        reader.close();
    }

    public void readMessagesArray(JsonReader reader) throws IOException, SQLException {
        reader.beginArray();
        int i = 1;
        while (reader.hasNext()) {
            MedicamentAdditional medicamentAdditional = readMedicamentAdditional(reader);
            progressDialog.setProgress(i);
            i++;
            saveMedicamentAdditional(medicamentAdditional);
        }
        reader.endArray();
    }

    private MedicamentAdditional readMedicamentAdditional(JsonReader reader) throws IOException {

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

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            JsonToken peek = reader.peek();
            if (peek == JsonToken.NULL) reader.skipValue();
            else if (name.equals("productLineID")) productLineID = reader.nextInt();
            else if (name.equals("composition")) composition = reader.nextString();
            else if (name.equals("effects")) effects = reader.nextString();
            else if (name.equals("indications")) indications = reader.nextString();
            else if (name.equals("contraindications")) contraindications = reader.nextString();
            else if (name.equals("precaution")) precaution = reader.nextString();
            else if (name.equals("pregnancy")) pregnancy = reader.nextString();
            else if (name.equals("sideeffects")) sideeffects = reader.nextString();
            else if (name.equals("interactions")) interactions = reader.nextString();
            else if (name.equals("dosage")) dosage = reader.nextString();
            else if (name.equals("remark")) remark = reader.nextString();
            else reader.skipValue();


        }
        reader.endObject();
        return new MedicamentAdditional(productLineID, composition, effects, indications, contraindications, precaution, pregnancy, sideeffects, interactions, dosage, remark);

    }

    private void saveMedicamentAdditional(MedicamentAdditional medicamentAdditional) throws SQLException {
        Dao<MedicamentAdditional, Integer> medicamentAdditionalsDao = getHelper().getMedicamentAdditionalsDao();
        medicamentAdditionalsDao.create(medicamentAdditional);
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
