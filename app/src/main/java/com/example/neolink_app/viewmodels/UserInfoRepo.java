package com.example.neolink_app.viewmodels;

import android.provider.ContactsContract;
import android.view.ViewDebug;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.neolink_app.adaptadores.FirebaseQueryLiveData;
import com.example.neolink_app.clases.Dias;
import com.example.neolink_app.clases.GPS;
import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.clases.Minutos;
import com.example.neolink_app.clases.OLDneolinksboleto;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.clases.Puerto;
import com.example.neolink_app.clases.dataPuerto;
import com.example.neolink_app.clases.database_state.horasstate;
import com.example.neolink_app.clases.database_state.minutosstate;
import com.example.neolink_app.clases.database_state.statePK;
import com.google.firebase.FirebaseExceptionMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserInfoRepo {

    //private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(HOT_STOCK_REF);
    private final MediatorLiveData ownerdata = new MediatorLiveData();
    private final MediatorLiveData oldneolinks = new MediatorLiveData();
    private final MediatorLiveData datahoy = new MediatorLiveData();
    private final MediatorLiveData datahoyState = new MediatorLiveData();
    private final MediatorLiveData GPSM = new MediatorLiveData();


    private OWNERitems userneolinks = new OWNERitems();
    private OLDneolinksboleto neolinks = new OLDneolinksboleto();
    private GPS gps = new GPS();
    private DatabaseReference BaseDatos = FirebaseDatabase.getInstance().getReference();

    public UserInfoRepo() {
    }

    public LiveData<OWNERitems> dameneolinks(String uid) {
        String patio = "/OWNERitems/" + uid;

        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        ownerdata.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable final DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    userneolinks = dataSnapshot.getValue(OWNERitems.class);
                    ownerdata.setValue(userneolinks);
                } else {
                    ownerdata.setValue(null);
                }
            }
        });
        return ownerdata;
    }

    public LiveData<OLDneolinksboleto> damenodos(String uid, String neolink){
        String patio = "/OLDneolinks/" + neolink;
        final String uid2 = uid;
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        oldneolinks.addSource(liveDataNL, new Observer<DataSnapshot>(){

            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null) {
                    neolinks = dataSnapshot.getValue(OLDneolinksboleto.class);
                    if(uid2.equals(neolinks.neolinksuid())){
                        oldneolinks.setValue(neolinks);
                    }
                } else {
                    oldneolinks.setValue(null);
                }
            }
        });
        return oldneolinks;
    }

    public LiveData<Horas> damedatahoyK(String neolink, int año, int mes, int dia, String sensor){
        String esp = "/";
        String patio = "/NeoLink/"+neolink+"/DataSet/"+sensor+esp+año+esp+operacionfecha(mes)+esp+operacionfecha(dia);
        //String patio = "/NeoLink/"+neolink+"/DataSet/k/20/07/01/"; // esto es solo para debuggear un dia especifico por ahora
        Horas data = new Horas();
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        datahoy.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    Horas horas = new Horas();
                    for(DataSnapshot childSnap:dataSnapshot.getChildren()){
                        String horichi = childSnap.getKey(); //hora
                        Minutos minutos = new Minutos();
                        for(DataSnapshot chilchilSnap:childSnap.getChildren()){
                            String min = chilchilSnap.getKey();//minutos
                            Puerto puerto = new Puerto();
                            for(DataSnapshot chilchilchilSnap:chilchilSnap.getChildren()){
                                String a = chilchilchilSnap.getKey();//puerto
                                dataPuerto b = chilchilchilSnap.getValue(dataPuerto.class);//data
                                puerto.tomaPuerto(a,b);
                            }
                            minutos.tomaMinutos(min,puerto);
                        }
                        horas.tomaHoras(horichi,minutos);
                    }
                    datahoy.setValue(horas);
                } else {
                    datahoy.setValue(null);
                }
            }
        });
        //String neolink, int año, int mes, int dia, String sensor
        return datahoy;
    }
    public LiveData<horasstate> damedatahoyState(String neolink, int año, int mes, int dia){
        String esp = "/";
        String patio = "/NeoLink/"+neolink+"/DataSet/State/"+año+esp+operacionfecha(mes)+esp+operacionfecha(dia);
        //String patio = "/NeoLink/"+neolink+"/DataSet/State/20/07/02/";
        horasstate data = new horasstate();
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        datahoyState.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    horasstate horas = new horasstate();
                    for(DataSnapshot childSnap:dataSnapshot.getChildren()){
                        String horichi = childSnap.getKey(); //hora
                        minutosstate minutos = new minutosstate();
                        for(DataSnapshot chilchilSnap:childSnap.getChildren()){
                            String min = chilchilSnap.getKey();//minutos
                            statePK puerto = new statePK();
                            puerto = chilchilSnap.getValue(statePK.class);
                            minutos.tomaminutos(min,puerto);
                        }
                        horas.tomahoras(horichi,minutos);
                    }
                    datahoyState.setValue(horas);
                } else {
                    datahoyState.setValue(null);
                }
            }
        });
        return datahoyState;
    }

    private String operacionfecha(int date){
        String resultado;
        if(date<10){
            resultado = "0"+date;
        } else resultado = Integer.toString(date);
        return resultado;
    }

    public LiveData<GPS> dameGPS(String neolink){
        String esp = "/";
        String patio = "/NeoLink/"+neolink+"/DataSet/State/GPS";
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        GPSM.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    gps = dataSnapshot.getValue(GPS.class);
                    GPSM.setValue(gps);
                } else {
                    GPSM.setValue(null);
                }
            }
        });
        return GPSM;
    }

}
