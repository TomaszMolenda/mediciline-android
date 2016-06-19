package local.tomo.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.model.Medicament;
import local.tomo.login.network.Synchronize;


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
                refreshList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemAtPosition = listView.getItemAtPosition(position);
                TextView textView = (TextView) view.findViewById(R.id.rowAllMedicamentId);
                String ids = textView.getText().toString();
                boolean b = databaseHandler.deleteMedicament(ids);
                Toast.makeText(getContext(), "usunieto: " + b, Toast.LENGTH_LONG).show();
                refreshList();
            }
        });

        fabAddMedicament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddMedicamentActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        return rootView;
    }

    private void refreshList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Synchronize synchronize = new Synchronize(getContext());
                synchronize.synchronizeAllMedicaments();
                medicaments.clear();
                medicaments.addAll(databaseHandler.getMedicaments());
                allMedicamentAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        refreshList();
    }





}
