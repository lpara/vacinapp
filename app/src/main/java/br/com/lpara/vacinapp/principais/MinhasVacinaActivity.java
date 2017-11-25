package br.com.lpara.vacinapp.principais;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import br.com.lpara.vacinapp.R;
import br.com.lpara.vacinapp.network.APIDoenca;
import br.com.lpara.vacinapp.recursos.DoencaRSC;

public class MinhasVacinaActivity extends AppCompatActivity {

    Spinner spnDoencas;
    ArrayAdapter<String> arrayAdDoenca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
    private static final String urlAPI = "http://localhost:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_vacina);
        spnDoencas = (Spinner) findViewById(R.id.spnDoencas);

        //populaAdpter();

        spnDoencas.setAdapter(arrayAdDoenca);

    }

    /*private void populaAdpter(){
        APIDoenca apiDoenca = new APIDoenca();
        List<DoencaRSC> doencasServer = apiDoenca.buscarDoencasDaAPI(urlAPI);
        for(DoencaRSC doenca : doencasServer){
            arrayAdDoenca.add(doenca.getNome());
        }

    }*/


}
