package br.com.lpara.vacinapp.principais;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.lpara.vacinapp.R;
import br.com.lpara.vacinapp.recursos.DoencaRSC;
import br.com.lpara.vacinapp.recursos.DoseRSC;

public class InserirDoseActivity extends MinhasVacinaActivity {

    //Elementos da View
    Button btnInserirDose;
    Button btnCancelar;
    EditText iptnIndice;
    CalendarView dataDose;
    TextView txtContadorDoses;

    //Variaveis Auxiliares
    private Date dataAux = new Date();
    private static List<Object> dosesInseridas = new ArrayList<Object>();
    private HashMap<String,List<Object>> mapaDados = new HashMap<String, List<Object>>();
    private Integer contadorNumeroDosesMax = 0;


    //List<DoseRSC> dosesInseridas = new ArrayList<DoseRSC>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_dose);
        btnInserirDose = (Button) findViewById(R.id.btnInserirDose);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        iptnIndice = (EditText) findViewById(R.id.iptnId);
        dataDose = (CalendarView) findViewById(R.id.caledarDose);
        txtContadorDoses = (TextView) findViewById(R.id.txtContadorDoses);

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
                dataAux = new Date(view.getDate());
            }
        });
    }

    public void inserirDose(View v) {
        if (contadorNumeroDosesMax > 0){
            DoseRSC doseInserida = new DoseRSC();
            doseInserida.setNumeracao(Integer.valueOf(iptnIndice.getText().toString()));
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

    public void cancelarOperacao(View v){
        mapaDados.put("doses", dosesInseridas);
        Intent inte = new Intent(InserirDoseActivity.this, MinhasVacinaActivity.class);
        inte.putExtra("dadosRecebidos", mapaDados);
        startActivity(inte);
    }
}
