package com.example.neolink_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neolink_app.clases.LoginFirebase.PCUN;
import com.example.neolink_app.clases.LoginFirebase.UsuarioNeoL;
import com.example.neolink_app.viewmodels.loginviewmodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout layouteins,layoutswei;
    private TextInputEditText user,pass;
    private CheckBox rec;
    private Button botonM;
    private TextView creaC,recuC;
    private ProgressBar loadM;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private loginviewmodel archi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.usuario); //La variable siempre se llama diferente que el id
        pass = findViewById(R.id.contraseña);
        rec = findViewById(R.id.recordarme);
        layouteins = findViewById(R.id.layout1);
        botonM =  findViewById(R.id.botoningreso);
        creaC = findViewById(R.id.crearcuenta);
        recuC= findViewById(R.id.recuperarcontraseña);
        loadM = findViewById(R.id.CargadoMain);
        mAuth = FirebaseAuth.getInstance();
        archi = new ViewModelProvider(this).get(loginviewmodel.class);

        archi.exist.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    PCUN us = archi.info().getValue();
                    if(us!=null) {
                        user.setText(us.getus());
                        pass.setText(us.getcn());
                        rec.setChecked(true);
                    }
                }
            }
        });
        /*
        String[] archivos = fileList(); // "NeoLinkid.txt"
        String Lineaguardada;

        if(archivoexiste(archivos,"NeoLinkid.txt")){
            try {

                InputStreamReader id = new InputStreamReader(openFileInput("NeoLinkid.txt"));
                BufferedReader br = new BufferedReader(id);
                Lineaguardada = br.readLine();
                id.close();
                br.close();
                user.setText(Lineaguardada.substring(0,Lineaguardada.indexOf(" ")));
                pass.setText(Lineaguardada.substring(Lineaguardada.indexOf(" ")+1));
                rec.setChecked(true);
                //logthisshit();

            } catch (IOException ignored){

            }
        }
        */
        /*user.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(user.didTouchFocusSelect()){
                    user.setHint("");
                }
            }
        });*/

    }

    public void logeo(View view){
        logthisshit();
    }
    void logthisshit(){
        setitMain();
        //Verifico si el usuario o password es correcto
        if((user.length()!=0)&&(pass.length()!=0)) {
            setitMain();
            String actual = user.getText().toString() + " " + pass.getText().toString() + '\n';
            archi.Log(user.getText().toString(), pass.getText().toString(), rec.isChecked(), actual, this).observe(this, new Observer<UsuarioNeoL>() {
                @Override
                public void onChanged(UsuarioNeoL usuarioNeoL) {
                    if(usuarioNeoL.Validate()==0) {
                        Intent i = new Intent(MainActivity.this, actividadbase.class);
                        i.putExtra("uid", usuarioNeoL.giveUID());
                        i.putExtra("correo", usuarioNeoL.giveMail());
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        setitbackMain();
                    } else {
                        switch(usuarioNeoL.Validate()){
                            case 1:
                                Toast.makeText(MainActivity.this, "El correo no existe", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(MainActivity.this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Toast.makeText(MainActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        setitbackMain();
                    }
                }
            });

            /*
            mAuth.signInWithEmailAndPassword(user.getText().toString(), pass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                String guardado = "";

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser usuarioF = mAuth.getCurrentUser();

                        if (rec.isChecked()) {
                            guardado = user.getText().toString() + " " + pass.getText().toString() + '\n';

                            try {
                                if (archivoexiste(fileList(), "NeoLinkid.txt")) {
                                    InputStreamReader id = new InputStreamReader(openFileInput("NeoLinkid.txt"));
                                    BufferedReader br = new BufferedReader(id);
                                    String Lineaguardada = br.readLine();
                                    id.close();
                                    br.close();
                                    if (!Lineaguardada.equals(guardado)) {
                                        OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("NeoLinkid.txt", Activity.MODE_PRIVATE));
                                        archivo.write(guardado);
                                        archivo.flush();
                                        archivo.close();
                                    }
                                } else {
                                    OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("NeoLinkid.txt", Activity.MODE_PRIVATE));
                                    archivo.write(guardado);
                                    archivo.flush();
                                    archivo.close();
                                }
                            } catch (IOException ignored) {

                            }
                        }
                        //mandado del intent - necesita paquete

                        Intent i = new Intent(MainActivity.this, actividadbase.class);
                        i.putExtra("uid", usuarioF.getUid());
                        i.putExtra("correo", user.getText().toString());
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        setitbackMain();

                    } else {

                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            Toast.makeText(MainActivity.this, "El correo no existe", Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Toast.makeText(MainActivity.this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                        }
                        setitbackMain();
                        Log.d(TAG, "signInWithEmail:fail");
                    }
                }
            });*/

        }
    }

    void setitMain(){
        user.setFocusable(false);
        pass.setFocusable(false);
        rec.setFocusable(false);
        botonM.setEnabled(false);
        creaC.setEnabled(false);
        recuC.setEnabled(false);
        loadM.setVisibility(View.VISIBLE);
    }
    void setitbackMain(){
        user.setFocusableInTouchMode(true);
        pass.setFocusableInTouchMode(true);
        rec.setFocusable(true);
        botonM.setEnabled(true);
        creaC.setEnabled(true);
        recuC.setEnabled(true);
        loadM.setVisibility(View.GONE);
    }

    public boolean archivoexiste(String direccion[], String nombre){
        for(int i=0;i<direccion.length;i++){
            if(nombre.equals(direccion[i])){
                return true;
            }
        }
        return false;
    }

    /*
    public boolean verificarUserPass(String user,String pass){
        final int[] v = {0};
        mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG,"signInWithEmail:success");
                    FirebaseUser user =mAuth.getCurrentUser();

                } else {

            }
        });
        return v[0] == 1;
    }*/

    public void registro(View view){ //Funcion para registrarse
        startActivity(new Intent(this,registroone.class));
    }
    public void recuperar(View view){ //Funcion de recuperar
        startActivity(new Intent(this,recuperar.class));
    }

    @Override
    public void onRestart() {
        super.onRestart();
        setitbackMain();
    }

}
