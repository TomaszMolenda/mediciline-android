package local.tomo.medi.network;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import local.tomo.medi.AllMedicamentAdapter;
import local.tomo.medi.LoginActivity;
import local.tomo.medi.json.MedicamentExclusion;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by tomo on 2016-06-05.
 */
public class Synchronize {

    private DatabaseHelper databaseHelper = null;

    private Context context;
    private Medicament medicament;


    private SwipeRefreshLayout swipeRefreshLayout;
    private AllMedicamentAdapter allMedicamentAdapter;


    public Synchronize(Context context) {
        this.context = context;
    }

    public void synchronizeAllMedicaments() {
        List<Medicament> localMedicamentsToSend = null;
        try {
            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
            QueryBuilder<Medicament, Integer> queryBuilder = medicamentDao.queryBuilder();
            queryBuilder.where().eq("idServer", 0);
            PreparedQuery<Medicament> prepare = queryBuilder.prepare();
            localMedicamentsToSend = medicamentDao.query(prepare);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(localMedicamentsToSend != null) {
            sendMedicamentsZeroIdServer(localMedicamentsToSend);
        }
        List<Medicament> remoteMedicaments = getAllMedicamentFromServer();
        List<Medicament> localMedicamentsSended = null;
        try {
            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
            QueryBuilder<Medicament, Integer> queryBuilder = medicamentDao.queryBuilder();
            queryBuilder.where().ne("idServer", 0);
            PreparedQuery<Medicament> prepare = queryBuilder.prepare();
            localMedicamentsSended = medicamentDao.query(prepare);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(remoteMedicaments == null) {
            Toast.makeText(context, "Błąd synchronizacji", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Medicament> remoteMedicamentsToSave = Medicament.compareIdServer(remoteMedicaments, localMedicamentsSended);
        //// TODO: 2016-06-24
        try {
            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
            for (Medicament medicament : remoteMedicamentsToSave) {
                medicamentDao.create(medicament);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    private List<Medicament> getAllMedicamentFromServer() {
        List<Medicament> medicaments = null;
        RestIntefrace restIntefrace = RetrofitBuilder.getRestIntefrace();
        Call<List<Medicament>> call = restIntefrace.getMedicaments(LoginActivity.getUser().getUniqueID());
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
            try {
                Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
                for (Medicament medicament : medicaments) {
                    medicamentDao.update(medicament);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context,DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
