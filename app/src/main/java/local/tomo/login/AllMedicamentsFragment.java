package local.tomo.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import local.tomo.login.database.DatabaseHandler;


public class AllMedicamentsFragment extends Fragment {

    private DatabaseHandler databaseHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_medicaments_layout, container, false);
        databaseHandler = new DatabaseHandler(getContext(), null, null, 1);


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
}
