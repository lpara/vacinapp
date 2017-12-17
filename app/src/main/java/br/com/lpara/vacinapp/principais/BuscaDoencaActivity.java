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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import br.com.lpara.vacinapp.R;
import br.com.lpara.vacinapp.network.APIDoencaInterface;
import br.com.lpara.vacinapp.network.RetrofitService;
import br.com.lpara.vacinapp.recursos.DoencaRSC;
import br.com.lpara.vacinapp.recursos.VacinaRSC;
import br.com.lpara.vacinapp.recursos.VacinacaoRSC;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaDoencaActivity extends AppCompatActivity {

    @BindView(R.id.actvDoencas)
    public AutoCompleteTextView actViewDoencas;
    private Map<String,DoencaRSC> mapaDoenca = new HashMap<String,DoencaRSC>();
    private HashMap<String,List<Object>> mapaDados = new HashMap<String,List<Object>>();

    //Ip localhost no Android = 10.0.2.2, mesmo que http://localhost:8080
    public static final String urlAPI = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_doenca);
        ButterKnife.bind(this);
        actViewDoencas.setThreshold(3);
        popularDadosDoencas();

        if(actViewDoencas.getText().length() < 3){
            Toast.makeText(getApplicationContext(), "Digite pelo menos 3 caracteres.", Toast.LENGTH_SHORT);
        }

        if(getIntent().hasExtra("dadosEnviados")){
            mapaDados = (HashMap<String,List<Object>>) getIntent().getSerializableExtra("dadosEnviados");
        }

        actViewDoencas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String doencaSelecionada = (String) adapterView.getItemAtPosition(i);
                DoencaRSC doencaAssociada = mapaDoenca.get(doencaSelecionada);
                List<Object> doencasAux = new ArrayList<Object>();
                doencasAux.add(doencaAssociada);
                mapaDados.put("doenca",doencasAux);
                Intent inte = new Intent(BuscaDoencaActivity.this, MinhasVacinaActivity.class);
                inte.putExtra("dadosRecebidos", mapaDados);
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
                        mapaDoenca.put(doenca.getNome(), doenca);
                    }
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            actViewDoencas.setAdapter(arrayAdDoenca);
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
