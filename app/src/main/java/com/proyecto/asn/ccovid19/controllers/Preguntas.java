package com.proyecto.asn.ccovid19.controllers;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.asn.ccovid19.R;
import com.proyecto.asn.ccovid19.models.HoraFecha;
import com.proyecto.asn.ccovid19.utilities.WorldTimeService;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.proyecto.asn.ccovid19.utilities.Constants.LINK_API_WORLD_TIME;

public class Preguntas extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_LOCATION = 500;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    LinearLayout primeraPregunta, SegundaPregunta,segundaPregunta2, TerceraPregunta, CuartaPregunta;
    Button btnSi, btnNo,btnSi2, btnNo2,btnSi21, btnNo21,btnSi3, btnNo3, btnSi4, btnNo4;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    public static int caso =0;
    Location location;
    String horaFecheServidor="";

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
            obtenerUbicacion();
        });
        builder.setNegativeButton(R.string.cancelar, (dialog, id) -> {
        });
        builder.setCancelable(true);
        builder.create();
        builder.show();


    }


    private void obtenerUbicacion() {
        LocationManager locationManager;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions
                (this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_LOCATION);
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;

            }

            try {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();

                assert locationManager != null;
                location = locationManager.getLastKnownLocation(Objects.requireNonNull(locationManager.getBestProvider(criteria, false)));
                obtenerHoraFechaServidor();

            } catch (Exception ex) {
                Toast.makeText(this, R.string.error_ubicacion, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void obtenerHoraFechaServidor() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LINK_API_WORLD_TIME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WorldTimeService worldTimeService = retrofit.create(WorldTimeService.class);
        Call<HoraFecha> callWoldTime = worldTimeService.getWorldTime();
        callWoldTime.enqueue(new Callback<HoraFecha>() {
            @Override
            public void onResponse(Call<HoraFecha> call, Response<HoraFecha> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(Preguntas.this, R.string.error_servidor_hora, Toast.LENGTH_LONG).show();
                }else {
                    if (response.body() != null) {
                        horaFecheServidor = response.body().getDatetime();
                        guardarDatos();
                    } else {
                        Toast.makeText(Preguntas.this, R.string.error_servidor_hora, Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<HoraFecha> call, Throwable t) {
                Toast.makeText(Preguntas.this, R.string.error_servidor_hora, Toast.LENGTH_LONG).show();
            }
        });
    }





    private void guardarDatos() {
        try {
            mDatabase.child("persona").child(Objects.requireNonNull(mAuth.getUid())).child("caso").setValue(caso);
            mDatabase.child("persona").child(Objects.requireNonNull(mAuth.getUid())).child("latitud").setValue(String.valueOf(location.getLatitude()));
            mDatabase.child("persona").child(Objects.requireNonNull(mAuth.getUid())).child("logitud").setValue(String.valueOf(location.getLongitude()));
            mDatabase.child("persona").child(Objects.requireNonNull(mAuth.getUid())).child("fechaDatos").setValue(horaFecheServidor);
            startActivity(new Intent(Preguntas.this, Resultados.class));
            finish();
        }catch (Exception ignored){
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(5000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationSettingsRequest.Builder builder;
            builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            Task<LocationSettingsResponse> task =
                    LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

            task.addOnSuccessListener(this, locationSettingsResponse -> {
                obtenerUbicacion();
            });

            task.addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ResolvableApiException) {
                        try {

                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(Preguntas.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException ignored) {
                        }
                    }
                }
            });
        }

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
