package com.example.neolink_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class recuperar extends AppCompatActivity {
    private TextInputEditText correo;
    private Button botonR;
    private TextView botonVR;
    private ProgressBar loadR;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    //private Button recuboton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        correo = (TextInputEditText)findViewById(R.id.correoRecuperar);
        botonR = findViewById(R.id.button_recuperar);
        botonVR = findViewById(R.id.volver);
        loadR = findViewById(R.id.CargadoR);
    }
    public void BotonRecuperar(View view){ //Funcion para recuperar
        //verificar escritirua
        if(correo.length() != 0) {
            setitR();
            auth.sendPasswordResetEmail(correo.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        logradaso();
                        finish();
                    } else {
                        try{
                            throw task.getException();
                        } catch (Exception e){
                            Toast.makeText( recuperar.this, "Correo enviado con exito", Toast.LENGTH_SHORT).show();
                        }
                        falladaso();
                        setitbackR();
                    }
                }
            });
        }
        //Mandar a Correo
    }

    void setitR(){
        botonR.setEnabled(false);
        botonVR.setEnabled(false);
        loadR.setVisibility(View.VISIBLE);
        correo.setFocusable(false);
    }

    void setitbackR(){
        botonR.setEnabled(true);
        botonVR.setEnabled(true);
        loadR.setVisibility(View.GONE);
        correo.setFocusableInTouchMode(true);
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

