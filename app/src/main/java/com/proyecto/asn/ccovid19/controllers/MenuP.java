package com.proyecto.asn.ccovid19.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.proyecto.asn.ccovid19.R;

public class MenuP extends AppCompatActivity implements View.OnClickListener {
    Button btnSi, btnNo;
    TextView txtRiesgo, txtUrgencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        inicializar();
    }

    private void inicializar() {
        btnSi = findViewById(R.id.btnSi);
        btnNo = findViewById(R.id.btnNo);
        btnSi.setOnClickListener(this);
        btnNo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSi:
                Intent intent1 = new Intent(MenuP.this, Resultados.class);
                startActivity(intent1);

                break;

            case R.id.btnNo:
                Intent intent = new Intent(MenuP.this, SegundaPregunta.class);
                startActivity(intent);
                break;
        }
    }
}
