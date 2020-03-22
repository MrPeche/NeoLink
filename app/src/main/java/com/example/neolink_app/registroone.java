package com.example.neolink_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class registroone extends AppCompatActivity {
    private TextInputEditText correo,passwuno,passwdos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroone);
        correo = (TextInputEditText)findViewById(R.id.correoRecuperar);
        passwuno = (TextInputEditText)findViewById(R.id.ContraseñaRecuperar);
        passwdos = (TextInputEditText)findViewById(R.id.ContraseñaRecuperar2);
    }

    public boolean verificarCorreo(String correo){
        return true;
    }

    public void siguiente(View view){

    }

    public void volver(View view){
        finish();
    }

}
