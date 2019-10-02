package local.tomo.medi.user;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;


import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import local.tomo.medi.MainActivity;
import local.tomo.medi.R;
import local.tomo.medi.json.MedicamentsDbAdditionalJSON;
import local.tomo.medi.json.MedicamentsDbJSON;
import local.tomo.medi.network.RestIntefrace;
import local.tomo.medi.network.RetrofitBuilder;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.User;
import local.tomo.medi.utills.Utill;
import lombok.SneakyThrows;
import retrofit2.Call;


public class LoginActivity extends Activity {

    @BindView(R.id.editTextUserName)
    EditText editTextUserName;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.buttonRegister)
    Button buttonRegister;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;

    private static String PREF_NAME = "medi_pref";
    private static String PREF_USER = "username";
    private static String PREF_PASSWORD = "password";
    private static String PREF_UNIQUE_ID = "uniqueId";
    private static String PREF_AUTH = "auth";
    private static String PREF_EMAIL = "email";

    private static User user = new User();

    private DatabaseHelper databaseHelper;

    private boolean canLogin = true;


    private class DatabaseBuilder extends AsyncTask<Void, Void, Void> {

        Resources resources;

        private ProgressDialog progressDialog;

        public DatabaseBuilder(Context applicationContext, Resources resources) {
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
    @SneakyThrows
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        //http://stackoverflow.com/a/35797136
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        checkMedicamentDb();

        if (canLogin) {
            SharedPreferences preference = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            String userName = preference.getString(PREF_USER, null);
            String password = preference.getString(PREF_PASSWORD, null);
            String uniqueID = preference.getString(PREF_UNIQUE_ID, null);
            String email = preference.getString(PREF_EMAIL, null);
            String auth = preference.getString(PREF_AUTH, null);
            if (userName != null & password != null & uniqueID != null & email != null & auth != null) {
                user.setName(userName);
                user.setPassword(password);
                user.setEmail(email);
                user.setUniqueID(uniqueID);
                user.setAuth(auth);
                InputStream open = getBaseContext().getAssets().open("app.properties");
                setOverdueMedicaments();
                doLogin();
            }
        }


    }

    @SneakyThrows
    private void checkMedicamentDb() {
        long countOfDbMedicaments = getHelper().getMedicamentDbDao().queryBuilder().countOf();
        long countOfDbAdditionals = getHelper().getMedicamentAdditionalsDao().queryBuilder().countOf();
        if (countOfDbMedicaments != MedicamentsDbJSON.count | countOfDbAdditionals != MedicamentsDbAdditionalJSON.count) {

            getHelper().dropMedicamentsDb();
            DatabaseBuilder databaseBuilder = new DatabaseBuilder(getApplicationContext(), getResources());
            databaseBuilder.execute();
        } else {
            canLogin = true;
        }
    }

    @SneakyThrows
    private void setOverdueMedicaments() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
        QueryBuilder<Medicament, Integer> queryBuilder = medicamentDao.queryBuilder();
        queryBuilder.where().eq("archive", false).and().eq("overdue", false);
        PreparedQuery<Medicament> prepare = queryBuilder.prepare();
        List<Medicament> medicaments = medicamentDao.query(prepare);

        for (Medicament medicament : medicaments) {
            calendar.setTimeInMillis(medicament.getDate());
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            if(mYear < year) setOverdue(medicament);
            if(mYear == year && mMonth < month) setOverdue(medicament);
        }
    }

    @SneakyThrows
    private void setOverdue(Medicament medicament) {
        medicament.setOverdue(true);
        getHelper().getMedicamentDao().update(medicament);
    }

    @OnClick(R.id.buttonRegister)
    void register() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }



    private void doLogin() {
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

    @OnClick(R.id.buttonLogin)
    @SneakyThrows
    void login() {
        String userName = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();
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
                doLogin();
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