package com.proyecto.asn.ccovid19.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proyecto.asn.ccovid19.R;

public class SegundaPregunta extends AppCompatActivity implements View.OnClickListener {
    TextView txtSegundaPregunta;
    Button btnSi, btnNo, btnSi2, btnNo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_pregunta);
        inicializar();
    }

    private void inicializar() {

        btnSi = findViewById(R.id.btnSi);
        btnNo = findViewById(R.id.btnNo);
        btnSi.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        txtSegundaPregunta = findViewById(R.id.txtSegundaPregunta);
        btnSi2 = findViewById(R.id.btnSi2);
        btnNo2 = findViewById(R.id.btnNo2);
        btnSi2.setOnClickListener(this);
        btnNo2.setOnClickListener(this);
        txtSegundaPregunta.setVisibility(View.INVISIBLE);
        btnSi2.setVisibility(View.INVISIBLE);
        btnNo2.setVisibility(View.INVISIBLE);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSi:
                txtSegundaPregunta.setVisibility(View.VISIBLE);
                btnSi2.setVisibility(View.VISIBLE);
                btnNo2.setVisibility(View.VISIBLE);
                break;

            case R.id.btnNo:
                Intent intent = new Intent(SegundaPregunta.this, TerceraPregunta.class);
                startActivity(intent);
                break;

            case R.id.btnSi2:
                //DIFERENTE RESULTADO
                Intent intent1 = new Intent(SegundaPregunta.this, Resultados.class);
                startActivity(intent1);
                break;


            case R.id.btnNo2:
                //DIFERENTE RESULTADO
                Intent intent2 = new Intent(SegundaPregunta.this, Resultados.class);
                startActivity(intent2);

                break;


        }
    }
}
