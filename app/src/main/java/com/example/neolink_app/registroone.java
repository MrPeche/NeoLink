package com.example.neolink_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class registroone extends AppCompatActivity {
    private TextInputEditText correo,passwuno,passwdos;
    private TextInputLayout   cor,pasu,pasd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroone);
        correo = (TextInputEditText)findViewById(R.id.correoRecuperar);
        passwuno = (TextInputEditText)findViewById(R.id.ContraseñaRecuperar);
        passwdos = (TextInputEditText)findViewById(R.id.ContraseñaRecuperartwo);
        cor = (TextInputLayout)findViewById(R.id.layoutcorreoregistroone);
        pasu = (TextInputLayout)findViewById(R.id.layoutcontraseñaregistroone);
        pasd = (TextInputLayout)findViewById(R.id.layoutcontraseñaregistrotwo);

        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwuno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwdos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void siguiente(View view){
        if(eselemailvalido(correo.getText().toString())){
            if(esacontraseñavale(passwuno.getText().toString(),passwdos.getText().toString())) {
                Bundle paqueteregistro = new Bundle();
                paqueteregistro.putString("correo",correo.getText().toString());
                paqueteregistro.putString("passw",passwuno.getText().toString());
                Intent i = new Intent(this, registrotwo.class);
                i.putExtras(paqueteregistro);
            } else Toast.makeText( this, "Contraseña invalida", Toast.LENGTH_SHORT).show();
        } else Toast.makeText( this, "Correo invalido", Toast.LENGTH_SHORT).show();

    }

    public static boolean eselemailvalido(CharSequence target) { //sacado de internet
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
        // Revisar si existe en firebase

    }

    public static boolean esacontraseñavale(String contraein,String contraswei) { //sacado de internet
        if((contraein.length()>=6)&&(contraswei==contraein)){
            return true;
        } else return false;
    }


    public void volver(View view){
        finish();
    }

}
