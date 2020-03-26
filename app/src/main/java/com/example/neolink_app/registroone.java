package com.example.neolink_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
        correo = findViewById(R.id.correoRecuperar);
        passwuno = findViewById(R.id.ContraseñaRecuperar);
        passwdos = findViewById(R.id.ContraseñaRecuperartwo);
        cor = findViewById(R.id.layoutcorreoregistroone);
        pasu = findViewById(R.id.layoutcontraseñaregistroone);
        pasd = findViewById(R.id.layoutcontraseñaregistrotwo);

        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(eselemailvalido(s.toString())){
                    cor.setError(null);  // "Correo valido"

                } else {
                    cor.setErrorTextAppearance(R.style.camposdeingreso);
                    cor.setError("Correo invalido");
                }
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
                if(s.length()>pasu.getCounterMaxLength()){
                    pasu.setError(null);
                } else pasu.setError("Mínimo 6 caracteres o más");
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
                if((s.length()!=0)&&(passwuno.length()!=0)){
                    if(s.toString().equals(passwuno.getText().toString())) {
                        pasd.setError(null);
                    } else pasd.setError("Repita su contraseña");
                } else pasd.setError("Repita su contraseña");
            }
        });
    }


    public void siguiente(View view){
        if(eselemailvalido(correo.getText().toString())){
            if(esacontrasenavale(passwuno.getText().toString(),passwdos.getText().toString())) {
                Bundle paqueteregistro = new Bundle();
                paqueteregistro.putString("correo",correo.getText().toString());
                paqueteregistro.putString("passw",passwuno.getText().toString());
                Intent ione = new Intent(this, registrotwo.class);
                ione.putExtras(paqueteregistro);
                startActivity(ione);
            } else Toast.makeText( this, "Contraseña invalida", Toast.LENGTH_SHORT).show();
        } else Toast.makeText( this, "Correo invalido", Toast.LENGTH_SHORT).show();

    }

    public static boolean eselemailvalido(CharSequence target) { //sacado de internet
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
        // Revisar si existe en firebase

    }

    public static boolean esacontrasenavale(String contraein,String contraswei) { //sacado de internet
        return (contraein.length() >= 6) && (contraswei.equals(contraein));
    }


    public void volver(View view){
        finish();
    }

}
