package com.proyecto.asn.ccovid19.controllers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.proyecto.asn.ccovid19.R;
import com.proyecto.asn.ccovid19.models.Persona;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    boolean bandera = true;
    private int valor = 0;
    ImageView imageView;
    private FirebaseAuth mAuth;
    Persona persona;
    private DatabaseReference mDatabase;
    private ProgressBar pbSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        inizialite();
        inizialiteFirebase();
        leerLugares();
    }

    private void inizialite(){
        imageView = findViewById(R.id.imageView);
        pbSplash =findViewById(R.id.pbSplash);
        pbSplash.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        bandera= true;
        valor=0;
    }

    private void inizialiteFirebase() {
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    private void leerLugares() {
        DatabaseReference municipios = mDatabase.child("municipios");
        DatabaseReference imagenes = mDatabase.child("imagenes");

        municipios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imagenes.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        iniciarSplash();
                        pbSplash.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Splash.this, R.string.error_conexion, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Splash.this, R.string.error_conexion, Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void iniciarSplash(){
        new Thread(() -> {
            while (bandera){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(() -> {
                    valor++;
                    if (valor==2){
                        animationSplash();
                        bandera=false;
                    }

                });
            }
        }).start();

    }

    private void animationSplash(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                updateUI(currentUser);
            }
        };
        new Timer().schedule(timerTask,3000);


    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            personaIdentificada();

        } else {
            startActivity( new Intent(Splash.this,MainActivity.class));
            finish();
        }
    }

    private void personaIdentificada(){
        DatabaseReference refPersona = mDatabase.child("persona").child(Objects.requireNonNull(mAuth.getUid()));
        refPersona.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                persona =dataSnapshot.getValue(Persona.class);
                assert persona != null;
                if (persona.getCaso() != 0 ){
                    Preguntas.caso = persona.getCaso();
                    startActivity(new Intent(Splash.this,Resultados.class));
                    finish();
                }else{
                    startActivity( new Intent(Splash.this,Preguntas.class));
                    finish();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
