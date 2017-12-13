package br.com.lpara.vacinapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LPAra on 19/11/2017.
 */

public class RetrofitService {

    public static <T> T criarRetrofitService(final Class<T> classe, final String urlAPI){
        Gson gsonDate = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(urlAPI)
        .addConverterFactory(GsonConverterFactory.create(gsonDate))
        .build();

        T service = retrofit.create(classe);

        return service;
    }

}
