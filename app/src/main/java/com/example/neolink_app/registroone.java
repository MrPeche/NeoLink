package com.example.neolink_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.concurrent.Executor;

import static com.google.android.gms.tasks.Tasks.await;

public class registroone extends AppCompatActivity {
    private TextInputEditText correo,passwuno,passwdos;
    private TextInputLayout   cor,pasu,pasd;
    private Button botonS;
    private TextView botonA;
    private ProgressBar load;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "LALA";


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
        botonS = findViewById(R.id.button_registroone);
        botonA = findViewById(R.id.volverRegistro);
        load = findViewById(R.id.Cargado);

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
                if(s.length()>=6){
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

    /*private class Validaciondecorreo extends AsyncTask<String,String,String> {
        String val = "";
        @Override
        protected String doInBackground(String... params){
            try {
                Log.d(TAG, "StartcreateUserWithEmail:success");
                mAuth.createUserWithEmailAndPassword(correo.getText().toString(), passwdos.getText().toString()).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            val = "1";
                        } else {
                            Log.d(TAG, "createUserWithEmail:fail");
                            val = "2";
                        }
                    }
                }).wait();
            } catch (InterruptedException ignored){
                val = "3";
            }
            return val;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(registroone.this,"Espere", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(String... text) {

        }

        @Override
        protected void onPostExecute(String s){
            if(s == "1") {
                Toast.makeText(registroone.this,"Usuario Registrado", Toast.LENGTH_SHORT).show();
                Bundle paqueteregistro = new Bundle();
                paqueteregistro.putString("correo", correo.getText().toString());
                paqueteregistro.putString("passw", passwuno.getText().toString());
                Intent ione = new Intent(registroone.this, registrothree.class);
                ione.putExtras(paqueteregistro);
                startActivity(ione);
            }

        }
    }*/

    public void siguiente(View view){
        //if (!mAuth.isSignInWithEmailLink(correo.getText().toString()))
            if(eselemailvalido(correo.getText().toString())){
                if(esacontrasenavale(passwuno.getText().toString(),passwdos.getText().toString())) {


                    /*Bundle paqueteregistro = new Bundle();
                    paqueteregistro.putString("correo",correo.getText().toString());
                    paqueteregistro.putString("passw",passwuno.getText().toString());*/

                    botonS.setEnabled(false);
                    botonA.setEnabled(false);
                    load.setVisibility(View.VISIBLE);
                    correo.setFocusable(false);
                    passwuno.setFocusable(false);
                    passwdos.setFocusable(false);
                    mAuth.createUserWithEmailAndPassword(correo.getText().toString(), passwuno.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "createUserWithEmail:success");
                                        Bundle paqueteregistro = new Bundle();
                                        paqueteregistro.putString("correo",correo.getText().toString());
                                        paqueteregistro.putString("passw",passwuno.getText().toString());
                                        Toast.makeText(registroone.this,"Usuario Creado", Toast.LENGTH_SHORT).show();
                                        Intent ione = new Intent(registroone.this, registrothree.class);
                                        ione.putExtras(paqueteregistro);
                                        startActivity(ione);
                                    } else {
                                        Log.d(TAG, "createUserWithEmail:fail");
                                        botonS.setEnabled(true);
                                        botonA.setEnabled(true);
                                        correo.setFocusable(true);
                                        passwuno.setFocusable(true);
                                        passwdos.setFocusable(true);
                                        load.setVisibility(View.GONE);
                                        Toast.makeText(registroone.this,"El email ya existe", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    /*Validaciondecorreo validameesta = new Validaciondecorreo();
                    String manyas = "manyas";
                    validameesta.execute(manyas);*/

                    /*crearenfirebase(correo.getText().toString(), passwuno.getText().toString(), new CallBacks() {
                        @Override
                        public void readcallback(int Calli) {

                        }
                    });

                    //Intent ione = new Intent(this, registrotwo.class);
                    Intent ione = new Intent(this, registrothree.class);

                    ione.putExtras(paqueteregistro);
                    startActivity(ione);
                    */
                } else Toast.makeText( this, "Contraseña invalida", Toast.LENGTH_SHORT).show();
            } else Toast.makeText( this, "Email invalido", Toast.LENGTH_SHORT).show();
    }

    //********* Esta funcion ya no es necesaria pero la dejo por si la necesito luego (IGNORAR) ***********
    public void crearenfirebase (String correo, String password){
        /*Task task = mAuth.createUserWithEmailAndPassword(correo, password);
        task.addOnCompleteListener(this, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isComplete()){
                    Log.d(TAG, "createUserWithEmail:success");
                }
            }
        });*/

        try {
            Log.d(TAG, "StartcreateUserWithEmail:success");
            mAuth.createUserWithEmailAndPassword(correo, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");
                            } else {
                                Log.d(TAG, "createUserWithEmail:fail");
                            }
                        }
                    }).wait();
        } catch (InterruptedException ignored){ }
    }


    public boolean eselemailvalido(CharSequence target) { //sacado de internet
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean esacontrasenavale(String contraein,String contraswei) { //sacado de internet
        return (contraein.length() >= 6) && (contraswei.equals(contraein));
    }

    public void volver(View view){
        finish();
    }



}
