package com.example.neolink_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class registrotwo extends AppCompatActivity {

    TextInputEditText codigo;
    TextInputLayout laycodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrotwo);
        codigo = findViewById(R.id.codigorecuperartwo);
        laycodigo = findViewById(R.id.layoutcodigoregistrotwo);
        codigo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==6){
                    laycodigo.setError(null);
                }
                else {
                    laycodigo.setError("El código es de 6 caracteres");
                }
            }
        });
    }


    public boolean verificarcodigo(String codigo){
        return true;
    }

    public void siguiente(View view){

        if(verificarcodigo(codigo.getText().toString())) {
            Intent i = new Intent(this, registrothree.class);
            startActivity(i);
        } else Toast.makeText( this, "Codigo Invalido", Toast.LENGTH_SHORT).show();
    }
    public void volver(View view){
        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
