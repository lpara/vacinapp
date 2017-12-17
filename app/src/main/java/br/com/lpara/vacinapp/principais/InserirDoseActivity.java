package br.com.lpara.vacinapp.principais;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.lpara.vacinapp.R;
import br.com.lpara.vacinapp.recursos.DoencaRSC;
import br.com.lpara.vacinapp.recursos.DoseRSC;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InserirDoseActivity extends AppCompatActivity {

    //Elementos da View
    @BindView(R.id.btnInserirDose)
    Button btnInserirDose;
    @BindView(R.id.btnCancelar)
    Button btnCancelar;
    @BindView(R.id.iptnId)
    EditText iptnIndice;
    @BindView(R.id.caledarDose)
    CalendarView dataDose;
    @BindView(R.id.txtContadorDoses)
    TextView txtContadorDoses;

    //Variaveis Auxiliares
    private Date dataAux = new Date();
    private static List<Object> dosesInseridas = new ArrayList<Object>();
    private HashMap<String,List<Object>> mapaDados = new HashMap<String, List<Object>>();
    private Integer contadorNumeroDosesMax = 0;
    private String dataFormatada = "";
    private String ano = "";
    private String mes = "";
    private String dia = "";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    //Ip localhost no Android = 10.0.2.2, mesmo que http://localhost:8080
    public static final String urlAPI = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_dose);
        ButterKnife.bind(this);

        if(getIntent().hasExtra("dadosEnviados")){
            mapaDados = (HashMap<String, List<Object>>) getIntent().getSerializableExtra("dadosEnviados");
            if(mapaDados.size() > 0 && mapaDados.containsKey("doenca")){
                List<Object> listaDoencaAux = (List<Object>) mapaDados.get("doenca");
                DoencaRSC doencaAux = (DoencaRSC) listaDoencaAux.get(0);
                contadorNumeroDosesMax = doencaAux.getVacina().getNumeroDoses();
            }
        }

        txtContadorDoses.setText(txtContadorDoses.getText().toString()+ " " + contadorNumeroDosesMax);

        dataDose.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dia = String.valueOf(dayOfMonth);
                mes = String.valueOf(month);
                ano = String.valueOf(year);
                dataAux = new Date(view.getDate());
            }
        });
    }

    @OnClick(R.id.btnInserirDose)
    public void inserirDose(View v) {
        if (contadorNumeroDosesMax > 0){
            DoseRSC doseInserida = new DoseRSC();
            doseInserida.setNumeracao(Integer.valueOf(iptnIndice.getText().toString()));
            try {
                String complete = "00";
                dia = complete.substring(dia.length()) + dia;
                mes = complete.substring(mes.length()) + mes;
                dataFormatada = ano + "-" + mes + "-" + dia;
                dataAux = dateFormat.parse(dataFormatada);
            }catch (ParseException e){
                Toast.makeText(InserirDoseActivity.this, "Erro ao inserir dose. Data no formato inválido.", Toast.LENGTH_SHORT);
            }
            doseInserida.setDataVacinacao(dataAux);
            dosesInseridas.add(doseInserida);
            iptnIndice.setText("");
            contadorNumeroDosesMax -= 1;
            txtContadorDoses.setText("Quantidade restante de doses a inserir: " + contadorNumeroDosesMax);
        }
        if(contadorNumeroDosesMax == 0){
            btnInserirDose.setEnabled(false);
        }
    }

    @OnClick(R.id.btnCancelar)
    public void cancelarOperacao(View v){
        mapaDados.put("doses", dosesInseridas);
        Intent inte = new Intent(InserirDoseActivity.this, MinhasVacinaActivity.class);
        inte.putExtra("dadosRecebidos", mapaDados);
        startActivity(inte);
    }
}
