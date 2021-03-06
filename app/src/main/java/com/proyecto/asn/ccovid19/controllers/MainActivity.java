package com.proyecto.asn.ccovid19.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.Person;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.asn.ccovid19.R;
import com.proyecto.asn.ccovid19.models.Persona;

import java.util.Objects;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    //Declaración de variables
    private Button btnLogin;
    private Button btnRegistrar;
    private FirebaseAuth mAuth;
    private EditText txtEmail, txtContrasena;
    private TextView txtOlvidoLaContrasena;
    private DatabaseReference mDatabase;
    Persona persona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        inizialite();
        inizialiteFirebase();
        setOnClickButtons();

    }

    //Método para inicializar las vistas
    private void inizialite() {
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        txtEmail = findViewById(R.id.txtEmail);
        txtContrasena = findViewById(R.id.txtContrasena);
        txtOlvidoLaContrasena = findViewById(R.id.txtOlvidoContrasena);
        txtOlvidoLaContrasena.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        findViewById(R.id.pbLogin).setVisibility(INVISIBLE);
    }

    //Método para inicializar la autentificación de Firebase
    private void inizialiteFirebase() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    //Método para darles acciones a los botones
    private void setOnClickButtons() {
        btnLogin.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        txtOlvidoLaContrasena.setOnClickListener(this);
    }

    //Método para iniciar sesión
    private void signIn(String email, String password) {

        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
                findViewById(R.id.pbLogin).setVisibility(INVISIBLE);

            } else {
                Log.w("Inicio de sesión", "signInWithEmail:failure", task.getException());
                Toast.makeText(MainActivity.this, R.string.el_usuario_no_registrado,
                        Toast.LENGTH_SHORT).show();
                updateUI(null);
                hideProgressDialog();
            }
        });
    }

    //Método para mostrar que la app está cargando
    private void showProgressDialog() {
        findViewById(R.id.pbLogin).setVisibility(VISIBLE);
        btnLogin.setEnabled(false);
        btnRegistrar.setEnabled(false);
        txtEmail.setEnabled(false);
        txtContrasena.setEnabled(false);
    }

    //Método para habilitar las vistas
    private void hideProgressDialog() {
        findViewById(R.id.pbLogin).setVisibility(INVISIBLE);
        btnLogin.setEnabled(true);
        btnRegistrar.setEnabled(true);
        txtEmail.setEnabled(true);
        txtContrasena.setEnabled(true);
    }

    //Método para validar el formulario
    private boolean validateForm() {
        boolean valid = true;

        String email = txtEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            txtEmail.setError(getString(R.string.este_valor_requerido));
            valid = false;
        } else {
            txtEmail.setError(null);
        }

        String password = txtContrasena.getText().toString();
        if (TextUtils.isEmpty(password)) {
            txtContrasena.setError(getString(R.string.este_valor_requerido));
            valid = false;
        } else {
            txtContrasena.setError(null);
        }

        return valid;
    }

    // Método para ingresar a la pantalla de inicio.
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            personaIdentificada();


        } else {
            Toast.makeText(this, "El usuario no está registrado", Toast.LENGTH_SHORT).show();
        }
    }

    //Método para reestablecer contraseña sin loguearse.
    private void reestablecerContrasena() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_reestablecer_contrasena);
        final EditText txtEmail = dialog.findViewById(R.id.txtEmail);
        final Button btnAceptar = dialog.findViewById(R.id.btnAceptar1);
        final Button btnCancelar = dialog.findViewById(R.id.btnCancelar1);

        btnAceptar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                btnAceptar.setEnabled(false);
                btnCancelar.setEnabled(false);
                dialog.setCancelable(false);

                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = txtEmail.getText().toString();

                auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Reestablecer C afuera", "Email sent.");
                            Toast.makeText(MainActivity.this, R.string.se_le_ha_enviado_un_correo, Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                        }else {
                            btnAceptar.setEnabled(false);
                            btnCancelar.setEnabled(false);
                            dialog.setCancelable(false);
                            Toast.makeText(MainActivity.this, R.string.a_ocurrido_un_error_afuera, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.cancel();
            }
        });

        btnLogin.setOnClickListener(v -> dialog.cancel());
        dialog.setCancelable(true);
        dialog.show();

    }

    // Método para la utilización del onClick a los items.
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                String email = txtEmail.getText().toString();
                String contrasena  = txtContrasena.getText().toString();
                signIn(email,contrasena);
                break;

            case R.id.btnRegistrar:
                enviarARegistro();
                break;

            case R.id.txtOlvidoContrasena:
                reestablecerContrasena();
                break;
        }

    }

    private void enviarARegistro() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.condicionesLegales)
                .setTitle(R.string.alerta_titulo);

        builder.setPositiveButton(R.string.aceptar, (dialog, id) -> {
            Intent intent = new Intent(MainActivity.this, Registro.class);
            startActivity(intent);
        });
        builder.setNegativeButton(R.string.cancelar, (dialog, id) -> {
        });

        builder.setNeutralButton(R.string.masInformacion, (dialog, id) -> {
            Intent intent = new Intent(MainActivity.this, PoliticasDePrivacidad.class);
            startActivity(intent);
        });

        builder.setCancelable(true);

        builder.create();
        builder.show();


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
                    startActivity(new Intent(MainActivity.this,Resultados.class));
                    finish();
                }else{
                    startActivity( new Intent(MainActivity.this,Preguntas.class));
                    finish();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
