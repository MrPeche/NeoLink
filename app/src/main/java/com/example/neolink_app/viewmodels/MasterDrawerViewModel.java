package com.example.neolink_app.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.neolink_app.clases.Dias;
import com.example.neolink_app.clases.GPS;
import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.clases.OLDneolinksboleto;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.clases.database_state.horasstate;
import com.example.neolink_app.clases.liveclases.livedaylydatapackage;

public class MasterDrawerViewModel extends AndroidViewModel {
    public LiveData<OWNERitems> Usuarioneolinks;
    public LiveData<OLDneolinksboleto> neonodos;
    public LiveData<Horas> datahoy;
    public LiveData<horasstate> datastatehoy;
    public livedaylydatapackage paquetesdedata = new livedaylydatapackage();
    public LiveData<GPS> GPSM;
    public MediatorLiveData datadiario = new MediatorLiveData<>();
    public boolean valua,valueb = false;
    public final MutableLiveData<String> datechoosen = new MutableLiveData<>();
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
        datahoy = appRepo.damedatahoyK(neolink,año,mes,dia,sensor); //TENGO QUE ARREGLAR ESTO PARA MAS EQUIPOS!!!!
        datastatehoy = appRepo.damedatahoyState(neolink,año,mes,dia,sensor);
        /*datadiario.addSource(datahoy, new Observer() {
            @Override
            public void onChanged(Object o) {
                valua = true;
            }
        });
        */
        //datadiario.addSource();
        //datadiario.addSource();
        return datahoy;
    }
    public void livelydatafull(String neolink, int año, int mes, int dia, String sensor){
        datahoy = appRepo.damedatahoyK(neolink,año,mes,dia,sensor); //TENGO QUE ARREGLAR ESTO PARA MAS EQUIPOS!!!!
        datastatehoy = appRepo.damedatahoyState(neolink,año,mes,dia,sensor);
        paquetesdedata = new livedaylydatapackage<Horas,horasstate>(datahoy,datastatehoy);
        //paquetesdedata = new livedaylydatapackage<Horas,horasstate>(datahoy,datastatehoy);
        //datadiario.addSource();
        //datadiario.addSource();
    }
    public LiveData<GPS> getGPS(String neolink){
        GPSM = appRepo.dameGPS(neolink);
        return GPSM;
    }
    public void savedate(String date){
        datechoosen.setValue(date);
    }
    public LiveData<String> retrivedate(){
        return datechoosen;
    }
}
