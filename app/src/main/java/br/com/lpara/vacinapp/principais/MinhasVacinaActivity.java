package br.com.lpara.vacinapp.principais;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.lpara.vacinapp.R;
import br.com.lpara.vacinapp.network.APIDoencaInterface;
import br.com.lpara.vacinapp.network.RetrofitService;
import br.com.lpara.vacinapp.recursos.DoencaRSC;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        popularDados();

    }

    private void popularDados(){
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
                        mapaDoencas.put(doenca.getId(), doenca.getNome());
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


}
