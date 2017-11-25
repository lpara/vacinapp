package br.com.lpara.vacinapp.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.lpara.vacinapp.principais.MinhasVacinaActivity;
import br.com.lpara.vacinapp.recursos.DoencaRSC;
import br.com.lpara.vacinapp.recursos.VacinaRSC;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LPAra on 19/11/2017.
 */

public class APIDoenca {


    public List<DoencaRSC> buscarDoencasDaAPI(String urlAPI){

        final List<DoencaRSC> result = new ArrayList<DoencaRSC>();

        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(urlAPI)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        APIDoencaInterface apiDoenca = retrofit.create(APIDoencaInterface.class);

        Call<List<DoencaRSC>> doencasCall = apiDoenca.getDoencasServ();
        doencasCall.enqueue(new Callback<List<DoencaRSC>>() {
            @Override
            public void onResponse(Call<List<DoencaRSC>> call, Response<List<DoencaRSC>> response) {
                List<DoencaRSC> doencas = response.body();
                result.addAll(doencas);

            }

            @Override
            public void onFailure(Call<List<DoencaRSC>> call, Throwable t) {
                Log.e("MinhasVacinaActivity",t.toString());
            }
        });
        return result;
    }


}
