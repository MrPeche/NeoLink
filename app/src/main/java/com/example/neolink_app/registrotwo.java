package com.example.neolink_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class registrotwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrotwo);
    }
    public void botonverificarcodigo(View view){

    }
    public void siguiente(View view){
        Intent i = new Intent(this,registrothree.class);
        startActivity(i);
    }
    public void volver(View view){
        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
