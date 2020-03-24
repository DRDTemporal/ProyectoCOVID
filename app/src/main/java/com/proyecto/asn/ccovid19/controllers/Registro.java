package com.proyecto.asn.ccovid19.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.asn.ccovid19.R;
import com.proyecto.asn.ccovid19.models.Lugar;
import com.proyecto.asn.ccovid19.models.Persona;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.View.*;
import static com.proyecto.asn.ccovid19.utilities.Constants.TIPO_ID;

public class Registro extends AppCompatActivity implements OnClickListener, OnItemSelectedListener {

    private EditText txtEmail, txtContrasena, txtConfirmarContrasena, txtNombres, txtApellidos;
    private EditText txtEdad, txtIdentificacion, txtDireccion, txtTelefono;
    private Spinner spDepartamento, spMunicipio, spTIpoId;
    private Button btnRegistrar, btnCancelar;
    private ProgressBar pbRegistrar;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private List<Lugar> lugares = new ArrayList<>();
    private List<String> departamentos = new ArrayList<>(), municipios = new ArrayList<>();
    boolean activadoDepartamento = false , activadoMunicipio = false , activadoTipoID = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        inizialite();
        inizialiteFirebase();
        inputDataToSpinners();
    }

    private void inizialite() {
        txtEmail = findViewById(R.id.txtEmail);
        txtContrasena = findViewById(R.id.txtContrasena);
        txtConfirmarContrasena = findViewById(R.id.txtConfirmarContrasena);
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtEdad = findViewById(R.id.txtEdad);
        txtIdentificacion = findViewById(R.id.identificacion);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtTelefono = findViewById(R.id.txtTelefono);
        spDepartamento = findViewById(R.id.spDepartamento);
        spMunicipio = findViewById(R.id.spMunicipio);
        spTIpoId = findViewById(R.id.tipoDeID);
        pbRegistrar = findViewById(R.id.pbRegistrar);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnRegistrar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        spDepartamento.setOnItemSelectedListener(this);
        spMunicipio.setOnItemSelectedListener(this);
        spTIpoId.setOnItemSelectedListener(this);

        spMunicipio.setEnabled(false);
        activadoDepartamento = false;
        activadoMunicipio = false;
        activadoTipoID= false;

    }

    // Método para inicializar la autentificación de Firebase.
    private void inizialiteFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

    }

    private void inputDataToSpinners(){
        Paper.init(this);
        lugares = Paper.book().read("lugares");
        anadirDepartamentos();
        anadirTipoID();
        municipios.add(getString(R.string.select_municipio));
        spMunicipio.setAdapter(obtenerAdaptador(municipios));

    }

    private void anadirDepartamentos() {
        boolean existe;
        departamentos.add(getString(R.string.select_departamento));
        Lugar lugar;
        for ( int i =0; i<lugares.size(); i++ ) {
            lugar = lugares.get(i);
            existe = false;
            String departamento = lugar.getDepartamento();
            for (int j=0; j<departamentos.size(); j++){
                if (departamento.equals(departamentos.get(j))){
                    existe = true;
                    break;
                }
            }
            if (!existe){
                departamentos.add(departamento);
            }

        }

        spDepartamento.setAdapter(obtenerAdaptador(departamentos));

    }

    private void anadirMunicipio(String departamento) {
        Lugar lugar;
        municipios.clear();
        municipios.add(getString(R.string.select_municipio));
        for(int i=0; i<lugares.size(); i++){
            lugar = lugares.get(i);
            if(departamento.equals(lugar.getDepartamento())){
                municipios.add(lugar.getMunicipio());
            }
        }


        spMunicipio.setAdapter(obtenerAdaptador(municipios));
    }

    private void anadirTipoID() {

        spTIpoId.setAdapter(obtenerAdaptador(Arrays.asList(TIPO_ID)));

    }

    private ArrayAdapter<String> obtenerAdaptador(List<String> datos){

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_dropdown_item, datos){
            @Override
            public boolean isEnabled(int position){
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        return  spinnerArrayAdapter;


    }

    // Método el cual hará los procesos para el registro de usuario..
    private void registarUsuario() {
        if (compareToPasswords()) {
            createAccount(txtEmail.getText().toString(),txtContrasena.getText().toString());
        }

    }

    // Método encargado de crear la cuenta.
    private void createAccount(String email, String password) {
        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(Registro.this, "Error en la creación de usuario.",
                            Toast.LENGTH_SHORT).show();

                    updateUI(null);
                }

                hideProgressDialog();
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;
        List<String> campos = new ArrayList<>();

        if (TextUtils.isEmpty(txtEmail.getText().toString())) {
            txtEmail.setError(getString(R.string.este_valor_requerido));
            valid = false;
        } else {
            txtEmail.setError(null);
        }

        if (TextUtils.isEmpty(txtContrasena.getText().toString())) {
            txtContrasena.setError(getString(R.string.este_valor_requerido));
            valid = false;
        } else {
            txtContrasena.setError(null);
        }

        if (TextUtils.isEmpty(txtConfirmarContrasena.getText().toString())) {
            txtConfirmarContrasena.setError(getString(R.string.este_valor_requerido));
            valid = false;
        } else {
            txtConfirmarContrasena.setError(null);
        }

        if (TextUtils.isEmpty(txtNombres.getText().toString())) {
            txtNombres.setError(getString(R.string.este_valor_requerido));
            valid = false;
        } else {
            txtNombres.setError(null);
        }

        if (TextUtils.isEmpty(txtApellidos.getText().toString())) {
            txtApellidos.setError(getString(R.string.este_valor_requerido));
            valid = false;
        } else {
            txtApellidos.setError(null);
        }

        if (TextUtils.isEmpty(txtEdad.getText().toString())) {
            txtEdad.setError(getString(R.string.este_valor_requerido));
            valid = false;
        } else {
            txtEdad.setError(null);
        }

       /* if (TextUtils.isEmpty(txtIdentificacion.getText().toString())) {
            txtIdentificacion.setError(getString(R.string.este_valor_requerido));
            valid = false;
        } else {
            txtIdentificacion.setError(null);
        }*/

        if (TextUtils.isEmpty(txtDireccion.getText().toString())) {
            txtDireccion.setError(getString(R.string.este_valor_requerido));
            valid = false;
        } else {
            txtDireccion.setError(null);
        }

        if (TextUtils.isEmpty(txtTelefono.getText().toString())) {
            txtTelefono.setError(getString(R.string.este_valor_requerido));
            valid = false;
        } else {
            txtTelefono.setError(null);
        }

        if(!activadoDepartamento){
            campos.add("Departamento");
            valid = false;
        }

        if(!activadoMunicipio){
            campos.add("Municipio");
            valid = false;
        }

        if(!activadoTipoID){
            campos.add("Tipo de identificación");
            valid = false;
        }

        String mensaje = "";
        if (campos.size()==1 ){
            mensaje = "El campo "+ campos.get(0) + " no ha sido seleccionado.";
        }else if (campos.size() ==2){
            mensaje = "Los campos "+ campos.get(0) + " y " + campos.get(1)  + " no han sido seleccionados.";
        }else if (campos.size() ==3){
            mensaje = "Los campos "+ campos.get(0) + ", " + campos.get(1)  + "y " + campos.get(2) + " no han sido seleccionados.";
        }

        if(mensaje.length()>0){
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }

        return valid;

    }

    // Método para comparar que las contraseñas coincidan.
    private boolean compareToPasswords(){
        boolean valid = false;

        String password1 = txtContrasena.getText().toString();
        String password2 = txtConfirmarContrasena.getText().toString();

        if (validateForm()){
            if (password1.length()<8){
                txtContrasena.setError(getString(R.string.contrasena_muy_corta));
                Toast.makeText(this, R.string.contrasena_muy_corta_m2, Toast.LENGTH_SHORT).show();
                valid=false;
            }else {

                if (password1.equals(password2)) {
                    valid = true;
                } else {
                    txtConfirmarContrasena.setError(getString(R.string.contrasenas_no_coinciden));
                    txtConfirmarContrasena.setError(getString(R.string.contrasenas_no_coinciden));
                    Toast.makeText(this, R.string.contrasenas_no_coinciden, Toast.LENGTH_SHORT).show();
                    valid = false;
                }
            }

        }else {
            Toast.makeText(this, R.string.revise_datos, Toast.LENGTH_SHORT).show();
        }

        return valid;
    }

    // Método para mostrar el progressDialog como señal de carga e inhabilitar las vistas.
    private void showProgressDialog() {
        pbRegistrar.setVisibility(View.VISIBLE);
        btnRegistrar.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtEmail.setEnabled(false);
        txtContrasena.setEnabled(false);
        txtConfirmarContrasena.setEnabled(false);
        txtNombres.setEnabled(false);
        txtApellidos.setEnabled(false);
        txtEdad.setEnabled(false);
        txtIdentificacion.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtTelefono.setEnabled(false);
        spDepartamento.setEnabled(false);
        spMunicipio.setEnabled(false);
        spTIpoId.setEnabled(false);
        pbRegistrar.setEnabled(false);

    }

    // Método para ocultar el progressDialog como señal de terminación de carga y habilitar las vistas.
    private void hideProgressDialog() {
        pbRegistrar.setVisibility(View.INVISIBLE);
        btnRegistrar.setEnabled(true);
        btnCancelar.setEnabled(true);
        txtEmail.setEnabled(true);
        txtContrasena.setEnabled(true);
        txtConfirmarContrasena.setEnabled(true);
        txtNombres.setEnabled(true);
        txtApellidos.setEnabled(true);
        txtEdad.setEnabled(true);
        txtIdentificacion.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtTelefono.setEnabled(true);
        spDepartamento.setEnabled(true);
        spMunicipio.setEnabled(true);
        spTIpoId.setEnabled(true);
        pbRegistrar.setEnabled(true);

    }

    // Método que permite verificar que el usuario ha sido creado.
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            databaseReference.child("persona").child(user.getUid()).setValue(datosPersona());
            Toast.makeText(this, R.string.mensaje_de_inicio, Toast.LENGTH_SHORT).show();
            finish();

        }

    }

    // Función que devuelve un objeto UserData para cargar los datos de las vistas en ese dicho objeto.
    private Persona datosPersona(){
        Persona data = new Persona();
        data.setEmail(txtEmail.getText().toString());
        data.setNombres(txtNombres.getText().toString());
        data.setApellidos(txtApellidos.getText().toString());
        data.setDireccion(txtDireccion.getText().toString());
        data.setId(txtIdentificacion.getText().toString());
        data.setEdad(txtEdad.getText().toString());
        data.setTelefono(txtTelefono.getText().toString());

        data.setDepartamento(spDepartamento.getSelectedItem().toString());
        data.setMunicipio(spMunicipio.getSelectedItem().toString());
        data.setTipoID(spTIpoId.getSelectedItem().toString());

        return data;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnRegistrar:
                registarUsuario();
                break;
            case R.id.btnCancelar:
                finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spDepartamento:
                if(position>0){
                    anadirMunicipio(departamentos.get(position));
                    spMunicipio.setEnabled(true);
                    activadoDepartamento = true;
                }else {
                    spMunicipio.setEnabled(false);
                    activadoDepartamento = false;
                }
                break;

            case R.id.spMunicipio:
                if(position>0){
                    activadoMunicipio = true;
                }else {
                    activadoMunicipio = false;
                }
                break;

            case R.id.tipoDeID:
                if(position>0){
                    activadoTipoID = true;
                }else {
                    activadoTipoID = false;
                }


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
