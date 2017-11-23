package br.com.lpara.vacinapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button btnGerVacina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnGerVacina = (Button) findViewById(R.id.btnGerVacina);
    }

    public void gerVacina(View v) {
        Intent in = new Intent(HomeActivity.this, GerenciarVacinaActivity.class);
        startActivity(in);
    }
}
