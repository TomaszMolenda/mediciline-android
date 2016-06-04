package local.tomo.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.json.MedicamentsDbJSON;
import local.tomo.login.model.MedicamentDb;
import local.tomo.login.model.User;
import local.tomo.login.network.RestIntefrace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity {

    private static String PREF_NAME = "tomo_pref";
    private static String PREF_USER = "username";
    private static String PREF_PASSWORD = "password";
    private static String PREF_UNIQUE_ID = "uniqueId";

    public static String uniqueId;



    String userName;
    DatabaseHandler databaseHandler;
    MedicamentsDbJSON medicamentsDbJSON;

    private EditText editTextLoginUserName;
    private EditText editTextLoginPassword;

    private class Background extends AsyncTask<Void, Void, Void> {

        Resources resources;
        LoginActivity loginActivity;

        private ProgressDialog progressDialog;

        public Background(Context applicationContext, Resources resources) {
            this.resources = resources;
            this.progressDialog = new ProgressDialog(applicationContext);
        }


        @Override
        protected Void doInBackground(Void... params) {

            medicamentsDbJSON = new MedicamentsDbJSON(getResources());
            InputStream inputStream = resources.openRawResource(R.raw.medicount);
            String s = "";
            try {
                s = IOUtils.toString(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int medicamentsDbCountFile = Integer.parseInt(s);
            //if(medicamentsDbCountFile > medicamentDbDAO.count()) {
            //if(medicamentsDbCountFile > databaseHandler.getMedicamentDbDAO().count()) {
            if(medicamentsDbCountFile > databaseHandler.getMedicamentsDbCount()) {
                List<MedicamentDb> medicamentsDbFromFile = medicamentsDbJSON.getMedicamentsDbFromFile();
                //List<MedicamentDb> medicamentsDbs = medicamentDbDAO.getAll();
                //List<MedicamentDb> medicamentsDbs = databaseHandler.getMedicamentDbDAO().getAll();
                List<MedicamentDb> medicamentsDbs = databaseHandler.getMedicamentsDb();
                List<MedicamentDb> list = new LinkedList<MedicamentDb>(medicamentsDbFromFile);
                List<MedicamentDb> toRemove = new ArrayList<MedicamentDb>();
                for (MedicamentDb medicamentDb : list) {
                    for (MedicamentDb medicamentDb1 : medicamentsDbs) {
                        if(medicamentDb.getPackageID() == medicamentDb1.getPackageID()) {
                            toRemove.add(medicamentDb);
                            break;
                        }
                    }
                }
                list.removeAll(toRemove);
                medicamentsDbFromFile = new ArrayList<MedicamentDb>(list);
                if(medicamentsDbFromFile.size() > 0)
                    //medicamentDbDAO.addAll(medicamentsDbFromFile);
                    //databaseHandler.getMedicamentDbDAO().addAll(medicamentsDbFromFile);
                    databaseHandler.putAllMedicamentsDb(medicamentsDbFromFile);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this, "Synchronizacja...", "Trwa synchronizacja", true, false);
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

        databaseHandler = new DatabaseHandler(getApplicationContext(), null, null, 1);
        editTextLoginUserName = (EditText) findViewById(R.id.editTextLoginUserName);
        editTextLoginPassword = (EditText) findViewById(R.id.editTextLoginPassword);

        Background background = new Background(getApplicationContext(), getResources());
        background.execute();

        Log.d("tomo", "111");

        SharedPreferences preference = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        userName = preference.getString(PREF_USER, null);
        String password = preference.getString(PREF_PASSWORD, null);
        uniqueId = preference.getString(PREF_UNIQUE_ID, null);
        if (userName != null & password != null & uniqueId != null) {
            login();
        }
    }

    private void login() {
        Toast.makeText(this, "Zalogowano jako " + userName, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void loginClick(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestIntefrace.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestIntefrace restIntefrace = retrofit.create(RestIntefrace.class);
        Call<User> call = restIntefrace.user(editTextLoginUserName.getText().toString(), editTextLoginPassword.getText().toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
//    TODO implements businnes logic
        /*
        UserRequest userRequest = new UserRequest(editTextLoginUserName.getText().toString(), editTextLoginPassword.getText().toString());
        spiceManager.execute(userRequest, new RequestListener<User>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                requestFailure();
            }

            @Override
            public void onRequestSuccess(User user) {
                if (user != null) {
                    userName = user.getName();
                    getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                            .putString(PREF_USER, userName)
                            .putString(PREF_PASSWORD, user.getPassword())
                            .putString(PREF_UNIQUE_ID, user.getUniqueID())
                            .commit();
                    login();
                } else
                    Toast.makeText(getApplicationContext(), "Błędny login lub hasło", Toast.LENGTH_SHORT).show();
            }
        });

*/
    }

    private void requestFailure() {
        if (!isInternetAvailable())
            Toast.makeText(getApplicationContext(), "Brak połączenia z Internetem", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "Brak połączenia z serwerem", Toast.LENGTH_LONG).show();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }

    public void registerClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }
}