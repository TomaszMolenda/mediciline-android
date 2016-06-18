package local.tomo.login.network;

import android.content.Context;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import local.tomo.login.AllMedicamentAdapter;
import local.tomo.login.LoginActivity;
import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.json.MedicamentExclusion;
import local.tomo.login.model.Medicament;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tomo on 2016-06-05.
 */
public class Synchronize {

    private Context context;
    private Medicament medicament;

    private DatabaseHandler databaseHandler;

    private SwipeRefreshLayout swipeRefreshLayout;
    private AllMedicamentAdapter allMedicamentAdapter;


    public Synchronize(Context context) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context, null, null, 1);
    }

    public void synchronizeAllMedicaments() {
        List<Medicament> localMedicamentsToSend = databaseHandler.getMedicamentsToSend();
        sendMedicamentsZeroIdServer(localMedicamentsToSend);
        List<Medicament> remoteMedicaments = getAllMedicamentFromServer();
        List<Medicament> localMedicamentsSended = databaseHandler.getSendedMedicaments();
        if(remoteMedicaments == null) {
            Toast.makeText(context, "Błąd synchronizacji", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Medicament> remoteMedicamentsToSave = Medicament.compareIdServer(remoteMedicaments, localMedicamentsSended);
        databaseHandler.addMedicaments(remoteMedicamentsToSave);


    }

    private List<Medicament> getAllMedicamentFromServer() {
        List<Medicament> medicaments = null;
        RestIntefrace restIntefrace = RetrofitBuilder.getRestIntefrace();
        Call<List<Medicament>> call = restIntefrace.getMedicaments(LoginActivity.uniqueId);
        try {
            Response<List<Medicament>> execute = call.execute();
            medicaments = execute.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medicaments;
    }

    private boolean sendMedicamentsZeroIdServer(List<Medicament> medicaments) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new MedicamentExclusion())
                .create();
        RestIntefrace restIntefrace = RetrofitBuilder.getRestIntefrace(gson);
        Call<List<Medicament>> call = restIntefrace.saveMedicaments(medicaments);
        try {
            Response<List<Medicament>> execute = call.execute();
            medicaments = execute.body();
            databaseHandler.setIdServer(medicaments);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
