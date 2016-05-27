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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.model.Medicament;


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


        Button button = (Button) rootView.findViewById(R.id.button);
        Button button2 = (Button) rootView.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //medicamentDbDAO.getAll();
                //databaseHandler.getMedicamentDbDAO().getAll();
                databaseHandler.getMedicamentsDb();
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
