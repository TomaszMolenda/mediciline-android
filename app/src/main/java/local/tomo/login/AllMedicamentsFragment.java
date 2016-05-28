package local.tomo.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.model.Medicament;
import local.tomo.login.network.RestIntefrace;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AllMedicamentsFragment extends ListFragment {

    private DatabaseHandler databaseHandler;
    List<Medicament> medicaments;

    private AllMedicamentAdapter allMedicamentAdapter;
    private ListView listView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_medicaments_layout, container, false);
        databaseHandler = new DatabaseHandler(getContext(), null, null, 1);
        medicaments = databaseHandler.getMedicaments();

        listView = (ListView) rootView.findViewById(android.R.id.list);
        Log.d("tomo", "wysywala sie");
        allMedicamentAdapter = new AllMedicamentAdapter(getContext(), R.layout.all_medicament_list_row, (ArrayList<Medicament>) medicaments);

        listView.setAdapter(allMedicamentAdapter);


        Button buttonDeleteMedicaments = (Button) rootView.findViewById(R.id.buttonDeleteMedicaments);
        Button buttonSynchronizeMedicaments = (Button) rootView.findViewById(R.id.buttonSynchronizeMedicaments);
        buttonDeleteMedicaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.removeAllMedicaments();

            }
        });
        buttonSynchronizeMedicaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RestIntefrace.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
                RestIntefrace restIntefrace = retrofit.create(RestIntefrace.class);
                Call<List<Medicament>> call = restIntefrace.getMedicaments(LoginActivity.uniqueId);
                call.enqueue(new Callback<List<Medicament>>() {
                    @Override
                    public void onResponse(Call<List<Medicament>> call, Response<List<Medicament>> response) {
                        List<Medicament> medicaments = response.body();
                        Log.d("tomo", "w liscie: " + medicaments.size());
                        Log.d("tomo", "internet");
                        List<Medicament> medicamentsLocal = databaseHandler.getMedicaments();

                        endloop:
                        for (Medicament medicament : medicaments) {
                            if(!medicamentsLocal.contains(medicament)) {
                                for (Medicament medicamentLocal : medicamentsLocal) {
                                    if(medicament.getIdServer() == medicamentLocal.getIdServer()) {
                                        databaseHandler.updateMedicament(medicament.getIdServer(), medicament);
                                        break endloop;
                                    }
                                }
                                databaseHandler.addMedicament(medicament);
                            }


//                            Log.d("tomo", medicament.toString());
//                            Log.d("tomo", String.valueOf(medicament.hashCode()));



                        }
                        List<Medicament> medicaments1 = databaseHandler.getMedicaments();
                        Log.d("tomo", "local");
                        for (Medicament medicament : medicaments1) {

//                            Log.d("tomo", medicament.toString());
//                            Log.d("tomo", String.valueOf(medicament.hashCode()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Medicament>> call, Throwable t) {

                    }
                });

            }
        });
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d("tomo", "detach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("tomo", "atach");

    }
}
