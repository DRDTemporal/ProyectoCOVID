package com.proyecto.asn.ccovid19.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.proyecto.asn.ccovid19.R;

public class TerceraPregunta extends AppCompatActivity implements View.OnClickListener {

    Button btnSi, btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercera_pregunta);
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
                Intent intent = new Intent(TerceraPregunta.this, Resultados.class);
                startActivity(intent);
                break;


            case R.id.btnNo:
                Intent intent1 = new Intent(TerceraPregunta.this, CuartaPregunta.class);
                startActivity(intent1);
                break;
        }

    }
}
