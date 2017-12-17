package br.com.lpara.vacinapp.principais;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

import br.com.lpara.vacinapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.btnGerVacina)
    Button btnGerVacina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnGerVacina)
    public void gerVacina(View v) {
        Intent in = new Intent(HomeActivity.this, GerenciarVacinaActivity.class);
        startActivity(in);
    }
}
