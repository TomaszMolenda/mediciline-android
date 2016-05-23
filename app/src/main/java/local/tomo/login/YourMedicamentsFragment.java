package local.tomo.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.model.Medicament;
import local.tomo.login.network.InternetFragment;
import local.tomo.login.network.MedicamentSaveRequest;

public class YourMedicamentsFragment extends InternetFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;

    private MedicamentsAdapter medicamentsAdapter;
    private DatabaseHandler databaseHandler;


    public static AllMedicamentsFragment newInstance(int sectionNumber) {
        AllMedicamentsFragment fragment = new AllMedicamentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public YourMedicamentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.your_medicaments_layout, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        databaseHandler = new DatabaseHandler(getContext(), null, null, 1);

        //Database.getMedicamentFromServer(getActivity(), spiceManager, databaseHandler);
        //List<Medicament> medicaments = medicamentDAO.getAll();
        //List<Medicament> medicaments = databaseHandler.getMedicamentDAO().getAll();
        List<Medicament> medicaments = databaseHandler.getAll();
        for (Medicament medicament : medicaments) {
            Log.d("tomo", "id:"+medicament.getIdServer());
        }
//        Medicament medicament = medicaments.get(0);
//        MedicamentSaveRequest medicamentSaveRequest = new MedicamentSaveRequest(medicament);
//        spiceManager.execute(medicamentSaveRequest, new RequestListener<Medicament>() {
//            @Override
//            public void onRequestFailure(SpiceException spiceException) {
//                Log.d("tomo", "jest zle");
//            }
//
//            @Override
//            public void onRequestSuccess(Medicament medicament) {
//                Log.d("tomo", "jest ok");
//            }
//        });
        medicamentsAdapter = new MedicamentsAdapter(medicaments);
        recyclerView.setAdapter(medicamentsAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
