package com.proyecto.asn.ccovid19.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.proyecto.asn.ccovid19.R;
import com.proyecto.asn.ccovid19.models.Imagenes;
import com.proyecto.asn.ccovid19.models.Lugar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Resultados extends AppCompatActivity implements View.OnClickListener {

    public LinearLayout contenedor;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        getSupportActionBar().hide();
        inicializar();
        inicializarFirebase();
        segun_caso();
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
        mStorage = FirebaseStorage.getInstance().getReference();
    }

    private void segun_caso(){

        mDatabase.child("imagenes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                GenericTypeIndicator<ArrayList<Imagenes>> t = new GenericTypeIndicator<ArrayList<Imagenes>>() {};
                List<Imagenes> imagenesList = dataSnapshot.getValue(t);
                if(imagenesList!=null){
                    int contar =0;
                    switch (Preguntas.caso){
                        case 6 :
                            contenedor.addView(getLayoutInflater().inflate(R.layout.item_caso6, null));
                            contar =0;
                            for(int i=0; i < imagenesList.size();i++){
                                if(imagenesList.get(i).getCaso() == Preguntas.caso){
                                    switch (contar){
                                        case  0:
                                            ImageView imageView = contenedor.findViewById(R.id.imgCaso61);
                                            Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView);
                                            contar=1;
                                            break;

                                        case  1:
                                            ImageView imageView2 = contenedor.findViewById(R.id.imgCaso62);
                                            Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView2);
                                            contar=2;
                                            break;


                                        case  2:
                                            ImageView imageView3 = contenedor.findViewById(R.id.imgCaso63);
                                            Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView3);
                                            contar=3;
                                            break;
                                    }
                                }

                            }
                            break;

                        case 5:
                            contenedor.addView(getLayoutInflater().inflate(R.layout.item_caso5, null));
                            contar =0;
                            for(int i=0; i < imagenesList.size();i++){
                                if(imagenesList.get(i).getCaso() == Preguntas.caso){
                                    switch (contar){
                                        case  0:
                                            ImageView imageView = contenedor.findViewById(R.id.imgCaso51);
                                            Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView);
                                            contar=1;
                                            break;

                                        case  1:
                                            ImageView imageView2 = contenedor.findViewById(R.id.imgCaso52);
                                            Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView2);
                                            contar=2;
                                            break;


                                        case  2:
                                            ImageView imageView3 = contenedor.findViewById(R.id.imgCaso53);
                                            Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView3);
                                            contar=3;
                                            break;
                                    }
                                }

                            }
                            break;

                        case 4:
                            contenedor.addView(getLayoutInflater().inflate(R.layout.item_caso4, null));
                            contar =0;
                            for(int i=0; i < imagenesList.size();i++){
                                if(imagenesList.get(i).getCaso() == Preguntas.caso){
                                    switch (contar){
                                        case  0:
                                            ImageView imageView = contenedor.findViewById(R.id.imgCaso41);
                                            Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView);
                                            contar=1;
                                            break;

                                        case  1:
                                            ImageView imageView2 = contenedor.findViewById(R.id.imgCaso42);
                                            Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView2);
                                            contar=2;
                                            break;


                                        case  2:
                                            ImageView imageView3 = contenedor.findViewById(R.id.imgCaso43);
                                            Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView3);
                                            contar=3;
                                            break;
                                    }
                                }

                            }
                            break;

                        case 3:
                            contenedor.addView(getLayoutInflater().inflate(R.layout.item_caso3, null));
                            contar =0;
                            for(int i=0; i < imagenesList.size();i++){
                                if(imagenesList.get(i).getCaso() == Preguntas.caso){
                                    switch (contar){
                                        case  0:
                                            ImageView imageView = contenedor.findViewById(R.id.imgCaso31);
                                            Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView);
                                            contar=1;
                                            break;

                                        case  1:
                                            ImageView imageView2 = contenedor.findViewById(R.id.imgCaso32);
                                            Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView2);
                                            contar=2;
                                            break;


                                        case  2:
                                            ImageView imageView3 = contenedor.findViewById(R.id.imgCaso33);
                                            Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView3);
                                            contar=3;
                                            break;
                                    }
                                }

                            }
                            break;

                        case 2:
                            contenedor.addView(getLayoutInflater().inflate(R.layout.item_caso2, null));
                            for (int i=0; i < imagenesList.size();i++) {
                                if (imagenesList.get(i).getCaso() == Preguntas.caso) {
                                    ImageView imageView = contenedor.findViewById(R.id.imgCaso2);
                                    Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView);
                                    break;
                                }
                            }
                            break;

                        case 1:
                            contenedor.addView(getLayoutInflater().inflate(R.layout.item_caso1, null));
                            for(int i=0; i < imagenesList.size();i++){
                                if(imagenesList.get(i).getCaso() == Preguntas.caso){
                                    ImageView imageView = contenedor.findViewById(R.id.imgCaso1);
                                    Glide.with(Resultados.this).load(imagenesList.get(i).getImagen()).fitCenter().placeholder(R.drawable.loading_spinner).into(imageView);
                                    break;
                                }

                            }
                            break;


                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void obtenerImagen(String url){

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


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void reiniciarEncuesta() {
        mDatabase  = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("persona").child(Objects.requireNonNull(mAuth.getUid())).child("caso").setValue(0);
        startActivity(new Intent(Resultados.this,Preguntas.class));
        finish();

    }

    private void cerrarSesion() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Resultados.this,MainActivity.class));
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
