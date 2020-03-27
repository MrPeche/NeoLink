package com.example.neolink_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class recuperar extends AppCompatActivity {
    private TextInputEditText correo;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    //private Button recuboton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        correo = (TextInputEditText)findViewById(R.id.correoRecuperar);
    }
    public void BotonRecuperar(View view){ //Funcion para recuperar
        //verificar escritirua
        if(correo.length() != 0){
            auth.sendPasswordResetEmail(correo.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  if(task.isSuccessful()){
                      logradaso();
                  } else falladaso();
                }
            });
            finish();
        } else falladaso();
        //Mandar a Correo
    }
    public void logradaso(){
        Toast.makeText( this, "Correo enviado con exito", Toast.LENGTH_SHORT).show();
    }

    public void falladaso(){
        Toast.makeText( this, "Correo Invalido", Toast.LENGTH_SHORT).show();
    }
    public void volver(View view){
        finish();
    }
}

