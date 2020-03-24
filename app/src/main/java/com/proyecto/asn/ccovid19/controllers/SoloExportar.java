package com.proyecto.asn.ccovid19.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
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
import com.opencsv.CSVWriter;
import com.proyecto.asn.ccovid19.R;
import com.proyecto.asn.ccovid19.models.Persona;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.proyecto.asn.ccovid19.utilities.Constants.DATOS_PERSONA;

public class SoloExportar extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private ProgressBar pbSoloExportar;

    private List<Persona> allPersonas = new ArrayList<>();
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_exportar);
        inizialite();
        inizialiteFirebaseApp();

    }


    private void inizialiteFirebaseApp() {
        FirebaseApp.initializeApp(this);
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(false);
            reference = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
        } catch (Exception ignored) {}

    }

    private void  inizialite(){
        pbSoloExportar = findViewById(R.id.pbSoloExportar);
        findViewById(R.id.btnExportar).setOnClickListener(v -> obtenerDatos());
    }

    private void obtenerDatos() {
        pbSoloExportar.setVisibility(View.VISIBLE);
        DatabaseReference refPersona = reference.child("persona");
        refPersona.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allPersonas = new ArrayList<>();
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Persona persona =child.getValue(Persona.class);
                    allPersonas.add(persona);
                    if(allPersonas.size() == dataSnapshot.getChildrenCount()){
                        pedirPermiso();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SoloExportar.this, R.string.error_obtener_datos, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Método para porder escribir y leer elk almacenamiento
    private void pedirPermiso() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }else {
            final FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser!=null){
                exportarEnCSV();
            }

        }

    }

    //Método para exportar un archivo csv de las entregas
    private void exportarEnCSV() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.formato_fecha_exportar));
        String fecha = dateFormat.format(new Date());

        Iterator<Persona> it = allPersonas.iterator();
        List<Persona> tmpDatos = new ArrayList<>();

        File exportDir = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File archivo = new File(exportDir, getString(R.string.app_name)+"-"+fecha+getString(R.string.tipo_archivo));
        try {
            archivo.createNewFile();
        } catch (IOException e) {
            Toast.makeText(this, R.string.error_crear_archivo, Toast.LENGTH_SHORT).show();
        }

        try {

            CSVWriter csvWrite = new CSVWriter(new FileWriter(archivo));
            String titulos [] = DATOS_PERSONA;
            csvWrite.writeNext(titulos);
            for (int i=0; i<tmpDatos.size();i++) {
                Persona tmpPersona = tmpDatos.get(i);
                String arrStr[] = {String.valueOf(
                        tmpPersona.getId()),
                        tmpPersona.getTipoID(),
                        tmpPersona.getNombres(),
                        tmpPersona.getApellidos(),
                        tmpPersona.getDepartamento(),
                        tmpPersona.getMunicipio(),
                        tmpPersona.getEdad(),
                        tmpPersona.getEmail(),
                        tmpPersona.getDireccion(),
                        String.valueOf(tmpPersona.getEstado()),


                };

                csvWrite.writeNext(arrStr);

            }
            csvWrite.close();

            if (tmpDatos.size()<1){
                Toast.makeText(SoloExportar.this, R.string.no_hay_datos_registrados, Toast.LENGTH_SHORT).show();
            }else {
                String mensaje = getString(R.string.carpeta_ccovid_19) + " "+getString(R.string.app_name)+"-"+fecha+getString(R.string.tipo_archivo);
                Toast.makeText(SoloExportar.this, mensaje, Toast.LENGTH_SHORT).show();

            }


        }catch (Exception e){
            Toast.makeText(SoloExportar.this, R.string.no_hay_datos_registrados, Toast.LENGTH_SHORT).show();
        }


    }




    
}
