package local.tomo.medi.ormlite;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.Drug;
import lombok.SneakyThrows;

import static local.tomo.medi.ormlite.data.Drug.D_PACKAGE_ID;

public class DatabaseDataCreator extends AsyncTask<Void, Void, Void> {

    private final Resources resources;
    private final DatabaseHelper databaseHelper;

    public DatabaseDataCreator(Resources resources, Context context) {
        this.resources = resources;
        this.databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        getDrugsFromFile().forEach(this::save);

        return null;
    }

    @SneakyThrows
    private void save(Drug drug) {

        Dao<Drug, Integer> drugsDataAccess = databaseHelper.getDrugsDataAccess();

        if (drugNotExist(drug)) {

            drugsDataAccess.create(drug);
        }
    }

    @SneakyThrows
    private boolean drugNotExist(Drug drug) {

        return databaseHelper.getDrugsDataAccess()
                .queryBuilder()
                .where()
                .eq(D_PACKAGE_ID, drug.getPackageID())
                .countOf() == 0;
    }

    private List<Drug> getDrugsFromFile() {

        InputStream inputStream = resources.openRawResource(R.raw.drugs_small);
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        return new Gson().fromJson(reader, new TypeToken<List<Drug>>(){}.getType());
    }
}
