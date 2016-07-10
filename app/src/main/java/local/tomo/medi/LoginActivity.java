package local.tomo.medi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;


import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.List;

import local.tomo.medi.json.MedicamentsDbAdditionalJSON;
import local.tomo.medi.json.MedicamentsDbJSON;
import local.tomo.medi.network.RestIntefrace;
import local.tomo.medi.network.RetrofitBuilder;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.DbMedicament;
import local.tomo.medi.ormlite.data.MedicamentAdditional;
import local.tomo.medi.ormlite.data.User;
import retrofit2.Call;


public class LoginActivity extends Activity {

    private static final int MEDICAMENTDB_COUNT = 11575;

    private static String PREF_NAME = "medi_pref";
    private static String PREF_USER = "username";
    private static String PREF_PASSWORD = "password";
    private static String PREF_UNIQUE_ID = "uniqueId";
    private static String PREF_EMAIL = "email";

    private static User user = new User();

    private DatabaseHelper databaseHelper = null;

    private EditText editTextLoginUserName;
    private EditText editTextLoginPassword;


    private class Background extends AsyncTask<Void, Void, Void> {

        Resources resources;

        private ProgressDialog progressDialog;

        public Background(Context applicationContext, Resources resources) {
            this.resources = resources;
            this.progressDialog = new ProgressDialog(applicationContext);
        }


        @Override
        protected Void doInBackground(Void... params) {
            MedicamentsDbAdditionalJSON medicamentsDbAdditionalJSON = new MedicamentsDbAdditionalJSON(getResources(), getApplicationContext(), progressDialog);
            medicamentsDbAdditionalJSON.getMedicamentAdditionalFromFile();

            MedicamentsDbJSON medicamentsDbJSON = new MedicamentsDbJSON(getResources(), getApplicationContext(), progressDialog);
            medicamentsDbJSON.getMedicamentsDbFromFile();
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Trwa budowanie bazy leków");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(false);
            progressDialog.setProgress(0);
            progressDialog.setMax(MedicamentsDbJSON.count + MedicamentsDbAdditionalJSON.count);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //http://stackoverflow.com/a/35797136
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        editTextLoginUserName = (EditText) findViewById(R.id.editTextLoginUserName);
        editTextLoginPassword = (EditText) findViewById(R.id.editTextLoginPassword);

        Background background = new Background(getApplicationContext(), getResources());
        background.execute();

        Log.d("meditomo", "22222");

        SharedPreferences preference = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String userName = preference.getString(PREF_USER, null);
        String password = preference.getString(PREF_PASSWORD, null);
        String uniqueID = preference.getString(PREF_UNIQUE_ID, null);
        String email = preference.getString(PREF_EMAIL, null);
        if (userName != null & password != null & uniqueID != null) {
            user.setName(userName);
            user.setPassword(password);
            user.setEmail(email);
            user.setUniqueID(uniqueID);
            login();
        }
    }

    private void login() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                Synchronize synchronize = new Synchronize(getApplicationContext());
//                synchronize.synchronizeAllMedicaments();

            }
        };
        new Thread(runnable).start();
        Toast.makeText(this, "Zalogowano jako " + user.getName(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void loginClick(View view) {
        RestIntefrace restIntefrace = RetrofitBuilder.getRestIntefrace();
        Call<User> call = restIntefrace.user(editTextLoginUserName.getText().toString(), editTextLoginPassword.getText().toString());
        try {
            user = call.execute().body();
            if (user != null) {
                getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                        .putString(PREF_USER, user.getName())
                        .putString(PREF_PASSWORD, user.getPassword())
                        .putString(PREF_UNIQUE_ID, user.getUniqueID())
                        .putString(PREF_EMAIL, user.getEmail())
                        .commit();
                login();
            } else
                Toast.makeText(getApplicationContext(), "Błędny login lub hasło", Toast.LENGTH_SHORT).show();
        } catch (SocketTimeoutException e) {
            Toast.makeText(getApplicationContext(), "Brak połączenia z Internetem", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LoginActivity.user = user;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }
}