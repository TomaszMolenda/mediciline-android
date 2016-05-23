package local.tomo.login.network;

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


    @GET("/api/login/{username}/{password}.json")
    User user(@Path("username") String username, @Path("password") String password);

    @GET("/api/medicaments/{uniqueId}.json")
    Medicament.List getMedicaments(@Path("uniqueId") String uniqueId);

    @GET("/api/medicamentsdb.json")
    MedicamentDb.List getMedicamentsDb();

    @GET("/api/medicamentsdb/count.json")
    Integer getMedicamentsDbCount();

    @POST("/api/medicament/save.json")
    Call<Medicament> saveMedicament(@Body Medicament medicament);
}
