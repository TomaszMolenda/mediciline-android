package local.tomo.login.json;

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import local.tomo.login.R;
import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.model.MedicamentDb;

public class MedicamentsDbJSON {

    DatabaseHandler databaseHandler;
    InputStream inputStream;
    ByteArrayOutputStream byteArrayOutputStream;

    GsonBuilder gsonBuilder;
    Gson gson;

    Resources resources;

    public MedicamentsDbJSON(Resources resources) {
        this.resources = resources;
    }

    public List<MedicamentDb> getMedicamentsDbFromFile() {
        inputStream = resources.openRawResource(R.raw.medi);
        byteArrayOutputStream = new ByteArrayOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        MedicamentDb[] medicamentDbs = gson.fromJson(reader, MedicamentDb[].class);
        return Arrays.asList(medicamentDbs);
    }






}
