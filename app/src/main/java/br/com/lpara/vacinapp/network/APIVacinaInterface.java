package br.com.lpara.vacinapp.network;

import java.util.List;

import br.com.lpara.vacinapp.recursos.VacinaRSC;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by LPAra on 28/11/2017.
 */

public interface APIVacinaInterface {

    @GET("/vacinas")
    Call<List<VacinaRSC>> getVacinasServ();

}
