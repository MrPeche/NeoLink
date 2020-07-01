package com.example.neolink_app.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.neolink_app.clases.Dias;
import com.example.neolink_app.clases.GPS;
import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.clases.OLDneolinksboleto;
import com.example.neolink_app.clases.OWNERitems;

public class MasterDrawerViewModel extends AndroidViewModel {
    public LiveData<OWNERitems> Usuarioneolinks;
    public LiveData<OLDneolinksboleto> neonodos;
    public LiveData<Horas> datahoy;
    public LiveData<GPS> GPSM;
    private UserInfoRepo appRepo;
    private String uid;

    public MasterDrawerViewModel(@NonNull Application application) {
        super(application);
        appRepo = new UserInfoRepo();
    }
    public void poneruid(String uid){
        this.uid = uid;
    }

    public LiveData<OWNERitems> getLiveNL(){
        Usuarioneolinks = appRepo.dameneolinks(uid);
        return Usuarioneolinks;
    }

    public LiveData<OLDneolinksboleto> getLiveNN(String neolink){
        neonodos = appRepo.damenodos(uid,neolink);
        return neonodos;
    }
    public LiveData<Horas> getLivedaylydata(String neolink, int año, int mes, int dia, String sensor){
        datahoy = appRepo.damedatahoy(neolink,año,mes,dia,sensor); //TENGO QUE ARREGLAR ESTO PARA MAS EQUIPOS!!!!
        return datahoy;
    }
    public LiveData<GPS> getGPS(String neolink){
        GPSM = appRepo.dameGPS(neolink);
        return GPSM;
    }
}
