package com.proyecto.asn.ccovid19.controllers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    TextView txtPregunta;
    Button btnSi, btnNo;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    public static int caso =0;
    Location location;
    String horaFechaServidor="";
    int nPregunta =1;
    ProgressBar pbPreguntas;

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
        txtPregunta = findViewById(R.id.txtpregunta);
        btnSi = findViewById(R.id.btnSi);
        btnNo = findViewById(R.id.btnNo);
        pbPreguntas =findViewById(R.id.pbPreguntas);
        pbPreguntas.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        btnSi.setOnClickListener(this);
        btnNo.setOnClickListener(this);

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
            reiniciarEncuesta();
        });
        builder.setCancelable(true);
        builder.create();
        builder.show();


    }

    private void reiniciarEncuesta(){
        caso=0;
        nPregunta = 1;
        txtPregunta.setText(R.string.pregunta3);
    }

    private void hideProgressBar(){
        pbPreguntas.setVisibility(View.INVISIBLE);

    }

    private void showProgressBar(){
        pbPreguntas.setVisibility(View.VISIBLE);

    }

    private void obtenerUbicacion() {
        showProgressBar();
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
                hideProgressBar();
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
            hideProgressBar();
            reiniciarEncuesta();
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
            hideProgressBar();
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
                        hideProgressBar();
                    }
                }
            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacion();
            }else{
                hideProgressBar();
                Toast.makeText(this, R.string.permiso_gps, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            pbPreguntas.setVisibility(View.INVISIBLE);
            switch (resultCode) {
                case Activity.RESULT_OK:
                {
                    obtenerUbicacion();
                    break;
                }
                case Activity.RESULT_CANCELED:
                {
                    hideProgressBar();
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
                switch (nPregunta){
                    case 1:
                        caso = 6;
                        pasarAResultado();
                        break;

                    case 2:
                        nPregunta= 3;
                        txtPregunta.setText(R.string.pregunta3);
                        break;

                    case 3:
                        caso = 5;
                        pasarAResultado();
                        break;

                    case 4:
                        caso = 3;
                        pasarAResultado();
                        break;

                    case 5:
                        caso = 2;
                        pasarAResultado();
                        break;
                }
                break;

            case R.id.btnNo:
                switch (nPregunta){
                    case 1:
                        nPregunta = 2;
                        txtPregunta.setText(R.string.pregunta2);
                        break;

                    case 2:
                        nPregunta = 4;
                        txtPregunta.setText(R.string.pregunta3);
                        break;

                    case 3:
                        caso = 4;
                        pasarAResultado();
                        break;

                    case 4:
                        nPregunta = 5;
                        txtPregunta.setText(R.string.tienefactoresde);
                        break;

                    case  5:
                        caso = 1;
                        pasarAResultado();
                        break;

                }

                break;


        }

    }

}