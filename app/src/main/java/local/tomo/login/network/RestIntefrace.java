package local.tomo.login.network;

import java.util.List;

import local.tomo.login.model.MedicamentDb;
import local.tomo.login.model.User;
import local.tomo.login.model.Medicament;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by tomo on 2016-05-02.
 */
public interface RestIntefrace {

    //public static final String url = "http://83.19.31.10:8080";
    public static final String url = "http://212.244.79.82:8080";


    @GET("/api/login/{username}/{password}.json")
    Call<User> user(@Path("username") String username, @Path("password") String password);

    @GET("/api/medicaments/{uniqueId}.json")
    Call<List<Medicament>> getMedicaments(@Path("uniqueId") String uniqueId);

    @GET("/api/medicamentsdb.json")
    MedicamentDb.List getMedicamentsDb();

    @GET("/api/medicamentsdb/count.json")
    Integer getMedicamentsDbCount();

    @POST("/api/medicament/save.json")
    Call<Medicament> saveMedicament(@Body Medicament medicament);
}
