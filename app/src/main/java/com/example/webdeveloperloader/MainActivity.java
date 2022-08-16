package com.example.webdeveloperloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private WebView pagina;
    private ImageButton bt_play, bt_pause;
    private boolean recargaAutomatica;
    //private Timer timer;
    private Handler handler;
    private String url;
    private float tiempoRecarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        setAjustesGuardados();
        ocultarBarra();
        initWeb();
        initBotones();
    }

    private void setAjustesGuardados(){
        SharedPreferences archivoAjustes = getSharedPreferences("ajustes", Context.MODE_PRIVATE);
        this.url = archivoAjustes.getString("url", "" );
        this.tiempoRecarga = archivoAjustes.getFloat("tiempoRecarga", 5.0f );
    }

    private void ocultarBarra(){
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    private void initWeb(){
        pagina = (WebView) findViewById(R.id.pagina);
        pagina.setWebViewClient(new WebViewClient());
        pagina.getSettings().setJavaScriptEnabled(true);
        cargarWeb();
    }

    private void cargarWeb(){
        pagina.loadUrl(url);
    }

    private void initBotones() {
        bt_play = (ImageButton) findViewById(R.id.bt_play);
        recargaAutomatica = false;
        bt_pause = (ImageButton) findViewById(R.id.bt_pause);
    }



    public void onclick_reload(View view){
        cargarWeb();
    }

    public void onclick_irAVentanaAjustes(View view){
        startActivity(new Intent(this, Ajustes.class));
    }

    public void onclick_play_pause(View view) {
        cambiarBotonPlayPause();
    }

    private void cambiarBotonPlayPause(){
        if(recargaAutomatica) {
            bt_play.setVisibility(View.VISIBLE);
            bt_pause.setVisibility(View.GONE);
            //Toast.makeText(this, "Parada recarga automática", Toast.LENGTH_SHORT).show(); BORRAR
        } else {
            bt_play.setVisibility(View.GONE);
            bt_pause.setVisibility(View.VISIBLE);
            iniciarRegargaAutomatica();
            //Toast.makeText(this, "Iniciada recarga automática", Toast.LENGTH_SHORT).show(); BORRAR
        }
        recargaAutomatica = !recargaAutomatica;
    }

    private void iniciarRegargaAutomatica() {
        int tiempoRecargaMilisegundos = Math.round(tiempoRecarga*1000);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pagina.reload();
                //Toast.makeText(MainActivity.this, "RECARGANDO", Toast.LENGTH_SHORT).show(); BORRAR
                if(recargaAutomatica) iniciarRegargaAutomatica();
            }
        }, tiempoRecargaMilisegundos);
    }
}