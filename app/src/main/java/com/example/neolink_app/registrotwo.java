package com.example.neolink_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

public class registrotwo extends AppCompatActivity {

    private TextInputEditText codigo;
    private TextInputLayout laycodigo;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "registrotwo";
    private int codigover = new Random().nextInt((999999-100000)-1) + 100000;

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


    public boolean verificarcodigo(String codigo, String codigover){
        //return codigo.equals(codigover);
        return true;
    }

    public void siguiente(View view){
        if(verificarcodigo(codigo.getText().toString(),String.valueOf(codigover))) {
            Intent ione = getIntent();
            Bundle extras = ione.getExtras();
            Bundle paqueteregistro = new Bundle();
            paqueteregistro.putString("correo",extras.getString("correo"));
            paqueteregistro.putString("passw",extras.getString("passw"));
            crearenfirebase(extras.getString("correo"),extras.getString("passw"));

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(extras.getString("correo"), extras.getString("passw"));

            /*user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d(TAG, "User re-authenticated.");
                }
            });*/

            Intent itwo = new Intent(this, registrothree.class);
            itwo.putExtras(paqueteregistro);
            startActivity(itwo);
        } else Toast.makeText( this, "Codigo Invalido", Toast.LENGTH_SHORT).show();
    }

    public void crearenfirebase (String correo, String password){
        mAuth.createUserWithEmailAndPassword(correo, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "createUserWithEmail:success");

                        } else {
                            //Log.d(TAG, "createUserWithEmail:fail");
                        }
                    }
                });
    }
    public void volver2(View view){
        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


}
