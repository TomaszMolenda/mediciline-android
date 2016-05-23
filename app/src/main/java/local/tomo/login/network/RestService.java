package local.tomo.login.network;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

/**
 * Created by tomo on 2016-05-02.
 */
public class RestService extends RetrofitGsonSpiceService {

//    private final static String BASE_URL = "http://176.111.147.66:8080";
    private final static String BASE_URL = "http://83.19.31.10:8080";
//    private final static String BASE_URL = "http://212.244.79.82:8080";


    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(RestIntefrace.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }
}
