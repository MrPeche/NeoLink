package com.example.neolink_app.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.example.neolink_app.clases.Dias;
import com.example.neolink_app.clases.GPS;
import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.clases.OLDneolinksboleto;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.clases.database_state.horasstate;
import com.example.neolink_app.clases.liveclases.livedaylydatapackage;

import java.util.ArrayList;
import java.util.List;

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
    public MutableLiveData<ArrayList<Integer>> date = new MutableLiveData<>();

    public MasterDrawerViewModel(@NonNull Application application) {
        super(application);
        appRepo = new UserInfoRepo();
        updatedate(20,7,25);

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
    public void livelydatafull(String neolink, int año, int mes, int dia, String sensor){
        datahoy = appRepo.damedatahoyK(neolink,año,mes,dia,sensor); //TENGO QUE ARREGLAR ESTO PARA MAS EQUIPOS!!!!
        datastatehoy = appRepo.damedatahoyState(neolink,año,mes,dia);
        paquetesdedata = new livedaylydatapackage<Horas,horasstate>(datahoy,datastatehoy);
        //paquetesdedata = new livedaylydatapackage<Horas,horasstate>(datahoy,datastatehoy);
        //datadiario.addSource();
        //datadiario.addSource();
    }
    public void retrivepaqueteDatos(String neolink){
        final String neo = neolink;
        datahoy = Transformations.switchMap(date, new Function<ArrayList<Integer>, LiveData<Horas>>() {
            @Override
            public LiveData<Horas> apply(ArrayList<Integer> input) {
                return appRepo.damedatahoyK(neo,input.get(0),input.get(1),input.get(2),"k");
            }
        });
        datastatehoy = Transformations.switchMap(date, new Function<ArrayList<Integer>, LiveData<horasstate>>() {
            @Override
            public LiveData<horasstate> apply(ArrayList<Integer> input) {
                return appRepo.damedatahoyState(neo,input.get(0),input.get(1),input.get(2));
            }
        });
        paquetesdedata = new livedaylydatapackage<Horas,horasstate>(datahoy,datastatehoy);
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

    public void updatedate(int ano, int mes, int dia){
        ArrayList<Integer> lista = new ArrayList<>();
        lista.add(ano);
        lista.add(mes);
        lista.add(dia);
        date.setValue(lista);
    }
}
