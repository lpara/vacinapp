package br.com.lpara.vacinapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LPAra on 19/11/2017.
 */

public class RetrofitService {

    public static <T> T criarRetrofitService(final Class<T> classe, final String urlAPI){
        final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(urlAPI)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        T service = retrofit.create(classe);

        return service;
    }

}
