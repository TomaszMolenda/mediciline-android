package local.tomo.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.model.Medicament;
import local.tomo.login.network.RestIntefrace;
import local.tomo.login.network.Synchronize;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AllMedicamentsFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private List<Medicament> medicaments;

    private AllMedicamentAdapter allMedicamentAdapter;
    private ListView listView ;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_medicaments_layout, container, false);
        databaseHandler = new DatabaseHandler(getContext(), null, null, 1);
        medicaments = databaseHandler.getMedicaments();

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);

        listView = (ListView) rootView.findViewById(android.R.id.list);
        allMedicamentAdapter = new AllMedicamentAdapter(getContext(), R.layout.all_medicament_list_row, (ArrayList<Medicament>) medicaments);

        listView.setAdapter(allMedicamentAdapter);

        FloatingActionButton fabAddMedicament = (FloatingActionButton) rootView.findViewById(R.id.fabAddMedicament);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                Log.d("tomo", "srart ref");
                Synchronize synchronize = new Synchronize(getContext());
                synchronize.synchronizeAllMedicaments(swipeRefreshLayout, allMedicamentAdapter);
            }
        });

//        buttonDeleteMedicaments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseHandler.removeAllMedicaments();
//                medicaments.clear();
//                allMedicamentAdapter.notifyDataSetChanged();
//
//            }
//        });

        fabAddMedicament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddMedicamentActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Medicament medicament = databaseHandler.getLastMedicament();
        int idToAdd = medicament.getId();
        int highestId = getHighestId(medicaments);
        if(idToAdd != highestId)
            medicaments.add(0, medicament);
        allMedicamentAdapter.notifyDataSetChanged();

    }

    private int getHighestId(List<Medicament> medicaments) {
        int id = 0;
        for (Medicament medicament : medicaments) {
            if(medicament.getId() > id)
                id = medicament.getId();
        }
        return id;
    }
}
