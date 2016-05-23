package local.tomo.login.network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import local.tomo.login.model.MedicamentDb;

/**
 * Created by tomo on 2016-05-02.
 */
public class MedicamentsDbCountRequest extends RetrofitSpiceRequest<Integer, RestIntefrace> {

    public MedicamentsDbCountRequest() {
        super(Integer.class, RestIntefrace.class);
    }

    @Override
    public Integer loadDataFromNetwork() throws Exception {
        return getService().getMedicamentsDbCount();
    }
}
