package br.com.lpara.vacinapp.network;

import java.util.List;

import br.com.lpara.vacinapp.recursos.DoseRSC;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by LPAra on 06/12/2017.
 */

public interface APIDoseInterface {

    @GET("/doses")
    Call<List<DoseRSC>> getDosesServ();

    @POST("/doses/new")
    Call<DoseRSC> insertDose(@Body DoseRSC dose);

    @POST("/doses/new/many")
    Call<List<DoseRSC>> insertDoseBatch(@Body List<DoseRSC> doses);

    @GET("/doses/{id}")
    Call<DoseRSC> buscarDoserPorId(@Path("id") Long idDose);
}
