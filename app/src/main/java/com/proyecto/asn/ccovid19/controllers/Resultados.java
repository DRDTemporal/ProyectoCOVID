package com.proyecto.asn.ccovid19.controllers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.asn.ccovid19.R;

import java.util.Objects;

public class Resultados extends AppCompatActivity implements View.OnClickListener {

     public LinearLayout contenedor;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        getSupportActionBar().hide();
        inicializar();
        segun_caso();
        inicializarFirebase();
    }

    private void inicializar() {
        contenedor = findViewById(R.id.Contenedor);
        findViewById(R.id.btnVolverEncuesta).setOnClickListener(this);
        findViewById(R.id.btnCerrarSesion).setOnClickListener(this);
        findViewById(R.id.btnInformacion).setOnClickListener(this);
    }

    private  void  inicializarFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    private void segun_caso(){
        switch (Preguntas.caso){
            case 6 :
                contenedor.addView(getLayoutInflater().inflate(R.layout.item_caso6, null));
                break;

            case 5:
                contenedor.addView(getLayoutInflater().inflate(R.layout.item_caso5, null));
                break;

            case 4:
                contenedor.addView(getLayoutInflater().inflate(R.layout.item_caso4, null));
                break;

            case 3:
                contenedor.addView(getLayoutInflater().inflate(R.layout.item_caso3, null));
                break;

            case 2:
                contenedor.addView(getLayoutInflater().inflate(R.layout.item_caso2, null));
                break;

            case 1:
                contenedor.addView(getLayoutInflater().inflate(R.layout.item_caso1, null));
                break;


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnVolverEncuesta:
                reiniciarEncuesta();
                break;

            case R.id.btnCerrarSesion:
                cerrarSesion();
                break;

            case R.id.btnInformacion:
                mostrarMasInformacion();
                break;
        }

    }

    private void reiniciarEncuesta() {
        mDatabase  = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("persona").child(Objects.requireNonNull(mAuth.getUid())).child("caso").setValue(0);
        startActivity(new Intent(Resultados.this,MainActivity.class));
        finish();

    }

    private void cerrarSesion() {
        startActivity(new Intent(Resultados.this,MainActivity.class));
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    private void mostrarMasInformacion() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.desarrollado)
                .setTitle(R.string.creditos);

        builder.setPositiveButton(R.string.aceptar, (dialog, id) -> {

        });


        builder.setCancelable(true);

        builder.create();
        builder.show();


    }
}
