package com.example.neolink_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class recuperar extends AppCompatActivity {
    private TextInputEditText correo;
    //private Button recuboton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        correo = (TextInputEditText)findViewById(R.id.correoRecuperar);
    }

    public void BotonRecuperar(View view){ //Funcion para recuperar
        //verificar escritirua
        if((correo.length() != 0)&&(verificarUser(correo.getText().toString()))){
            Toast.makeText( this, "Correo enviado con exito", Toast.LENGTH_SHORT).show();
            finish();

        } else Toast.makeText( this, "Correo Invalido", Toast.LENGTH_SHORT).show();
        //Mandar a Correo

    }

    public static boolean eselemailvalido(CharSequence target) { //sacado de internet
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void volver(View view){
        finish();
    }

    public boolean verificarUser(String user){  //Verificacion de Usuario
        return true;
    }
}
