package br.com.lpara.vacinapp.principais;

import android.app.DatePickerDialog;
import android.content.Intent;
import java.util.Calendar;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.lpara.vacinapp.R;
import br.com.lpara.vacinapp.network.APIVacinacaoInterface;
import br.com.lpara.vacinapp.network.RetrofitService;
import br.com.lpara.vacinapp.recursos.CarteiraRSC;
import br.com.lpara.vacinapp.recursos.DoencaRSC;
import br.com.lpara.vacinapp.recursos.DoseRSC;
import br.com.lpara.vacinapp.recursos.VacinaRSC;
import br.com.lpara.vacinapp.recursos.VacinacaoRSC;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MinhasVacinaActivity extends AppCompatActivity implements View.OnClickListener{

    //Objetos da View
    @BindView(R.id.iptnDoenca)
    public EditText iptnDoenca;
    @BindView(R.id.textRenovavel)
    public TextView txtRenovavel;
    @BindView(R.id.iptnLote)
    public EditText iptnLote;
    @BindView(R.id.btnDataRenovacao)
    public Button btnDataRenovacao;
    @BindView(R.id.btnDoses)
    public Button btnDoses;
    @BindView(R.id.btnInserirVacinacao)
    public Button btnInserirVacinacao;
    @BindView(R.id.btnCancelarOp)
    public Button btnCancelarOp;

    //Variáveis auxiliares
    private HashMap<String,List<Object>> mapaDadosVacinacao = new HashMap<String,List<Object>>();
    private VacinaRSC vacinaRealizada = new VacinaRSC();
    private DoencaRSC doencaSelecionada = new DoencaRSC();
    private VacinacaoRSC vacinacao = new VacinacaoRSC();
    private List<DoseRSC> dosesInseridas = new ArrayList<DoseRSC>();
    private Integer ano, mes, dia;
    private Date dataRenovacao = new Date();
    //private Boolean testActivity = true;

    //Ip localhost no Android = 10.0.2.2, mesmo que http://localhost:8080
    public static final String urlAPI = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_vacina);
        ButterKnife.bind(this);
        txtRenovavel.setVisibility(View.INVISIBLE);
        btnDataRenovacao.setVisibility(View.INVISIBLE);

        if (getIntent().hasExtra("dadosRecebidos")) {
            mapaDadosVacinacao = (HashMap) getIntent().getSerializableExtra("dadosRecebidos");
            if (mapaDadosVacinacao.size() > 0 && mapaDadosVacinacao.containsKey("doenca")) {
                List<Object> listaDoencaAux = mapaDadosVacinacao.get("doenca");
                doencaSelecionada = (DoencaRSC) listaDoencaAux.get(0);
                if (doencaSelecionada != null) {
                    vacinaRealizada = doencaSelecionada.getVacina();
                    iptnDoenca.setText(doencaSelecionada.getNome());
                    if (vacinaRealizada.getRenovavel()) {
                        txtRenovavel.setVisibility(View.VISIBLE);
                        btnDataRenovacao.setVisibility(View.VISIBLE);
                        btnDataRenovacao.setOnClickListener(this);
                    }
                }
            }
            if (mapaDadosVacinacao.size() > 0 && mapaDadosVacinacao.containsKey("doses")) {
                List<Object> listaDosesAux = mapaDadosVacinacao.get("doses");
                for (Object dose : listaDosesAux) {
                    dosesInseridas.add((DoseRSC) dose);
                }
            }
        }
        iptnDoenca.setOnClickListener(this);
        btnDoses.setOnClickListener(this);
        btnInserirVacinacao.setOnClickListener(this);
        btnCancelarOp.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        if(v == btnDataRenovacao) {
            final Calendar c = Calendar.getInstance();
            ano = c.get(Calendar.YEAR);
            mes = c.get(Calendar.MONTH);
            dia = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dataPickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker date, int ano, int mes, int dia){
                            dataRenovacao = new Date(ano,mes,dia);
                        }

                    }, ano, mes, dia);
            dataPickerDialog.show();
        }
        if(v == iptnDoenca){
            Intent inte = new Intent(MinhasVacinaActivity.this, BuscaDoencaActivity.class);
            inte.putExtra("dadosEnviados", mapaDadosVacinacao);
            startActivity(inte);
        }
        if(v == btnDoses){
            Intent inteDoses = new Intent(MinhasVacinaActivity.this, InserirDoseActivity.class);
            inteDoses.putExtra("dadosEnviados", mapaDadosVacinacao);
            startActivity(inteDoses);
        }
        if(v == btnInserirVacinacao){
            if(dosesInseridas.size() > 0) {
                vacinacao = new VacinacaoRSC();
                final Handler mainHandler = new Handler(Looper.getMainLooper());

                //Codigo auxiliar de teste
                CarteiraRSC carteira = new CarteiraRSC();
                carteira.setId(1L);

                vacinacao.setDoses(dosesInseridas);
                vacinacao.setVacina(vacinaRealizada);
                vacinacao.setLote(iptnLote.getText().toString().trim());
                vacinacao.setCarteira(carteira);
                if(vacinaRealizada.getRenovavel()){
                    vacinacao.setDataRenovacao(dataRenovacao);
                }


                RetrofitService apiService = new RetrofitService();
                APIVacinacaoInterface apiVacinacao = apiService.criarRetrofitService(APIVacinacaoInterface.class, urlAPI);
                Call<VacinacaoRSC> vacinacaoCall = apiVacinacao.insertVacinacao(vacinacao);
                vacinacaoCall.enqueue(new Callback<VacinacaoRSC>() {
                    @Override
                    public void onResponse(Call<VacinacaoRSC> call, Response<VacinacaoRSC> response) {
                        if(response.isSuccessful()){
                            final VacinacaoRSC vacinacaoAux = response.body();
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MinhasVacinaActivity.this, "Vacinação da vacina "+ vacinacaoAux.getVacina().getNome() +" foi inserida com sucesso.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Toast.makeText(MinhasVacinaActivity.this, "Erro ao inserir vacinação.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<VacinacaoRSC> call, Throwable t) {
                        Toast.makeText(MinhasVacinaActivity.this, "Erro ao acessar a rede.", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(MinhasVacinaActivity.this, "Nenhuma dose foi definida para a vacinação.", Toast.LENGTH_SHORT).show();
            }
        }
        if(v == btnCancelarOp){
            Intent inteCancelar = new Intent(MinhasVacinaActivity.this, GerenciarVacinaActivity.class);
            startActivity(inteCancelar);
        }

    }

    /*public void buscarDoenca(){
        Intent inte = new Intent(MinhasVacinaActivity.this, BuscaDoencaActivity.class);
        startActivity(inte);
    }*/

    /*public void criarDoses(){
        Intent inteDoses = new Intent(MinhasVacinaActivity.this, InserirDoseActivity.class);
    }*/

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

    /*public void inserirVacinacao(){
        if(dosesInseridas.size() > 0) {
            final Handler mainHandler = new Handler(Looper.getMainLooper());

            vacinacao.setDoses(dosesInseridas);
            vacinacao.setVacina(vacinaRealizada);
            vacinacao.setLote(iptnLote.getText().toString());
            if(vacinaRealizada.getRenovavel()){
                vacinacao.setDataRenovacao(dataRenovacao);
            }


            RetrofitService apiService = new RetrofitService();
            APIVacinacaoInterface apiVacinacao = apiService.criarRetrofitService(APIVacinacaoInterface.class, urlAPI);
            Call<VacinacaoRSC> vacinacaoCall = apiVacinacao.insertVacinacao(vacinacao);
            vacinacaoCall.enqueue(new Callback<VacinacaoRSC>() {
                @Override
                public void onResponse(Call<VacinacaoRSC> call, Response<VacinacaoRSC> response) {
                    if(response.isSuccessful()){
                        final VacinacaoRSC vacinacaoAux = response.body();
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Vacinação da vacina "+ vacinacaoAux.getVacina().getNome() +" inserida com sucesso.", Toast.LENGTH_SHORT);
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), "Erro ao inserir vacinação.", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onFailure(Call<VacinacaoRSC> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Erro ao acessar a rede.", Toast.LENGTH_SHORT);
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Nenhuma dose foi definida para a vacinação.", Toast.LENGTH_SHORT);
        }
    }*/


}
