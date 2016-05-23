package local.tomo.login.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.model.Medicament;
import local.tomo.login.network.MedicamentsRequest;

/**
 * Created by tomo on 2016-05-03.
 */
public class Database {

    private static String PREF_NAME = "tomo_pref";
    private static String PREF_UNIQUE_ID = "uniqueId";




    public static void dropDatabase() {

    }

    public static void getMedicamentFromServer(Activity activity, SpiceManager spiceManager, final DatabaseHandler databaseHandler) {
        SharedPreferences preference = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String uniqueId = preference.getString(PREF_UNIQUE_ID, null);

        if(uniqueId != null) {
            MedicamentsRequest medicamentsRequest = new MedicamentsRequest(uniqueId);
            spiceManager.execute(medicamentsRequest, new RequestListener<Medicament.List>() {
                @Override
                public void onRequestFailure(SpiceException spiceException) {
                    //Toast.makeText(getActivity(), "blad", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onRequestSuccess(Medicament.List medicaments) {
                    //Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                    //medicamentDAO.deleteAll();
                    //databaseHandler.getMedicamentDAO().deleteAll();
                    databaseHandler.delete();

                    for (Medicament medicament : medicaments) {
                        Log.d("tomo", "data: "+medicament.getDateFormatExpiration().toString());
                        //medicamentDAO.add(medicament);
                        //databaseHandler.getMedicamentDAO().add(medicament);
                        databaseHandler.addMedicament(medicament);
                    }
                }
            });
        }
    }
}
