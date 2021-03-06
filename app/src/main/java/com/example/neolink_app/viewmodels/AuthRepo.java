package com.example.neolink_app.viewmodels;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.neolink_app.MainActivity;
import com.example.neolink_app.actividadbase;
import com.example.neolink_app.clases.LoginFirebase.PCUN;
import com.example.neolink_app.clases.LoginFirebase.UsuarioNeoL;
import com.example.neolink_app.recuperar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executor;

public class AuthRepo {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "Login";
    private final MediatorLiveData<UsuarioNeoL> neousuario = new MediatorLiveData<>();
    private final MediatorLiveData<PCUN> valid = new MediatorLiveData<>();
    private final MediatorLiveData<Boolean> fileexist = new MediatorLiveData<>();
    private final MediatorLiveData<Boolean> Mailrecuperado = new MediatorLiveData<>();

    public LiveData<UsuarioNeoL> Login(final String usuario, final String password, final Boolean checkstatus, final Boolean exist, String actual, Application app, Activity activity){
        final String ac = actual;
        final Application soft = app;

        mAuth.signInWithEmailAndPassword(usuario,password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Log.d(TAG,"signInWithEmail:success");
                    FirebaseUser usuarioF =mAuth.getCurrentUser();

                    if(checkstatus) {
                        String guardado = usuario + " " + password + '\n';
                        if(exist){
                            if(!ac.equals(guardado)) {
                                try {
                                    savedata(soft,guardado);
                                } catch (IOException ignored) { }
                            }
                        } else {
                            try {
                                savedata(soft,guardado);
                            } catch (IOException ignored) { }
                        }

                    }
                    //mandado del intent - necesita paquete
                    String uid = usuarioF.getUid();
                    UsuarioNeoL user = new UsuarioNeoL(uid,usuario);
                    neousuario.setValue(user);

                } else {

                    try{
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){
                        UsuarioNeoL user = new UsuarioNeoL(1);
                        neousuario.setValue(user);
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        UsuarioNeoL user = new UsuarioNeoL(2);
                        neousuario.setValue(user);
                    } catch (Exception e){
                        UsuarioNeoL user = new UsuarioNeoL(3);
                        neousuario.setValue(user);
                    }
                    Log.d(TAG,"signInWithEmail:fail");
                }
            }
        });
        return neousuario;
    }
    public void limpiarelneousuario(){
        neousuario.setValue(new UsuarioNeoL(4));
    }
    public LiveData<Boolean> archivoexiste(String direccion[], String nombre){
        fileexist.setValue(false);
        for (String s : direccion) {
            if (nombre.equals(s)) {
                fileexist.setValue(true);
            }
        }
        return fileexist;
    }
    public LiveData<PCUN> archivoguardado(@NonNull Application application){
        try {
            InputStreamReader id = new InputStreamReader(application.openFileInput("NeoLinkid.txt"));
            BufferedReader br = new BufferedReader(id);
            String Lineaguardada = br.readLine();
            id.close();
            br.close();
            PCUN you = new PCUN(Lineaguardada.substring(0,Lineaguardada.indexOf(" ")),Lineaguardada.substring(Lineaguardada.indexOf(" ")+1));
            valid.setValue(you);


        } catch (IOException e){
            valid.setValue(null);
        }

        return valid;
    }
    private void savedata(@NonNull Application application, String guardado) throws IOException {
        OutputStreamWriter archivo = new OutputStreamWriter(application.openFileOutput("NeoLinkid.txt", Activity.MODE_PRIVATE));
        archivo.write(guardado);
        archivo.flush();
        archivo.close();
    }
    public LiveData<Boolean> recuperarcontraseña(String correo,Activity act){
        mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(act, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Mailrecuperado.setValue(true);
                } else {
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){
                        Mailrecuperado.setValue(false);
                    } catch (Exception e) {
                        Mailrecuperado.setValue(null);
                    }
                }
            }
        });
        return Mailrecuperado;
    }

}
