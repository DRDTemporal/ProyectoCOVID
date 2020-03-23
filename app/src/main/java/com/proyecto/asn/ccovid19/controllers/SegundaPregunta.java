package com.proyecto.asn.ccovid19.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.proyecto.asn.ccovid19.R;

public class SegundaPregunta extends AppCompatActivity implements View.OnClickListener {
    LinearLayout txtPregunta;
    Button btnSi, btnNo, btnSi2, btnNo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_pregunta);
        inicializar();
    }

    private void inicializar() {

        txtPregunta = findViewById(R.id.txtSegundaPregunta);
        btnSi = findViewById(R.id.btnSi);
        btnNo = findViewById(R.id.btnNo);
        btnSi2 = findViewById(R.id.btnSi2);
        btnNo2 = findViewById(R.id.btnNo2);
        btnSi.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        btnSi2.setOnClickListener(this);
        btnNo2.setOnClickListener(this);
        txtPregunta.setVisibility(View.INVISIBLE);
        btnSi2.setVisibility(View.INVISIBLE);
        btnNo2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSi:
                txtPregunta.setVisibility(View.VISIBLE);
                btnSi2.setVisibility(View.VISIBLE);
                btnNo2.setVisibility(View.VISIBLE);
                break;


            case R.id.btnNo:

                break;

            case R.id.btnSi2:


                break;


            case R.id.btnNo2:


                break;


        }
    }
}
