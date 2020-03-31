package com.proyecto.asn.ccovid19.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.source.DocumentSource;
import com.proyecto.asn.ccovid19.R;

import static com.proyecto.asn.ccovid19.utilities.Constants.PDF_NAME;

public class PoliticasDePrivacidad extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_politicas_de_privacidad);
        getSupportActionBar().hide();
        cargarPdf();
        closeActivity();
    }

    private void cargarPdf() {
        pdfView = findViewById(R.id.pdf);
        pdfView.fromAsset(PDF_NAME).load();

    }
    private void closeActivity() {
        ImageView imageView = findViewById(R.id.imgSalir);
        imageView.setOnClickListener(v -> {
            finish();
        });
    }
}
