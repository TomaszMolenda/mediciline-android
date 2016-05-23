package local.tomo.login.network;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import local.tomo.login.model.Medicament;
import local.tomo.login.model.MedicamentDb;

/**
 * Created by tomo on 2016-05-02.
 */
public class MedicamentsDbRequest extends RetrofitSpiceRequest<MedicamentDb.List, RestIntefrace> {

    public MedicamentsDbRequest() {
        super(MedicamentDb.List.class, RestIntefrace.class);
    }

    @Override
    public MedicamentDb.List loadDataFromNetwork() throws Exception {
        MedicamentDb.List medicamentDbs = getService().getMedicamentsDb();
        return medicamentDbs;
    }
}
