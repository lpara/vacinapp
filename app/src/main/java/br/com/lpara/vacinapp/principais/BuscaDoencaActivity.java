package br.com.lpara.vacinapp.principais;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.lpara.vacinapp.R;
import br.com.lpara.vacinapp.network.APIDoencaInterface;
import br.com.lpara.vacinapp.network.RetrofitService;
import br.com.lpara.vacinapp.recursos.DoencaRSC;
import br.com.lpara.vacinapp.recursos.VacinaRSC;
import br.com.lpara.vacinapp.recursos.VacinacaoRSC;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaDoencaActivity extends MinhasVacinaActivity {

    AutoCompleteTextView actvDoencas;
    private Map<String,VacinaRSC> mapaDoenca = new HashMap<String,VacinaRSC>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_doenca);
        actvDoencas = (AutoCompleteTextView) findViewById(R.id.actvDoencas);
        actvDoencas.setThreshold(3);
        popularDadosDoencas();

        if(actvDoencas.getText().length() < 3){
            Toast.makeText(getApplicationContext(), "Digite pelo menos 3 caracteres.", Toast.LENGTH_SHORT);
        }

        actvDoencas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String doencaSelecionada = (String) adapterView.getItemAtPosition(i);
                VacinaRSC vacinaAssociada = mapaDoenca.get(doencaSelecionada);
                iptnDoenca.setText(doencaSelecionada);
                Intent inte = new Intent(BuscaDoencaActivity.this, MinhasVacinaActivity.class);
                inte.putExtra("vacina", vacinaAssociada);
                startActivity(inte);
            }
        });
    }

    private void popularDadosDoencas(){
        final ArrayAdapter<String> arrayAdDoenca = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        RetrofitService apiService = new RetrofitService();
        APIDoencaInterface apiDoenca = apiService.criarRetrofitService(APIDoencaInterface.class, urlAPI);
        Call<List<DoencaRSC>> doencasCall = apiDoenca.getDoencasServ();

        doencasCall.enqueue(new Callback<List<DoencaRSC>>() {
            @Override
            public void onResponse(Call<List<DoencaRSC>> call, Response<List<DoencaRSC>> response) {
                if(response.isSuccessful()) {
                    List<DoencaRSC> doencas = response.body();
                    for(DoencaRSC doenca : doencas) {
                        arrayAdDoenca.add(doenca.getNome());
                        mapaDoenca.put(doenca.getNome(), doenca.getVacina());
                    }
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            actvDoencas.setAdapter(arrayAdDoenca);
                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(), "Erro ao acessar API", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<List<DoencaRSC>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro de acesso a rede", Toast.LENGTH_SHORT);
            }
        });
    }
}
