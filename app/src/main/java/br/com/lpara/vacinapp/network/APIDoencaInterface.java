package br.com.lpara.vacinapp.network;


import java.util.List;

import br.com.lpara.vacinapp.recursos.DoencaRSC;
import br.com.lpara.vacinapp.recursos.VacinaRSC;
import br.com.lpara.vacinapp.recursos.VacinacaoRSC;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by LPAra on 18/11/2017.
 */

public interface APIDoencaInterface {

    @GET("/doencas")
    Call<List<DoencaRSC>> getDoencasServ();

    @POST("/doencas/new")
    Call<DoencaRSC> insertDoenca(@Body DoencaRSC doenca);

    @GET("/doencas/nome/like/{nome}")
    Call<List<DoencaRSC>> buscarDoencaPorNomeLike (@Path("nome") String nome);

    @GET("/doencas/vacina/{id}")
    Call<VacinaRSC> buscarVacinaPorDoenca(@Path("id") Long idDoenca);

}
