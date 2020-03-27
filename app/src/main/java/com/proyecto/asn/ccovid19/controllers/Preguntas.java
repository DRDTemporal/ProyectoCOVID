package com.proyecto.asn.ccovid19.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.asn.ccovid19.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Preguntas extends AppCompatActivity implements View.OnClickListener {
    LinearLayout primeraPregunta, SegundaPregunta,segundaPregunta2, TerceraPregunta, CuartaPregunta;
    Button btnSi, btnNo,btnSi2, btnNo2,btnSi21, btnNo21,btnSi3, btnNo3, btnSi4, btnNo4;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    public static int caso =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);
        inicializar();
        inicializarFirebase();

    }

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

    private  void  inicializarFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    private  void pasarAResultado(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.mensaje_confirmacion)
                .setTitle(R.string.alerta_titulo);

        builder.setPositiveButton(R.string.aceptar, (dialog, id) -> {
            startActivity(new Intent(Preguntas.this, Resultados.class));
            guardarCaso();
            finish();
        });
        builder.setNegativeButton(R.string.cancelar, (dialog, id) -> {
        });

        builder.setCancelable(true);

        builder.create();
        builder.show();



    }

    private void guardarCaso() {
        mDatabase.child("persona").child(Objects.requireNonNull(mAuth.getUid())).child("caso").setValue(caso);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSi:
                caso = 6;
                pasarAResultado();
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
                btnSi21.setEnabled(true);
                btnNo21.setEnabled(true);
                break;


            case R.id.btnNo2:
                TerceraPregunta.setVisibility(View.VISIBLE);
                btnSi3.setVisibility(View.VISIBLE);
                btnNo3.setVisibility(View.VISIBLE);
                btnSi3.setEnabled(true);
                btnNo3.setEnabled(true);
                break;

            case R.id.btnSi21:
                caso = 5;
                pasarAResultado();
                break;


            case R.id.btnNo21:
                caso = 4;
                pasarAResultado();
                break;


            case R.id.btnSi3:
                caso = 3;
                pasarAResultado();
                break;

            case R.id.btnNo3:
                CuartaPregunta.setVisibility(View.VISIBLE);
                btnSi4.setVisibility(View.VISIBLE);
                btnNo4.setVisibility(View.VISIBLE);
                btnSi4.setEnabled(true);
                btnNo4.setEnabled(true);
                break;

            case R.id.btnSi4:
                caso = 2;
                pasarAResultado();
                break;

            case R.id.btnNo4:
                caso = 1;
                pasarAResultado();
                break;


        }

    }


}
