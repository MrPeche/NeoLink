package com.example.neolink_app.viewmodels;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
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
import com.example.neolink_app.clases.SensorG.DiasG;
import com.example.neolink_app.clases.SensorG.HorasG;
import com.example.neolink_app.clases.SensorG.MinutosG;
import com.example.neolink_app.clases.SensorG.PuertoG;
import com.example.neolink_app.clases.SensorG.dataPuertoG;
import com.example.neolink_app.clases.clasesdelregistro.notihist;
import com.example.neolink_app.clases.clasesdelregistro.registrocontenido;
import com.example.neolink_app.clases.clasesdelregistro.registrodia;
import com.example.neolink_app.clases.clasesdelregistro.registromes;
import com.example.neolink_app.clases.clasesdelregistro.registroyear;
import com.example.neolink_app.clases.clasesparaformargraficos.InfoParaGraficos;
import com.example.neolink_app.clases.configuracion.Confvalues;
import com.example.neolink_app.clases.configuracion.state;
import com.example.neolink_app.clases.configuracion.statelimitsport;
import com.example.neolink_app.clases.configuracion.statesinglelimitvalues;
import com.example.neolink_app.clases.dataPuerto;
import com.example.neolink_app.clases.database_state.diasstate;
import com.example.neolink_app.clases.database_state.horasstate;
import com.example.neolink_app.clases.database_state.minutosstate;
import com.example.neolink_app.clases.database_state.statePK;
import com.example.neolink_app.clases.liveclases.paquetededatacompleto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        final MediatorLiveData<Horas> datahoy2 = new MediatorLiveData();
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
    public LiveData<HorasG> damedatahoyG2(String neolink, int ano, int mes, int dia){
        final MediatorLiveData<HorasG> datahoyG2 = new MediatorLiveData();
        String esp = "/";
        String sensor = "g";
        String patio = "/NeoLink/"+neolink+"/DataSet/"+sensor+esp+ano+esp+operacionfecha(mes)+esp+operacionfecha(dia);
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        datahoyG2.addSource(liveDataNL, new Observer<DataSnapshot>() {
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
        String patio = "/NeoLink/"+neolink+"/State/GPS";
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

    public void saveconfiguration(String neolink,boolean beep,int beepv,boolean port, int portv, boolean gps, int gpsv, boolean tiempoentreplazos, int tiempoentreplazosv,boolean[] switchs,boolean[] switchsactuales,boolean[] superior,boolean[] inferior,String[] superiorl,String[] inferiorl,double[] valorsuperior,double[] valorinferior){
        String patio = "/NeoLink/"+neolink;
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        Map<String, Object> childUpdates  = new HashMap<>();
        if(beep) childUpdates.put("/Conf_values/BEEP_EN/",beepv);
        if(port) childUpdates.put("/Conf_values/PORT_RQ/",portv);
        if(gps) childUpdates.put("/Conf_values/GPS_RQ/",gpsv);
        if(tiempoentreplazos) childUpdates.put("/Conf_values/SLEEP_TIME/",tiempoentreplazosv);
        for(int i =0;i<switchs.length;i++){
            if(!(switchs[i]&&superior[i]&&inferior[i])){
                int a;
                if(switchsactuales[i]){
                    a = 1;
                } else a = 0;
                double b = generarlimite(superior[i],superiorl[i],valorsuperior[i]);
                double c = generarlimite(inferior[i],inferiorl[i],valorinferior[i]);
                statesinglelimitvalues paquetedesubida = new statesinglelimitvalues(b,c,a);
                if(i<3){
                    childUpdates.put("/State/Limits/Port1/g/V"+(i+1)+"/",paquetedesubida);
                } else childUpdates.put("/State/Limits/Port1/k/V"+(i-2)+"/",paquetedesubida);
            }
        }
        BaseDatosNL.updateChildren(childUpdates);
    }
    private double generarlimite(boolean limiteb,String valor,double valororiginal){
        if(limiteb){
            if(valor.equals("")){
                return 0;
            } else return valororiginal;
        } else return Double.parseDouble(valor);
    }
    public LiveData<statelimitsport> fetchlimites(String neolink){
        final MediatorLiveData<statelimitsport> limites = new MediatorLiveData<>();
        String patio = "/NeoLink/"+neolink+"/State/Limits/";
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        limites.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    limites.setValue(dataSnapshot.getValue(statelimitsport.class));
                } else limites.setValue(null);
            }
        });
        return limites;
    }
    public LiveData<InfoParaGraficos> fetchhoyayer(String neolink,ArrayList<Integer> fechahoy,ArrayList<Integer> fechaayer){
        final MediatorLiveData<InfoParaGraficos> hoyayer = new MediatorLiveData<>();
        paquetededatacompleto<Dias,diasstate,DiasG> hoy = new paquetededatacompleto<>(fetchdataperdaywithsensork(neolink, fechahoy.get(0), fechahoy.get(1), fechahoy.get(2)), fetchdataperdaywithsensorstate(neolink, fechahoy.get(0), fechahoy.get(1), fechahoy.get(2)), fetchdataperdaywithsensorg(neolink, fechahoy.get(0), fechahoy.get(1), fechahoy.get(2)));
        paquetededatacompleto<Dias,diasstate,DiasG> ayer = new paquetededatacompleto<>(fetchdataperdaywithsensork(neolink, fechaayer.get(0), fechaayer.get(1), fechaayer.get(2)), fetchdataperdaywithsensorstate(neolink, fechaayer.get(0), fechaayer.get(1), fechaayer.get(2)), fetchdataperdaywithsensorg(neolink, fechaayer.get(0), fechaayer.get(1), fechaayer.get(2)));
        InfoParaGraficos data = new InfoParaGraficos();
        //data.actualizarcantidad(2);
        hoyayer.setValue(null);
        hoyayer.addSource(ayer, new Observer<Pair<Pair<Dias, diasstate>, DiasG>>() {
            @Override
            public void onChanged(Pair<Pair<Dias, diasstate>, DiasG> pairDiasGPair) {
                if(pairDiasGPair!=null){
                    if(ayer.isitready()){
                        android.util.Pair<Integer,Boolean> pregunta = data.buscarpordiadentro(ayer.damevalorB().damedia(0));
                        if(pregunta.second){
                            data.actualizardatoespecifico(ayer,pregunta.first);
                        } else
                            data.agregarinfodias(ayer);
                        hoyayer.setValue(data);
                    }
                } else
                    hoyayer.setValue(data);
            }
        });
        hoyayer.addSource(hoy, new Observer<Pair<Pair<Dias, diasstate>, DiasG>>() {
            @Override
            public void onChanged(Pair<Pair<Dias, diasstate>, DiasG> pairDiasGPair) {
                if(pairDiasGPair!=null){
                    if(hoy.isitready()){
                        android.util.Pair<Integer,Boolean> pregunta = data.buscarpordiadentro(hoy.damevalorB().damedia(0));
                        if(pregunta.second){
                            data.actualizardatoespecifico(hoy,pregunta.first);
                        } else
                            data.agregarinfodias(hoy);
                        hoyayer.setValue(data);
                    }
                } else
                    hoyayer.setValue(data);
            }
        });
        return hoyayer;
    }
    public LiveData<Dias> fetchdataperdaywithsensork(String neolink, int ano, int mes, int dia){
        final MediatorLiveData<Dias> datasensork = new MediatorLiveData<>();
        String esp ="/";
        String sensor ="k";
        String patio ="/NeoLink/"+neolink+"/DataSet/"+sensor+esp+ano+esp+operacionfecha(mes)+esp+operacionfecha(dia);
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        datasensork.addSource(liveDataNL, dataSnapshot -> {
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
                Dias diadata = new Dias();
                diadata.tomadias(ano+esp+operacionfecha(mes)+esp+operacionfecha(dia),horas);
                datasensork.setValue(diadata);
            } else {
                datasensork.setValue(null);
            }
        });
        return datasensork;
    }
    public LiveData<DiasG> fetchdataperdaywithsensorg(String neolink, int ano, int mes, int dia){
        final MediatorLiveData<DiasG> datasensorg = new MediatorLiveData<>();
        String esp = "/";
        String sensor = "g";
        String patio = "/NeoLink/"+neolink+"/DataSet/"+sensor+esp+ano+esp+operacionfecha(mes)+esp+operacionfecha(dia);
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        datasensorg.addSource(liveDataNL, new Observer<DataSnapshot>() {
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
                    DiasG diadata = new DiasG();
                    diadata.tomadias(ano+esp+operacionfecha(mes)+esp+operacionfecha(dia),horas);
                    datasensorg.setValue(diadata);
                } else {
                    datasensorg.setValue(null);
                }
            }
        });
        return datasensorg;
    }
    public LiveData<diasstate> fetchdataperdaywithsensorstate(String neolink, int ano, int mes, int dia){
        final MediatorLiveData<diasstate> datasensorstate = new MediatorLiveData<>();
        String esp = "/";
        String patio = "/NeoLink/"+neolink+"/DataSet/State/"+ano+esp+operacionfecha(mes)+esp+operacionfecha(dia);
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        datasensorstate.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    horasstate horas = new horasstate();
                    for(DataSnapshot childSnap:dataSnapshot.getChildren()){
                        String horichi = childSnap.getKey(); //hora
                        minutosstate minutos = new minutosstate();
                        for(DataSnapshot chilchilSnap:childSnap.getChildren()){
                            String min = chilchilSnap.getKey();//minutos
                            statePK puerto = chilchilSnap.getValue(statePK.class);
                            minutos.tomaminutos(min,puerto);
                        }
                        horas.tomahoras(horichi,minutos);
                    }
                    diasstate diasdata = new diasstate();
                    diasdata.tomadata(ano+esp+operacionfecha(mes)+esp+operacionfecha(dia),horas);
                    datasensorstate.setValue(diasdata);
                } else {
                    datasensorstate.setValue(null);
                }
            }
        });
        return datasensorstate;
    }
    public LiveData<InfoParaGraficos> fetchestasemana(String neolink, ArrayList<ArrayList<Integer>> semana){
        final MediatorLiveData<InfoParaGraficos> estasemana = new MediatorLiveData<>();
        InfoParaGraficos obj = new InfoParaGraficos();
        estasemana.setValue(null);
        for(ArrayList<Integer> dia:semana){
            paquetededatacompleto<Dias,diasstate,DiasG> diarandom = new paquetededatacompleto<Dias,diasstate,DiasG>(fetchdataperdaywithsensork(neolink,dia.get(0),dia.get(1),dia.get(2)),
                    fetchdataperdaywithsensorstate(neolink,dia.get(0),dia.get(1),dia.get(2)),
                    fetchdataperdaywithsensorg(neolink,dia.get(0),dia.get(1),dia.get(2)));

            estasemana.addSource(diarandom, new Observer<Pair<Pair<Dias, diasstate>, DiasG>>() {
                @Override
                public void onChanged(Pair<Pair<Dias, diasstate>, DiasG> pairDiasGPair) {
                    if(pairDiasGPair!=null){
                        if(diarandom.isitready()){
                            android.util.Pair<Integer,Boolean> pregunta = obj.buscarpordiadentro(diarandom.damevalorB().damedia(0));
                            if(pregunta.second){
                                obj.actualizardatoespecifico(diarandom,pregunta.first);
                            } else
                                obj.agregarinfodias(diarandom);
                            estasemana.setValue(obj);
                        }
                    } else
                        estasemana.setValue(obj);
                }
            });
        }
        return estasemana;
    }
    /*
    public LiveData<Meses> fetchdatakpormonth(String neolink, int ano, int mes){
        final MediatorLiveData<Meses> datasensork = new MediatorLiveData<>();
        String esp ="/";
        String sensor ="k";
        String patio ="/NeoLink/"+neolink+"/DataSet/"+sensor+esp+ano+esp+operacionfecha(mes);
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        datasensork.addSource(liveDataNL, dataSnapshot -> {
            if(dataSnapshot!=null){
                Dias dias = new Dias();
                for(DataSnapshot diasFB:dataSnapshot.getChildren()){
                    String horichi = diasFB.getKey(); //dias
                    Horas horas = new Horas();
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
                Dias diadata = new Dias();
                diadata.tomadias(ano+esp+operacionfecha(mes)+esp+operacionfecha(dia),horas);
                datasensork.setValue(diadata);
            } else {
                datasensork.setValue(null);
            }
        });
        return datasensork;
    }

     */

    public LiveData<InfoParaGraficos> fetchestemes(){
        final MediatorLiveData<InfoParaGraficos> estemes = new MediatorLiveData<>();

        return estemes;
    }
    public LiveData<InfoParaGraficos> fetchesteano(){
        final MediatorLiveData<InfoParaGraficos> esteano = new MediatorLiveData<>();
        return esteano;
    }
    public LiveData<InfoParaGraficos> fetchdiasrandom(){
        final MediatorLiveData<InfoParaGraficos> diasrandom = new MediatorLiveData<>();
        return diasrandom;
    }
    public LiveData<notihist> fetchregistro(String neolink,int ano, int mes){
        final MediatorLiveData<notihist> registrodata = new MediatorLiveData<>();
        String path = "/NeoLink/"+neolink+"/DataSet/NotiHist/"+ano+"/"+operacionfecha(mes);
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(path);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        registrodata.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    registromes regmes = new registromes();
                    for(DataSnapshot dia:dataSnapshot.getChildren()){
                        String dianame = dia.getKey();
                        registrodia regdia = new registrodia();
                        for(DataSnapshot minutos:dia.getChildren()){
                            String min = minutos.getKey();
                            String contenido = minutos.getValue(String.class);
                            registrocontenido content = new registrocontenido(min,contenido);
                            regdia.agregarcontenido(content);
                        }
                        regmes.agregarboth(dianame,regdia);
                    }
                    registroyear regyear = new registroyear();
                    regyear.agregarlosdos(operacionfecha(mes),regmes);
                    notihist reg = new notihist();
                    reg.agregarlosdos(Integer.toString(ano),regyear);
                    registrodata.setValue(reg);
                } else {
                    registrodata.setValue(null);
                }
            }
        });
        return registrodata;
    }
}
