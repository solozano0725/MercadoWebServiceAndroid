package com.example.solmayra.mercadofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnregistrar, btnconsultar, btnlistar, btnacerca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnregistrar  = (Button) findViewById(R.id.btnregistrar);
        btnconsultar = (Button) findViewById(R.id.btnconsultar);
        btnlistar = (Button) findViewById(R.id.btnlistar);
        btnacerca = (Button) findViewById(R.id.btnacerca);

        btnregistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AbrirActivity(RegistrarActivity.class);
            }
        });


        btnconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirActivity(ConsultarActivity.class);
            }
        });

        btnlistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirActivity(ListarActivity.class);
            }
        });

        btnacerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirActivity(AcercaActivity.class);
            }
        });
    }


    public void AbrirActivity(Class activity)
    {
        Intent i=new Intent(this, activity);
        startActivity(i);
    }
}
