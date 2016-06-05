package local.tomo.login.network;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.json.exclusion.MedicamentExclusion;
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


    public Synchronize(Context context) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context, null, null, 1);
    }

    public boolean synchronizeMedicament(Medicament m) {
        this.medicament = m;
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new MedicamentExclusion())
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestIntefrace.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        RestIntefrace restIntefrace = retrofit.create(RestIntefrace.class);
        Call<Medicament> call = restIntefrace.saveMedicament(medicament);
        call.enqueue(new Callback<Medicament>() {
            @Override
            public void onResponse(Call<Medicament> call, Response<Medicament> response) {
                Medicament body = response.body();
                medicament.setIdServer(body.getId());
                databaseHandler.setIdServer(medicament);
                Toast.makeText(context, "Wysłano lek  " + medicament.getName() + " na serwer", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Medicament> call, Throwable t) {
                Toast.makeText(context, "Nie udało się wysłać leku  " + medicament.getName() + " na serwer", Toast.LENGTH_LONG).show();
            }
        });
        return true;
    }

}
