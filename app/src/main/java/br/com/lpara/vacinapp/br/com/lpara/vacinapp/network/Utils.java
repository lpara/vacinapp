package br.com.lpara.vacinapp.br.com.lpara.vacinapp.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.lpara.vacinapp.br.com.lpara.vacinapp.recursos.DoencaRSC;
import br.com.lpara.vacinapp.br.com.lpara.vacinapp.recursos.VacinaRSC;

/**
 * Created by LPAra on 19/11/2017.
 */

public class Utils {

    public DoencaRSC getDoencasAPI(String urlAPI){
        try{
            DoencaRSC doenca = new DoencaRSC();

            JSONArray jsonArray = new JSONArray(getInformacoesServidor(urlAPI));
            JSONObject objArray = jsonArray.getJSONObject(0);

            doenca.setId(objArray.getString("id"));
            doenca.setNome(objArray.getString("nome"));

            VacinaRSC vacinaDoenca = new VacinaRSC();
            JSONObject objVacina = objArray.getJSONObject("vacina");
            vacinaDoenca.setId(objVacina.getString("id"));
            vacinaDoenca.setNome(objVacina.getString("nome"));
            vacinaDoenca.setRenovavel(objVacina.getString("renovavel"));

            doenca.setVacina(vacinaDoenca);

            return doenca;

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }


    private String getInformacoesServidor(String url){
        String json = NetworkUtils.getResourceFromAPI(url);
        //Log.i("Resultado",json);
        return json;
    }


}
