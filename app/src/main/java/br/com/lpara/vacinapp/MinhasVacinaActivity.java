package br.com.lpara.vacinapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.lpara.vacinapp.br.com.lpara.vacinapp.network.Utils;
import br.com.lpara.vacinapp.br.com.lpara.vacinapp.recursos.DoencaRSC;

public class MinhasVacinaActivity extends AppCompatActivity {

    Spinner spnDoencas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_vacina);
        spnDoencas = (Spinner) findViewById(R.id.spnDoencas);

        List<DoencaRSC> doencas = getDoencasFromServer();
        List<String> doencasStr = new ArrayList<String>();
        for (DoencaRSC doenca : doencas){
            doencasStr.add(doenca.getNome());
        }
        ArrayAdapter<String> arrayAdDoenca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item ,doencasStr);
        spnDoencas.setAdapter(arrayAdDoenca);
    }

    public List<DoencaRSC> getDoencasFromServer(){
        Utils conexao  = new Utils();
        List<DoencaRSC> doencas = new ArrayList<DoencaRSC>();
        //conexao.getDoencasAPI("http://localhost:8080/doencas/2");
        doencas.add(conexao.getDoencasAPI("https://randomuser.me/api/"));
        return doencas;
    }
}
