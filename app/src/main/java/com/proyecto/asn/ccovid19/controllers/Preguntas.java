package com.proyecto.asn.ccovid19.controllers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.asn.ccovid19.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Preguntas extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_LOCATION = 0;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int LOCATION_REQUEST = 500;

    LinearLayout primeraPregunta, SegundaPregunta,segundaPregunta2, TerceraPregunta, CuartaPregunta;
    Button btnSi, btnNo,btnSi2, btnNo2,btnSi21, btnNo21,btnSi3, btnNo3, btnSi4, btnNo4;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    public static int caso =0;
    Location location;
    String horaFechaServidor="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);
        inicializar();
        inicializarFirebase();
        /*StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old).permitNetwork().build());*/


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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION);
        {
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return;
            }

            try {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                assert locationManager != null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) { }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) { }

                    @Override
                    public void onProviderEnabled(String provider) { }

                    @Override
                    public void onProviderDisabled(String provider) { }
                });
                location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                obtenerHoraDispositivo();

            } catch (Exception ex) {
                Toast.makeText(this, R.string.error_ubicacion, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void obtenerHoraDispositivo() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date date = cal.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            horaFechaServidor = format1.format(date);
            guardarDatos();
        } catch (Exception e1) {
            Toast.makeText(this, R.string.reintente_encuesta, Toast.LENGTH_SHORT).show();


        }

    }

    /*private void obtenerHoraFechaServidor() {

        String urlLugares =LINK_API_WORLD_TIME+LINK_HORA_COLOMBIANA;
        URL url;
        HttpURLConnection connection;
        try {
            url = new URL(urlLugares);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputLine="";

            while ((inputLine=reader.readLine())!=null){
                response.append(inputLine);
            }
            String json = response.toString();
            HoraFecha horaFecha = new Gson().fromJson(json, HoraFecha.class);;
            horaFechaServidor = horaFecha.getDatetime();
            guardarDatos();

        }catch (Exception e){
            Log.e("Error en consumo",e.getMessage());
            Toast.makeText(this, "No tienes acceso a internet, por favor conectese a una", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void guardarDatos() {
        try {
            mDatabase.child("persona").child(Objects.requireNonNull(mAuth.getUid())).child("caso").setValue(caso);
            mDatabase.child("persona").child(Objects.requireNonNull(mAuth.getUid())).child("latitud").setValue(String.valueOf(location.getLatitude()));
            mDatabase.child("persona").child(Objects.requireNonNull(mAuth.getUid())).child("logitud").setValue(String.valueOf(location.getLongitude()));
            mDatabase.child("persona").child(Objects.requireNonNull(mAuth.getUid())).child("fechaDatos").setValue(horaFechaServidor);
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

            task.addOnFailureListener(this, e -> {
                if (e instanceof ResolvableApiException) {
                    try {

                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(Preguntas.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException ignored1) {
                    }
                }
            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    obtenerUbicacion();
                }

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                {
                    obtenerUbicacion();
                    break;
                }
                case Activity.RESULT_CANCELED:
                {
                    Toast.makeText(this, R.string.activar_gps, Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                {
                    break;
                }
            }
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
