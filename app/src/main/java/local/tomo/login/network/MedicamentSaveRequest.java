package local.tomo.login.network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import local.tomo.login.model.Medicament;

/**
 * Created by tomo on 2016-05-08.
 */
public class MedicamentSaveRequest extends RetrofitSpiceRequest<Medicament, RestIntefrace> {

    Medicament medicament;

    public MedicamentSaveRequest(Medicament medicament) {
        super(Medicament.class, RestIntefrace.class);
        this.medicament = medicament;
    }

    @Override
    public Medicament loadDataFromNetwork() throws Exception {
        return null;//getService().saveMedicament(medicament);
    }



}
