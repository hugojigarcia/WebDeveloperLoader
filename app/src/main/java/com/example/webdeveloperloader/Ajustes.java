package com.example.webdeveloperloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Ajustes extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private EditText et_url, et_tiempoRecarga;
    private String url;
    private float tiempoRecarga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        initComponents();
    }

    private void initComponents(){
        ocultarBarra();
        getAjustesGuardados();
        setAjustesGuardados();
    }

    private void ocultarBarra(){
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    private void getAjustesGuardados(){
        SharedPreferences archivoAjustes = getSharedPreferences("ajustes", Context.MODE_PRIVATE);
        editor = getSharedPreferences("ajustes", Context.MODE_PRIVATE).edit();
        this.url = archivoAjustes.getString("url", "" );
        this.tiempoRecarga = archivoAjustes.getFloat("tiempoRecarga", 5.0f );
    }

    private void setAjustesGuardados() {
        et_url = (EditText) findViewById(R.id.et_url);
        et_url.setText(url);

        et_tiempoRecarga = (EditText) findViewById(R.id.et_tiempoRecarga);
        et_tiempoRecarga.setText(""+tiempoRecarga);
    }




    public void onclick_guardar(View view) {
        editor.putString("url", et_url.getText().toString()).commit();
        editor.putFloat("tiempoRecarga", Float.parseFloat(et_tiempoRecarga.getText().toString())).commit();

        Toast.makeText(this, "Ajustes guardados", Toast.LENGTH_SHORT).show();
    }

    public void onclick_volver(View view){
        cargar_pagina_principal();
    }

    @Override
    public void onBackPressed()
    {
        cargar_pagina_principal();
    }

    private void cargar_pagina_principal(){
        startActivity(new Intent(this, MainActivity.class));
    }
}