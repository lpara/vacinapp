package br.com.lpara.vacinapp.principais;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.lpara.vacinapp.R;
import br.com.lpara.vacinapp.network.APIDoencaInterface;
import br.com.lpara.vacinapp.network.APIDoseInterface;
import br.com.lpara.vacinapp.network.APIVacinaInterface;
import br.com.lpara.vacinapp.network.APIVacinacaoInterface;
import br.com.lpara.vacinapp.network.RetrofitService;
import br.com.lpara.vacinapp.recursos.DoencaRSC;
import br.com.lpara.vacinapp.recursos.DoseRSC;
import br.com.lpara.vacinapp.recursos.VacinaRSC;
import br.com.lpara.vacinapp.recursos.VacinacaoRSC;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MinhasVacinaActivity extends AppCompatActivity {

    public static  EditText iptnDoenca;
    private TextView txtRenovavel;
    private EditText iptnLote;

    //private Map<String,Long> mapaVacinas = new HashMap<String,Long>();
    private VacinaRSC vacinaRealizada = new VacinaRSC();
    private static VacinacaoRSC vacinacao = new VacinacaoRSC();
    public static List<DoseRSC> dosesInseridas = new ArrayList<DoseRSC>();

    //ip localhost no Android = 10.0.2.2, mesmo que http://localhost:8080
    public static final String urlAPI = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_vacina);
        iptnDoenca = (EditText) findViewById(R.id.iptnDoenca);
        txtRenovavel = (TextView) findViewById(R.id.textRenovavel);
        txtRenovavel.setVisibility(View.INVISIBLE);
        iptnLote = (EditText) findViewById(R.id.iptnLote);

        if(getIntent().hasExtra("vacina")){
            vacinaRealizada = (VacinaRSC) getIntent().getSerializableExtra("vacina");
            if(vacinaRealizada.getRenovavel()){
                txtRenovavel.setVisibility(View.VISIBLE);
            }
        }


    }

    public void buscarDoenca(){
        Intent inte = new Intent(MinhasVacinaActivity.this, BuscaDoencaActivity.class);
        startActivity(inte);
    }

    public void criarDoses(){
        Intent inteDoses = new Intent(MinhasVacinaActivity.this, InserirDoseActivity.class);
    }

    /*private void popularDadosVacinas(Long idDoenca){
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        RetrofitService apiService = new RetrofitService();
        APIDoencaInterface apiDoenca = apiService.criarRetrofitService(APIDoencaInterface.class, urlAPI);
        Call<VacinaRSC> doencasCall = apiDoenca.buscarVacinaPorDoenca(idDoenca);

        doencasCall.enqueue(new Callback<VacinaRSC>() {
            @Override
            public void onResponse(Call<VacinaRSC> call, Response<VacinaRSC> response) {
                if(response.isSuccessful()){
                    VacinaRSC vacina = response.body();
                    vacinaRealizada = vacina;
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            vacinacao.setVacina(vacinaRealizada);
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Erro ao acessar API", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<VacinaRSC> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro de acesso a rede", Toast.LENGTH_SHORT);
            }
        });
    }*/

    /*public void inserirDoses(){
        if(dosesInseridas.size() > 0){
            RetrofitService apiServiceAux = new RetrofitService();
            APIDoseInterface apiDoses = apiServiceAux.criarRetrofitService(APIDoseInterface.class, urlAPI);
            Call<List<DoseRSC>> doseCall = apiDoses.insertDoseBatch(dosesInseridas);
            doseCall.enqueue(new Callback<List<DoseRSC>>() {
                @Override
                public void onResponse(Call<List<DoseRSC>> call, Response<List<DoseRSC>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Erro no processamento da operação.", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onFailure(Call<List<DoseRSC>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Erro de comunicação com servidor, tente novamente mais tarde.", Toast.LENGTH_SHORT);
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Nenhuma dose foi definida para a vacinação.", Toast.LENGTH_SHORT);
        }

    }*/

    public void inserirVacinacao(){
        if(dosesInseridas.size() > 0) {
            vacinacao.setDoses(dosesInseridas);
            vacinacao.setVacina(vacinaRealizada);
            vacinacao.setLote(iptnLote.getText().toString());
            if(vacinaRealizada.getRenovavel()){

            }


            RetrofitService apiService = new RetrofitService();
            APIVacinacaoInterface apiVacinacao = apiService.criarRetrofitService(APIVacinacaoInterface.class, urlAPI);
            Call<VacinacaoRSC> vacinacaoCall = apiVacinacao.insertVacinacao(vacinacao);
            /*doencaCall.enqueue(new Callback<DoencaRSC>() {
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
            });*/
        }else{
            Toast.makeText(getApplicationContext(), "Nenhuma dose foi definida para a vacinação.", Toast.LENGTH_SHORT);
        }
    }


}
