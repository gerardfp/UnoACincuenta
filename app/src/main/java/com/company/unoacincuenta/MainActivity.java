package com.company.unoacincuenta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random random;
    Chronometer cronometroJuego;

    Button[] botones;
    boolean[] asignados = new boolean[50];

    int actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();

        cronometroJuego = findViewById(R.id.cronometroJuego);

        botones = new Button[25];

        for (int i = 0; i < 25; i++) {
            botones[i] = findViewById(getResources().getIdentifier("boton" + i, "id", getPackageName()));
        }

        iniciarJuego();
    }

    void iniciarJuego(){

        actual = 1;

        for (int i = 0; i < 50; i++) {
            asignados[i] = false;
        }

        for (int i = 0; i < 25; i++) {
            while(true) {
                int num = random.nextInt(25);

                if(!asignados[num]){
                    botones[i].setText(String.valueOf(num+1));
                    botones[i].setBackgroundColor(Color.parseColor("#4466aa"));
                    asignados[num] = true;
                    break;
                }
            }
        }

        cronometroJuego.setBase(SystemClock.elapsedRealtime());
    }

    public void clicBoton(View view){
        Button boton = (Button) view;

        if(boton.getText().toString().equals(String.valueOf(actual))){

            if(actual == 1){
                cronometroJuego.setBase(SystemClock.elapsedRealtime());
                cronometroJuego.start();
            }

            if(actual == 50) {
                cronometroJuego.stop();

                new AlertDialog.Builder(this)
                        .setTitle("COMPLETADO!")
                        .setMessage("Tiempo: " + cronometroJuego.getContentDescription())
                        .setPositiveButton("Juegar de nuevo", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                iniciarJuego();
                            }
                        })
                        .create()
                        .show();
            } else if(actual <= 25){
                while(true) {
                    int num = random.nextInt(50);

                    if(!asignados[num]){
                        boton.setText(String.valueOf(num+1));
                        boton.setBackgroundColor(Color.parseColor("#335599"));
                        asignados[num] = true;
                        break;
                    }
                }
            } else {
                boton.setVisibility(View.INVISIBLE);
            }

            actual++;
        }
    }
}
