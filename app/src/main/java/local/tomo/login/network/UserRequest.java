package local.tomo.login.network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import local.tomo.login.model.User;

/**
 * Created by tomo on 2016-05-02.
 */
public class UserRequest extends RetrofitSpiceRequest<User, RestIntefrace> {

    private String userName;
    private String password;

    public UserRequest(String userName, String password) {
        super(User.class, RestIntefrace.class);
        this.userName = userName;
        this.password = password;
    }

    @Override
    public User loadDataFromNetwork() throws Exception {
        return getService().user(userName, password);
    }
}
