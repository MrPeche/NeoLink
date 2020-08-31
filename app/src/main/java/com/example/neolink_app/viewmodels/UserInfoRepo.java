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
import com.example.neolink_app.clases.Meses;
import com.example.neolink_app.clases.Minutos;
import com.example.neolink_app.clases.OLDneolinksboleto;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.clases.Puerto;
import com.example.neolink_app.clases.SensorG.HorasG;
import com.example.neolink_app.clases.SensorG.MinutosG;
import com.example.neolink_app.clases.SensorG.PuertoG;
import com.example.neolink_app.clases.SensorG.dataPuertoG;
import com.example.neolink_app.clases.configuracion.Confvalues;
import com.example.neolink_app.clases.configuracion.state;
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
    private final MediatorLiveData datahoyG = new MediatorLiveData();
    private final MediatorLiveData GPSM = new MediatorLiveData();
    private final MediatorLiveData<Meses> monthdata = new MediatorLiveData<>();
    private final MediatorLiveData<Boolean> transferencia = new MediatorLiveData<>();


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
    // ESTE ES GENERICO
    public LiveData<OLDneolinksboleto> damenodos2(String uid, String neolink){
        final MediatorLiveData<OLDneolinksboleto> neonodo = new MediatorLiveData<>();
        String patio = "/OLDneolinks/" + neolink;
        final String uid2 = uid;
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        neonodo.addSource(liveDataNL, new Observer<DataSnapshot>(){

            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null) {
                    neolinks = dataSnapshot.getValue(OLDneolinksboleto.class);
                    if(uid2.equals(neolinks.neolinksuid())){
                        neonodo.setValue(neolinks);
                    }
                } else {
                    neonodo.setValue(null);
                }
            }
        });
        return neonodo;
    }

    public LiveData<Horas> damedatahoyK(String neolink, int ano, int mes, int dia, String sensor){
        String esp = "/";
        String patio = "/NeoLink/"+neolink+"/DataSet/"+sensor+esp+ano+esp+operacionfecha(mes)+esp+operacionfecha(dia);
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
    //TENGO QUE BORRAR ESTO LUEGO PRUEBA 1
    public LiveData<Horas> damedatahoyK2(String neolink, int ano, int mes, int dia, String sensor){
        final MediatorLiveData datahoy2 = new MediatorLiveData();
        String esp = "/";
        String patio = "/NeoLink/"+neolink+"/DataSet/"+sensor+esp+ano+esp+operacionfecha(mes)+esp+operacionfecha(dia);
        //String patio = "/NeoLink/"+neolink+"/DataSet/k/20/07/01/"; // esto es solo para debuggear un dia especifico por ahora
        Horas data = new Horas();
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        datahoy2.addSource(liveDataNL, new Observer<DataSnapshot>() {
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
                    datahoy2.setValue(horas);
                } else {
                    datahoy2.setValue(null);
                }
            }
        });
        //String neolink, int año, int mes, int dia, String sensor
        return datahoy2;
    }
    public LiveData<horasstate> damedatahoyState2(String neolink, int ano, int mes, int dia){
        final MediatorLiveData datahoyState2 = new MediatorLiveData();
        String esp = "/";
        String patio = "/NeoLink/"+neolink+"/DataSet/State/"+ano+esp+operacionfecha(mes)+esp+operacionfecha(dia);
        //String patio = "/NeoLink/"+neolink+"/DataSet/State/20/07/02/";
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        datahoyState2.addSource(liveDataNL, new Observer<DataSnapshot>() {
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
                    datahoyState2.setValue(horas);
                } else {
                    datahoyState2.setValue(null);
                }
            }
        });
        return datahoyState2;
    }
    public LiveData<horasstate> damedatahoyState(String neolink, int ano, int mes, int dia){
        String esp = "/";
        String patio = "/NeoLink/"+neolink+"/DataSet/State/"+ano+esp+operacionfecha(mes)+esp+operacionfecha(dia);
        //String patio = "/NeoLink/"+neolink+"/DataSet/State/20/07/02/";
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
    public LiveData<HorasG> damedatahoyG(String neolink, int ano, int mes, int dia){
        String esp = "/";
        String sensor = "g";
        String patio = "/NeoLink/"+neolink+"/DataSet/"+sensor+esp+ano+esp+operacionfecha(mes)+esp+operacionfecha(dia);
        //String patio = "/NeoLink/"+neolink+"/DataSet/k/20/07/01/"; // esto es solo para debuggear un dia especifico por ahora
        HorasG data = new HorasG();
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        datahoy.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    HorasG horas = new HorasG();
                    for(DataSnapshot childSnap:dataSnapshot.getChildren()){
                        String horichi = childSnap.getKey(); //hora
                        MinutosG minutos = new MinutosG();
                        for(DataSnapshot chilchilSnap:childSnap.getChildren()){
                            String min = chilchilSnap.getKey();//minutos
                            PuertoG puerto = new PuertoG();
                            for(DataSnapshot chilchilchilSnap:chilchilSnap.getChildren()){
                                String a = chilchilchilSnap.getKey();//puerto
                                dataPuertoG b = chilchilchilSnap.getValue(dataPuertoG.class);//data
                                puerto.tomaPuerto(a,b);
                            }
                            minutos.tomaMinutos(min,puerto);
                        }
                        horas.tomaHoras(horichi,minutos);
                    }
                    datahoyG.setValue(horas);
                } else {
                    datahoyG.setValue(null);
                }
            }
        });
        //String neolink, int año, int mes, int dia, String sensor
        return datahoyG;
    }
    public LiveData<Horas> damedatahoyG2(String neolink, int ano, int mes, int dia){
        final MediatorLiveData datahoyG2 = new MediatorLiveData();
        String sensor = "g";
        String esp = "/";
        String patio = "/NeoLink/"+neolink+"/DataSet/"+sensor+esp+ano+esp+operacionfecha(mes)+esp+operacionfecha(dia);
        //String patio = "/NeoLink/"+neolink+"/DataSet/k/20/07/01/"; // esto es solo para debuggear un dia especifico por ahora
        Horas data = new Horas();
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        datahoyG2.addSource(liveDataNL, new Observer<DataSnapshot>() {
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
                    datahoyG2.setValue(horas);
                } else {
                    datahoyG2.setValue(null);
                }
            }
        });
        //String neolink, int año, int mes, int dia, String sensor
        return datahoyG2;
    }
    public LiveData<Meses> givedatafromMonth(String neolink, int ano, int mes, int dia){
        String esp = "/";
        String patio = "/NeoLink/"+neolink+"/DataSet/State/"+ano+esp+operacionfecha(mes);
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        monthdata.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    Meses monthlyD = new Meses();
                    for(DataSnapshot childsnap:dataSnapshot.getChildren()){
                        String mes = childsnap.getKey();
                        Dias dias = new Dias();
                        for(DataSnapshot diasF:childsnap.getChildren()) {
                            String dia = diasF.getKey();
                            Horas horichis = new Horas();
                            for (DataSnapshot childchildsnap : diasF.getChildren()) {
                                String nombrehoras = childchildsnap.getKey();
                                Minutos minutichis = new Minutos();
                                for (DataSnapshot childchildchilsnap : childchildsnap.getChildren()) {
                                    String minutos = childchildchilsnap.getKey();
                                    Puerto puerto = new Puerto();
                                    for (DataSnapshot childchildchildchildsnap : childchildchilsnap.getChildren()) {
                                        String a = childchildchildchildsnap.getKey();
                                        dataPuerto b = childchildchildchildsnap.getValue(dataPuerto.class);
                                        puerto.tomaPuerto(a, b);
                                    }
                                    minutichis.tomaMinutos(minutos, puerto);
                                }
                                horichis.tomaHoras(nombrehoras,minutichis);
                            }
                            dias.tomadias(dia,horichis);
                        }
                        monthlyD.tomameses(mes,dias);
                    }
                    monthdata.setValue(monthlyD);
                } else monthdata.setValue(null);
            }
        });
        return monthdata;
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
    public LiveData<Boolean> agregarneolink(final String codigo, final ArrayList<String> actual, final String uid){
        String patio = "/NEWneolinks";
        final DatabaseReference basecompleta = FirebaseDatabase.getInstance().getReference();
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);

        transferencia.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    ArrayList<String> lista = actual;
                    if(dataSnapshot.child(codigo).exists()){
                        lista.add(codigo);
                        transferencia.setValue(true);
                        basecompleta.child("NEWneolinks").child(codigo).removeValue();
                        basecompleta.child("OWNERitems").child(uid).child("neolinks").setValue(lista);
                        OLDneolinksboleto paqueton = new OLDneolinksboleto(uid);
                        basecompleta.child("OLDneolinks").child(codigo).setValue(paqueton);
                    }
                } else transferencia.setValue(false);
            }
        });
        return transferencia;
    }

    public LiveData<Dias> fecthdatapordiaG(String neolink, int ano, int mes, int dia){
        final MediatorLiveData<Dias> datahoyG2 = new MediatorLiveData<>();
        String sensor = "g";
        String esp = "/";
        String patio = "/NeoLink/"+neolink+"/DataSet/"+sensor+esp+ano+esp+operacionfecha(mes)+esp+operacionfecha(dia);
        //String patio = "/NeoLink/"+neolink+"/DataSet/k/20/07/01/"; // esto es solo para debuggear un dia especifico por ahora
        Horas data = new Horas();
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        datahoyG2.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    Dias dias = new Dias();
                    for(DataSnapshot diasF:dataSnapshot.getChildren()) {
                        String dia = diasF.getKey();
                        Horas horichis = new Horas();
                        for (DataSnapshot childchildsnap : diasF.getChildren()) {
                            String nombrehoras = childchildsnap.getKey();
                            Minutos minutichis = new Minutos();
                            for (DataSnapshot childchildchilsnap : childchildsnap.getChildren()) {
                                String minutos = childchildchilsnap.getKey();
                                Puerto puerto = new Puerto();
                                for (DataSnapshot childchildchildchildsnap : childchildchilsnap.getChildren()) {
                                    String a = childchildchildchildsnap.getKey();
                                    dataPuerto b = childchildchildchildsnap.getValue(dataPuerto.class);
                                    puerto.tomaPuerto(a, b);
                                }
                                minutichis.tomaMinutos(minutos, puerto);
                            }
                            horichis.tomaHoras(nombrehoras,minutichis);
                        }
                        dias.tomadias(dia,horichis);
                    }
                    datahoyG2.setValue(dias);
                } else datahoyG2.setValue(null);
            }
        });
        //String neolink, int año, int mes, int dia, String sensor
        return datahoyG2;
    }
    public LiveData<state> fetchdataconfigracionstate(String neolink){
        final MediatorLiveData<state> configuracionstate = new MediatorLiveData<>();
        String patio = "/NeoLink/"+neolink+"/State/";
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        configuracionstate.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    configuracionstate.setValue(dataSnapshot.getValue(state.class));
                } else configuracionstate.setValue(null);
            }
        });
        return configuracionstate;
    }
    public LiveData<Confvalues> fetchdataconfigracionconfvalue(String neolink){
        final MediatorLiveData<Confvalues> configuracionconfvalue = new MediatorLiveData<>();
        String patio = "/NeoLink/"+neolink+"/Conf_values/";
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        configuracionconfvalue.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    configuracionconfvalue.setValue(dataSnapshot.getValue(Confvalues.class));
                } else configuracionconfvalue.setValue(null);
            }
        });
        return configuracionconfvalue;
    }
}
