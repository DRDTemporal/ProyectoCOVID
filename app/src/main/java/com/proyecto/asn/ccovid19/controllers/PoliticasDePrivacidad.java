package com.proyecto.asn.ccovid19.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.source.DocumentSource;
import com.proyecto.asn.ccovid19.R;

public class PoliticasDePrivacidad extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politicas_de_privacidad);
        cargarPdf();
    }

    private void cargarPdf() {
        pdfView = findViewById(R.id.pdf);
        //PDF EN RAW MANDAR A FIREBASE PARA MANDARLO POR URL
        //pdfView.fromUri("");

    }
}
