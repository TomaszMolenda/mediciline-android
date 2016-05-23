package local.tomo.login.network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import local.tomo.login.model.Medicament;

/**
 * Created by tomo on 2016-05-02.
 */
public class MedicamentsRequest extends RetrofitSpiceRequest<Medicament.List, RestIntefrace> {

    String uniqueId;

    public MedicamentsRequest(String uniqueId) {
        super(Medicament.List.class, RestIntefrace.class);
        this.uniqueId = uniqueId;
    }

    @Override
    public Medicament.List loadDataFromNetwork() throws Exception {
        Medicament.List medicaments = getService().getMedicaments(uniqueId);
        for (Medicament medicament : medicaments) {
            medicament.createDate();
        }
        return medicaments;
    }
}
