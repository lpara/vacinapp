package br.com.lpara.vacinapp.network;

import java.util.List;

import br.com.lpara.vacinapp.recursos.VacinacaoRSC;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by LPAra on 06/12/2017.
 */

public interface APIVacinacaoInterface {

    @GET("/vacinacoes")
    Call<List<VacinacaoRSC>> getVacinacaoServ();

    @POST("/vacinacoes/new")
    Call<VacinacaoRSC> insertVacinacao(@Body VacinacaoRSC vacinacao);

    @GET("/vacinacoes/{id}")
    Call<VacinacaoRSC> buscarVacinacaoPorId(@Path("id") Long idVacinacao);


}
