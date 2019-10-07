package local.tomo.medi.old;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

import butterknife.ButterKnife;
import local.tomo.medi.ormlite.DatabaseHelper;
import lombok.SneakyThrows;


public class LoginActivity extends Activity {

    private DatabaseHelper databaseHelper;

    private boolean canLogin;


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
        ButterKnife.bind(this);

        //http://stackoverflow.com/a/35797136
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



    }
}