package local.tomo.medi.network;

import java.util.List;

import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.Patient;
import local.tomo.medi.ormlite.data.User;
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
//    public static final String url = "http://212.244.79.82:8080";
    public static final String url = "http://212.244.79.82:8085";


    @GET("/api/login")
    Call<User> login();

    @GET("/api/medicaments/{uniqueId}.json")
    Call<List<Medicament>> getMedicaments(@Path("uniqueId") String uniqueId);

    @POST("/api/medicament")
    Call<Medicament> saveMedicament(@Body Medicament medicament);

    @POST("/api/medicaments/save.json")
    Call<List<Medicament>> saveMedicaments(@Body List<Medicament> medicaments);

    @POST("/api/patient")
    Call<Patient> savePatient(@Body Patient patient);
}
