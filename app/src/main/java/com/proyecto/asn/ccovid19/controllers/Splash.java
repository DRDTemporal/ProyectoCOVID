package com.proyecto.asn.ccovid19.controllers;

import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.proyecto.asn.ccovid19.R;
import com.proyecto.asn.ccovid19.models.Lugar;
import com.proyecto.asn.ccovid19.utilities.CityService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.proyecto.asn.ccovid19.utilities.Constants.LINK_API;

public class Splash extends AppCompatActivity {

    boolean bandera = true;
    private int valor = 0;
    ImageView imageView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        obtenerLugares();
    }

    // Método para inicializar las vistas y variables.
    private void inizialite(){
        imageView = findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);
        bandera= true;
        valor=0;
    }

    // Método para inicializar Firebase.
    private void inizialiteFirebase() {
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
    }


    private void obtenerLugares() {
        Paper.init(this);
        List<Lugar> lugares = Paper.book().read("lugares");
        if (lugares.size()==0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(LINK_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CityService cityService = retrofit.create(CityService.class);
            Call<List<Lugar>> callLugares = cityService.getLugares();
            callLugares.enqueue(new Callback<List<Lugar>>() {
                @Override
                public void onResponse(Call<List<Lugar>> call, Response<List<Lugar>> response) {
                    if(!response.isSuccessful()){

                        Toast.makeText(Splash.this, "Error al obtener la información. Por favor cierre y abra la app nuevamente", Toast.LENGTH_LONG).show();
                        iniciarSplash();

                    }else{

                        guardarEnLaBaseDeDatos(response.body());

                    }
                }

                @Override
                public void onFailure(Call<List<Lugar>> call, Throwable t) {
                    Toast.makeText(Splash.this, "Error al obtener la información. Por favor cierre y abra la app nuevamente", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            iniciarSplash();
        }


    }

    private void guardarEnLaBaseDeDatos(List<Lugar> lugares) {
        Paper.book().write("lugares", lugares);
    }

    private void iniciarSplash(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (bandera){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            valor++;
                            if (valor==2){
                                animationSplash();
                                bandera=false;
                            }

                        }
                    });
                }
            }
        }).start();

    }

    private void animationSplash(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            try {

                Animator animator = ViewAnimationUtils.createCircularReveal(imageView,0,imageView.getWidth(),0,imageView.getHeight()*1.5f);
                final Animator animator1 = ViewAnimationUtils.createCircularReveal(imageView,imageView.getWidth()/2,imageView.getHeight()/2,imageView.getHeight()*1.5f,0);
                animator.setDuration(800);
                animator1.setDuration(800);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        animator1.start();
                    }
                });

                animator1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        imageView.setVisibility(View.INVISIBLE);
                        super.onAnimationEnd(animation);

                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        updateUI(currentUser);
                    }
                });

                animator.start();


            }catch (Exception e){

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        imageView.setVisibility(View.VISIBLE);
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        updateUI(currentUser);
                    }
                };
                new Timer().schedule(timerTask,1000);

            }


        }else {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    imageView.setVisibility(View.VISIBLE);
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    updateUI(currentUser);
                }
            };
            new Timer().schedule(timerTask,1000);
        }


    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(Splash.this,MainActivity.class);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(Splash.this,MenuP.class);
            startActivity(intent);
            finish();
        }
    }

}
