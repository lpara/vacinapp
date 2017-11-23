package br.com.lpara.vacinapp.principais;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.lpara.vacinapp.R;

public class GerenciarVacinaActivity extends AppCompatActivity {

    Button btnMinhasVacina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_vacina);
        btnMinhasVacina = (Button) findViewById(R.id.btnMinhasVacinas);
    }

    public void minhasVacinas(View v){
        Intent in = new Intent(GerenciarVacinaActivity.this, MinhasVacinaActivity.class);
        startActivity(in);
    }
}
