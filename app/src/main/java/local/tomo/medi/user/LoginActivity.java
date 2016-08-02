package local.tomo.medi.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import local.tomo.medi.MainActivity;
import local.tomo.medi.R;
import local.tomo.medi.json.MedicamentsDbAdditionalJSON;
import local.tomo.medi.json.MedicamentsDbJSON;
import local.tomo.medi.network.RestIntefrace;
import local.tomo.medi.network.RetrofitBuilder;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.User;
import retrofit2.Call;


public class LoginActivity extends Activity {

    public static final String TAG = "meditomo";

    public static final int MEDICAMENTDB_COUNT = 11575;

    private static String PREF_NAME = "medi_pref";
    private static String PREF_USER = "username";
    private static String PREF_PASSWORD = "password";
    private static String PREF_UNIQUE_ID = "uniqueId";
    private static String PREF_AUTH = "auth";
    private static String PREF_EMAIL = "email";

    private static User user = new User();

    private DatabaseHelper databaseHelper;

    private EditText editTextLoginUserName;
    private EditText editTextLoginPassword;
    private Button buttonRegister;


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

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });


        SharedPreferences preference = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String userName = preference.getString(PREF_USER, null);
        String password = preference.getString(PREF_PASSWORD, null);
        String uniqueID = preference.getString(PREF_UNIQUE_ID, null);
        String email = preference.getString(PREF_EMAIL, null);
        String auth = preference.getString(PREF_AUTH, null);
        if (userName != null & password != null & uniqueID != null & auth != null) {
            user.setName(userName);
            user.setPassword(password);
            user.setEmail(email);
            user.setUniqueID(uniqueID);
            user.setAuth(auth);
            Log.d(TAG, "22222:");
            setOverdueMedicaments();
            login();
        }
        else {
            Background background = new Background(getApplicationContext(), getResources());
            background.execute();
        }
    }

    private void setOverdueMedicaments() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        List<Medicament> medicaments = null;
        try {
            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
            QueryBuilder<Medicament, Integer> queryBuilder = medicamentDao.queryBuilder();
            queryBuilder.where().eq("archive", false).and().eq("overdue", false);
            PreparedQuery<Medicament> prepare = queryBuilder.prepare();
            medicaments = medicamentDao.query(prepare);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (Medicament medicament : medicaments) {
            calendar.setTimeInMillis(medicament.getDate());
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            if(mYear < year) setOverdue(medicament);
            if(mYear == year && mMonth < month) setOverdue(medicament);
        }
    }

    private void setOverdue(Medicament medicament) {
        medicament.setOverdue(true);
        try {
            getHelper().getMedicamentDao().update(medicament);
        } catch (SQLException e) {
            e.printStackTrace();
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
        String userName = editTextLoginUserName.getText().toString();
        String password = editTextLoginPassword.getText().toString();
        RestIntefrace restIntefrace = RetrofitBuilder.getRestIntefrace(userName, password);
        Call<User> call = restIntefrace.login();
        try {
            user = call.execute().body();
            if (user != null) {
                getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                        .putString(PREF_USER, user.getName())
                        .putString(PREF_PASSWORD, user.getPassword())
                        .putString(PREF_UNIQUE_ID, user.getUniqueID())
                        .putString(PREF_EMAIL, user.getEmail())
                        .putString(PREF_AUTH, user.getAuth())
                        .commit();
                login();
            } else
                Toast.makeText(getApplicationContext(), "Błędny login lub hasło", Toast.LENGTH_SHORT).show();
        } catch (SocketTimeoutException e) {
            Toast.makeText(getApplicationContext(), "Brak połączenia z serwerem", Toast.LENGTH_LONG).show();
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