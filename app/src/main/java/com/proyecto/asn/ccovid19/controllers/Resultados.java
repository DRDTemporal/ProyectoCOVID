package com.proyecto.asn.ccovid19.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proyecto.asn.ccovid19.R;

public class Resultados extends AppCompatActivity {

     public LinearLayout contenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        segun_caso();
        inicializar();
    }

    private void segun_caso(){
        switch (Preguntas.caso){
            case 6 :
                setContentView(R.layout.item_caso6);
                break;

            case 5:
                setContentView(R.layout.item_caso5);
                break;

            default:
                setContentView(R.layout.activity_resultados);

        }
    }

    private void inicializar() {
        contenedor = findViewById(R.id.Contenedor);


    }
}
