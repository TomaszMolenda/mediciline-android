package local.tomo.login.network;

import android.os.StrictMode;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tomo on 2016-06-18.
 */
public class RetrofitBuilder {

    public static RestIntefrace getRestIntefrace() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(300, TimeUnit.MILLISECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestIntefrace.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        RestIntefrace restIntefrace = retrofit.create(RestIntefrace.class);

        return restIntefrace;
    }

    public static RestIntefrace getRestIntefrace(Gson gson) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(300, TimeUnit.MILLISECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestIntefrace.url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        RestIntefrace restIntefrace = retrofit.create(RestIntefrace.class);

        return restIntefrace;
    }
}
