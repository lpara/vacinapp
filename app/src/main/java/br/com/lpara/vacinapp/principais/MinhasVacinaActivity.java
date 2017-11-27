package br.com.lpara.vacinapp.principais;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.lpara.vacinapp.R;
import br.com.lpara.vacinapp.network.APIDoenca;
import br.com.lpara.vacinapp.recursos.DoencaRSC;

public class MinhasVacinaActivity extends AppCompatActivity {

    Spinner spnDoencas;
    private Map<Long,String> mapaDoencas = new HashMap<Long,String>();
    //ip localhost no Android = 10.0.2.2, mesmo que http://localhost:8080
    private static final String urlAPI = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_vacina);
        spnDoencas = (Spinner) findViewById(R.id.spnDoencas);
        ArrayAdapter<String> arrayAdDoenca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        popularMapa();
        arrayAdDoenca.addAll(mapaDoencas.values());
        spnDoencas.setAdapter(arrayAdDoenca);
    }

    private void popularMapa(){
        APIDoenca apiDoenca = new APIDoenca();
        List<DoencaRSC> doencasServer = apiDoenca.buscarDoencasDaAPI(urlAPI);
        for(DoencaRSC doenca : doencasServer){
            mapaDoencas.put(doenca.getId(), doenca.getNome());
        }
    }


}
