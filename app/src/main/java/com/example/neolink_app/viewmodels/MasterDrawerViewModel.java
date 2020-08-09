package com.example.neolink_app.viewmodels;

import android.annotation.SuppressLint;
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
import com.example.neolink_app.clases.LoginFirebase.UsuarioNeoL;
import com.example.neolink_app.clases.OLDneolinksboleto;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.clases.database_state.horasstate;
import com.example.neolink_app.clases.liveclases.livedaylydatapackage;
import com.example.neolink_app.clases.liveclases.livedaylydatapackagetwoday;

import java.security.acl.Owner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MasterDrawerViewModel extends AndroidViewModel {
    public LiveData<OWNERitems> Usuarioneolinks;
    public LiveData<OLDneolinksboleto> neonodos;
    public LiveData<Horas> datahoy;
    public LiveData<Horas> dataayer;
    public LiveData<horasstate> datastatehoy;
    public LiveData<horasstate> datastateayer;
    public livedaylydatapackage paquetesdedata = new livedaylydatapackage();
    public livedaylydatapackage paquetesdedataayer= new livedaylydatapackage();
    public livedaylydatapackagetwoday paquetesdedata2dias= new livedaylydatapackagetwoday();
    public LiveData<GPS> GPSM;
    public LiveData<Boolean> neolinkguardado;
    public MediatorLiveData datadiario = new MediatorLiveData<>();
    public boolean valua,valueb = false;
    public final MutableLiveData<String> datechoosen = new MutableLiveData<>();
    public final MutableLiveData<String> nuevoNeolink = new MutableLiveData<>();
    private UserInfoRepo appRepo;
    private String uid;
    private Calendar ahora = Calendar.getInstance();
    public MutableLiveData<ArrayList<Integer>> date = new MutableLiveData<>();
    public MutableLiveData<String> neolinkF = new MutableLiveData<>();
    public MutableLiveData<String> neolinkGPS = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Integer>> datebefore = new MutableLiveData<>();
    public ArrayList<OLDneolinksboleto> listacompleta = new ArrayList<>();

    public MasterDrawerViewModel(@NonNull Application application) {
        super(application);
        appRepo = new UserInfoRepo();
        updatetoday();
        setayer();
        //neolinkguardadopositivo();

    }
    public void poneruid(String uid){
        this.uid = uid;
    }
    public void getLiveNL(){
        Usuarioneolinks = appRepo.dameneolinks(uid);
    }
    /*
    public void getlistaNLNN(OWNERitems prelista){
        ArrayList<ArrayList<String>> listacompleta = new ArrayList<>();
        for(int i = 0;i<prelista.gettamanolista();i++){
          ArrayList<String> lista = new ArrayList<>();
          updateneolinkF(prelista.getitem(i));
          getLiveNN();
          lista.add(prelista.getitem(i));
          for(int j = 0;j<neonodos.getValue().dametamano();j++){
              lista.add(neonodos.getValue().damenodo(j));
          }
          listacompleta.add(lista);
        }
        this.listacompleta = listacompleta;
    }
     */
    public LiveData<OLDneolinksboleto> getneonodofromneolink(String neolink){
        return appRepo.damenodos2(uid,neolink);
    }
    public LiveData<OLDneolinksboleto> getLiveNN(){
        neonodos = Transformations.switchMap(neolinkF, new Function<String, LiveData<OLDneolinksboleto>>() {
            @Override
            public LiveData<OLDneolinksboleto> apply(String input) {
                return appRepo.damenodos(uid,input);
            }
        });
        return neonodos;
    }
    public void updateneolinkF(String neolink){
        neolinkF.setValue(neolink);
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
                return appRepo.damedatahoyK2(neo,input.get(0),input.get(1),input.get(2),"k");
            }
        });
        datastatehoy = Transformations.switchMap(date, new Function<ArrayList<Integer>, LiveData<horasstate>>() {
            @Override
            public LiveData<horasstate> apply(ArrayList<Integer> input) {
                return appRepo.damedatahoyState2(neo,input.get(0),input.get(1),input.get(2));
            }
        });
        //paquetesdedata = new livedaylydatapackage<Horas,horasstate>(datahoy,datastatehoy);

        dataayer = Transformations.switchMap(datebefore, new Function<ArrayList<Integer>, LiveData<Horas>>() {
            @Override
            public LiveData<Horas> apply(ArrayList<Integer> input) {

                return appRepo.damedatahoyK2(neo,input.get(0),input.get(1),input.get(2),"k");
            }
        });
        datastateayer = Transformations.switchMap(datebefore, new Function<ArrayList<Integer>, LiveData<horasstate>>() {
            @Override
            public LiveData<horasstate> apply(ArrayList<Integer> input) {
                return appRepo.damedatahoyState2(neo,input.get(0),input.get(1),input.get(2));
            }
        });

        paquetesdedata2dias = new livedaylydatapackagetwoday<Horas,horasstate,Horas,horasstate>(datahoy,datastatehoy,dataayer,datastateayer);
    }

    public LiveData<GPS> getGPS(String neolink){
        GPSM = appRepo.dameGPS(neolink);
        /*GPSM = Transformations.switchMap(neolinkGPS, new Function<String, LiveData<GPS>>() {
            @Override
            public LiveData<GPS> apply(String input) {
                return appRepo.dameGPS(input);
            }
        });*/
        return GPSM;
    }
    public LiveData<GPS> getGPS2(){
        GPSM = Transformations.switchMap(neolinkGPS, new Function<String, LiveData<GPS>>() {
            @Override
            public LiveData<GPS> apply(String input) {
                return appRepo.dameGPS(input);
            }
        });
        return GPSM;
    }
    public void updateGPS(String neolink){
        neolinkGPS.setValue(neolink);
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
    public void updatedatebefore(int ano, int mes, int dia){
        ArrayList<Integer> date = new ArrayList<>();
        date.add(ano);
        date.add(mes);
        date.add(dia);
        datebefore.setValue(date);
    }
    public void updatetoday(){
        int hoyaño = ahora.get(Calendar.YEAR)%100;
        int hoymes = ahora.get(Calendar.MONTH)+1;
        int hoydia = ahora.get(Calendar.DAY_OF_MONTH);
        updatedate(hoyaño,hoymes,hoydia);
    }
    private void setayer(){
        ArrayList<Integer> resultado = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String day = dateFormat.format(cal.getTime());
        String[] ayer = day.split("/");
        resultado.add(Integer.parseInt(ayer[0])%100);
        resultado.add(Integer.parseInt(ayer[1]));
        resultado.add(Integer.parseInt(ayer[2]));
        datebefore.setValue(resultado);
    }
    public Long getmillistoday(){
        return ahora.getTimeInMillis();
    }
    public void agregarneolink(String codigo){
        if(Usuarioneolinks!=null){
            neolinkguardado=appRepo.agregarneolink(codigo,Usuarioneolinks.getValue().damelista(),this.uid);
        }
    }
    public LiveData<Boolean> segraboelneolink(){
        return neolinkguardado;
    }

    public void neolinkguardadopositivo(){
        MediatorLiveData<Boolean> transaccion = new MediatorLiveData<>();
        transaccion.setValue(null);
        neolinkguardado = transaccion;
    }
    public void guardarneolinkdeldialogo(String neolink){
        nuevoNeolink.setValue(neolink);
    }
    public void vacialneolinkdeldialogo(){
        nuevoNeolink.setValue(null);
    }
    public LiveData<String> neolinkdeldialogo(){
        return nuevoNeolink;
    }
}
