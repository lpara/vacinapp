package br.com.lpara.vacinapp.network;


import java.util.List;

import br.com.lpara.vacinapp.recursos.DoencaRSC;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by LPAra on 18/11/2017.
 */

public interface APIDoencaInterface {

    @GET("/doencas")
    Call<List<DoencaRSC>> getDoencasServ();

    @POST("/new")
    Call<DoencaRSC> insertDoenca(@Body DoencaRSC doenca);

}
