package com.example.neolink_app.viewmodels;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.neolink_app.clases.LoginFirebase.PCUN;
import com.example.neolink_app.clases.LoginFirebase.UsuarioNeoL;

import java.io.FileInputStream;

public class loginviewmodel extends AndroidViewModel {
    private AuthRepo repo;
    public LiveData<Boolean> exist;
    private FileInputStream inputfile;
    private Application app;

    public loginviewmodel(@NonNull Application application) {
        super(application);
        repo = new AuthRepo();
        app = application;
        String[] archivos = application.fileList(); // "NeoLinkid.txt"
        exist = repo.archivoexiste(archivos,"NeoLinkid.txt");
    }
    public LiveData<PCUN> info(){
        return repo.archivoguardado(app);
    }
    public LiveData<UsuarioNeoL> Log(String usuario, String password, Boolean checkstatus, String actual, Activity act){
        return repo.Login(usuario,password,checkstatus,exist.getValue(),actual,app,act);
    }
    public void borrarlavariablelog(){
        repo.limpiarelneousuario();
    }
    public LiveData<Boolean> recuperarcontraseña(String correo, Activity act){
        return repo.recuperarcontraseña(correo,act);
    }

}
