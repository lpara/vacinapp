package br.com.lpara.vacinapp.principais;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;


import java.util.Date;

import br.com.lpara.vacinapp.R;
import br.com.lpara.vacinapp.recursos.DoseRSC;

public class InserirDoseActivity extends MinhasVacinaActivity {

    Button btnInserirDose;
    Button btnCancelar;
    EditText iptnIndice;
    CalendarView dataDose;
    Date dataAux = new Date();


    //List<DoseRSC> dosesInseridas = new ArrayList<DoseRSC>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_dose);
        btnInserirDose = (Button) findViewById(R.id.btnInserirDose);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        iptnIndice = (EditText) findViewById(R.id.iptnId);
        dataDose = (CalendarView) findViewById(R.id.caledarDose);

        dataDose.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dataAux = new Date(view.getDate());
            }
        });
    }

    public void inserirDose(){
        DoseRSC doseInserida = new DoseRSC();
        doseInserida.setNumeracao(Integer.valueOf(iptnIndice.getText().toString()));
        doseInserida.setDataVacinacao(dataAux);
        MinhasVacinaActivity.dosesInseridas.add(doseInserida);
    }

    public void cancelarOperacao(){
        Intent inte = new Intent(InserirDoseActivity.this, MinhasVacinaActivity.class);
        startActivity(inte);
    }
}
