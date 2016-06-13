package local.tomo.login.network;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import local.tomo.login.AllMedicamentAdapter;
import local.tomo.login.LoginActivity;
import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.json.deserializer.MedicamentDeserializer;
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

    public boolean synchronizeMedicament(Medicament m) {
        this.medicament = m;


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Medicament.class, new MedicamentDeserializer())
                        .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestIntefrace.url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        RestIntefrace restIntefrace = retrofit.create(RestIntefrace.class);
        Call<Medicament> call = restIntefrace.saveMedicament(medicament);
        call.enqueue(new Callback<Medicament>() {
            @Override
            public void onResponse(Call<Medicament> call, Response<Medicament> response) {
                Medicament body = response.body();
                databaseHandler.setIdServer(medicament);
                Toast.makeText(context, "Wysłano lek  " + medicament.getName() + " na serwer", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Medicament> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Nie udało się wysłać leku  " + medicament.getName() + " na serwer", Toast.LENGTH_LONG).show();
            }
        });
        return true;
    }

    public void synchronizeAllMedicaments(SwipeRefreshLayout s, AllMedicamentAdapter a) {
        swipeRefreshLayout = s;
        allMedicamentAdapter = a;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestIntefrace.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        RestIntefrace restIntefrace = retrofit.create(RestIntefrace.class);
        Call<List<Medicament>> listCall = restIntefrace.getMedicaments(LoginActivity.uniqueId);

        listCall.enqueue(new Callback<List<Medicament>>() {
            @Override
            public void onResponse(Call<List<Medicament>> call, Response<List<Medicament>> response) {
                Log.d("tomo", "oberbalem lekii");
                swipeRefreshLayout.setRefreshing(false);
                List<Medicament> medicaments = allMedicamentAdapter.getMedicaments();
                medicaments.clear();
                medicaments.addAll(response.body());
                allMedicamentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Medicament>> call, Throwable t) {

            }
        });
    }

    public void sendNewMedicaments() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestIntefrace.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        RestIntefrace restIntefrace = retrofit.create(RestIntefrace.class);
        List<Medicament> medicamentsToSend = databaseHandler.getMedicaments();
        Call<List<Medicament>> listCall = restIntefrace.saveMedicaments(medicamentsToSend);
        listCall.enqueue(new Callback<List<Medicament>>() {
            @Override
            public void onResponse(Call<List<Medicament>> call, Response<List<Medicament>> response) {
                Log.d("tomo", "wysłałem leki");
            }

            @Override
            public void onFailure(Call<List<Medicament>> call, Throwable t) {

            }
        });
    }



}
