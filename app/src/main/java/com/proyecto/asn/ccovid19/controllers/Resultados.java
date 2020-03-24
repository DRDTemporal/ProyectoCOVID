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
        setContentView(R.layout.activity_resultados);
        inicializar();
    }

    private void inicializar() {
        contenedor = findViewById(R.id.Contenedor);

    }
}
