package br.com.lpara.vacinapp.principais;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.lpara.vacinapp.R;
import br.com.lpara.vacinapp.network.APIDoencaInterface;
import br.com.lpara.vacinapp.network.APIVacinaInterface;
import br.com.lpara.vacinapp.network.RetrofitService;
import br.com.lpara.vacinapp.recursos.DoencaRSC;
import br.com.lpara.vacinapp.recursos.VacinaRSC;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MinhasVacinaActivity extends AppCompatActivity {

    Spinner spnDoencas;
    Spinner spnVacinas;
    EditText iptNDoenca;
    private Map<String,Long> mapaDoencas = new HashMap<String,Long>();
    private Map<String,Long> mapaVacinas = new HashMap<String,Long>();
    //ip localhost no Android = 10.0.2.2, mesmo que http://localhost:8080
    private static final String urlAPI = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_vacina);
        spnDoencas = (Spinner) findViewById(R.id.spnDoencas);
        spnVacinas = (Spinner) findViewById(R.id.spnVacinas);
        iptNDoenca = (EditText) findViewById(R.id.iptNDoenca);
        popularDadosDoencas();
        popularDadosVacinas();
    }

    private void popularDadosDoencas(){
        final ArrayAdapter<String> arrayAdDoenca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
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
                        mapaDoencas.put(doenca.getNome(), doenca.getId());
                    }
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            spnDoencas.setAdapter(arrayAdDoenca);
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

    private void popularDadosVacinas(){
        final ArrayAdapter<String> arrayAdVacina = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        RetrofitService apiService = new RetrofitService();
        APIVacinaInterface apiVacina = apiService.criarRetrofitService(APIVacinaInterface.class, urlAPI);
        Call<List<VacinaRSC>> vacinasCall = apiVacina.getVacinasServ();

        vacinasCall.enqueue(new Callback<List<VacinaRSC>>() {
            @Override
            public void onResponse(Call<List<VacinaRSC>> call, Response<List<VacinaRSC>> response) {
                if(response.isSuccessful()){
                    List<VacinaRSC> vacinas = response.body();
                    for(VacinaRSC vacina : vacinas){
                        arrayAdVacina.add(vacina.getNome());
                        mapaVacinas.put(vacina.getNome(), vacina.getId());
                    }
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            spnVacinas.setAdapter(arrayAdVacina);
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Erro ao acessar API", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<List<VacinaRSC>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro de acesso a rede", Toast.LENGTH_SHORT);
            }
        });
    }

    public void inserirDoenca(){
        String nomeDoenca = iptNDoenca.getText().toString();
        Long idVacina = mapaVacinas.get(nomeDoenca);
        VacinaRSC vacinaAux = new VacinaRSC();
        vacinaAux.setId(idVacina);
        DoencaRSC novaDoenca = new DoencaRSC();
        novaDoenca.setNome(nomeDoenca);
        novaDoenca.setVacina(vacinaAux);

        RetrofitService apiService = new RetrofitService();
        APIDoencaInterface apiDoenca = apiService.criarRetrofitService(APIDoencaInterface.class, urlAPI);
        Call<DoencaRSC> doencaCall = apiDoenca.insertDoenca(novaDoenca);
        doencaCall.enqueue(new Callback<DoencaRSC>() {
            @Override
            public void onResponse(Call<DoencaRSC> call, Response<DoencaRSC> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Doeça inserida com sucesso.", Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(getApplicationContext(), "Erro ao inserir vacina.", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<DoencaRSC> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro ao acessar a rede.", Toast.LENGTH_SHORT);
            }
        });

    }


}
