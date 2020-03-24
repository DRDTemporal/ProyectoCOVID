package com.proyecto.asn.ccovid19.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proyecto.asn.ccovid19.R;

public class Preguntas extends AppCompatActivity implements View.OnClickListener {
    LinearLayout primeraPregunta, SegundaPregunta,segundaPregunta2, TerceraPregunta, CuartaPregunta;
    Button btnSi, btnNo,btnSi2, btnNo2,btnSi21, btnNo21,btnSi3, btnNo3, btnSi4, btnNo4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);
        inicializar();

    }

    //METODO PARA INICIALIZAR VALORES
    private void inicializar() {

        primeraPregunta = findViewById(R.id.PrimeraPregunta);
        SegundaPregunta = findViewById(R.id.SegundaPregunta);
        segundaPregunta2 = findViewById(R.id.SegundaPregunta2);
        TerceraPregunta = findViewById(R.id.terceraPregunta);
        CuartaPregunta = findViewById(R.id.cuartaPregunta);
        btnSi = findViewById(R.id.btnSi);
        btnNo = findViewById(R.id.btnNo);
        btnSi2 = findViewById(R.id.btnSi2);
        btnNo2 = findViewById(R.id.btnNo2);
        btnSi21 = findViewById(R.id.btnSi21);
        btnNo21 = findViewById(R.id.btnNo21);
        btnSi3 = findViewById(R.id.btnSi3);
        btnNo3 = findViewById(R.id.btnNo3);
        btnSi4 = findViewById(R.id.btnSi4);
        btnNo4 = findViewById(R.id.btnNo4);
        btnSi.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        btnSi2.setOnClickListener(this);
        btnNo2.setOnClickListener(this);
        btnSi21.setOnClickListener(this);
        btnNo21.setOnClickListener(this);
        btnSi3.setOnClickListener(this);
        btnNo3.setOnClickListener(this);
        btnSi4.setOnClickListener(this);
        btnNo4.setOnClickListener(this);


        //OCULTAR PREGUNTAS
        SegundaPregunta.setVisibility(View.INVISIBLE);
        segundaPregunta2.setVisibility(View.INVISIBLE);
        TerceraPregunta.setVisibility(View.INVISIBLE);
        CuartaPregunta.setVisibility(View.INVISIBLE);
        btnSi2.setVisibility(View.INVISIBLE);
        btnNo2.setVisibility(View.INVISIBLE);
        btnSi21.setVisibility(View.INVISIBLE);
        btnNo21.setVisibility(View.INVISIBLE);
        btnSi3.setVisibility(View.INVISIBLE);
        btnNo3.setVisibility(View.INVISIBLE);
        btnSi4.setVisibility(View.INVISIBLE);
        btnNo4.setVisibility(View.INVISIBLE);
        btnSi2.setEnabled(false);
        btnNo2.setEnabled(false);
        btnSi21.setEnabled(false);
        btnNo21.setEnabled(false);
        btnSi3.setEnabled(false);
        btnNo3.setEnabled(false);
        btnSi4.setEnabled(false);
        btnNo4.setEnabled(false);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSi:
                Intent intent = new Intent(Preguntas.this, Resultados.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btnNo:
                SegundaPregunta.setVisibility(View.VISIBLE);
                btnSi2.setVisibility(View.VISIBLE);
                btnNo2.setVisibility(View.VISIBLE);
                btnSi2.setEnabled(true);
                btnNo2.setEnabled(true);

                break;

            case R.id.btnSi2:
                segundaPregunta2.setVisibility(View.VISIBLE);
                btnSi21.setVisibility(View.VISIBLE);
                btnNo21.setVisibility(View.VISIBLE);
                btnNo21.setEnabled(true);
                btnNo21.setEnabled(true);
                break;


            case R.id.btnNo2:
                TerceraPregunta.setVisibility(View.VISIBLE);
                btnSi3.setVisibility(View.VISIBLE);
                btnNo3.setVisibility(View.VISIBLE);
                btnNo3.setEnabled(true);
                btnNo3.setEnabled(true);
                break;

            case R.id.btnSi21:
                Intent intent2 = new Intent(Preguntas.this, Resultados.class);
                startActivity(intent2);
                finish();
                break;


            case R.id.btnNo21:
                Intent intent3 = new Intent(Preguntas.this, Resultados.class);
                startActivity(intent3);
                finish();
                break;


            case R.id.btnSi3:
                Intent intent4 = new Intent(Preguntas.this, Resultados.class);
                startActivity(intent4);
                finish();
                break;

            case R.id.btnNo3:
                CuartaPregunta.setVisibility(View.VISIBLE);
                btnSi4.setVisibility(View.VISIBLE);
                btnNo4.setVisibility(View.VISIBLE);
                btnNo4.setEnabled(true);
                btnNo4.setEnabled(true);
                break;

            case R.id.btnSi4:

                Intent intent5 = new Intent(Preguntas.this, Resultados.class);
                startActivity(intent5);
                finish();
                break;

            case R.id.btnNo4:
                Intent intent6 = new Intent(Preguntas.this, Resultados.class);
                startActivity(intent6);
                finish();
                break;


        }

    }
}
