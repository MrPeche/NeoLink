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
import androidx.lifecycle.Transformations;

import com.example.neolink_app.clases.GPS;
import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.clases.OLDneolinksboleto;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.clases.SensorG.HorasG;
import com.example.neolink_app.clases.clasesdelregistro.notihist;
import com.example.neolink_app.clases.clasesparaformargraficos.InfoParaGraficos;
import com.example.neolink_app.clases.configuracion.Confvalues;
import com.example.neolink_app.clases.configuracion.state;
import com.example.neolink_app.clases.configuracion.statelimitsport;
import com.example.neolink_app.clases.database_state.horasstate;
import com.example.neolink_app.clases.liveclases.datadeconfiguracion;
import com.example.neolink_app.clases.liveclases.livedaylydatapackage;
import com.example.neolink_app.clases.liveclases.livedaylydatapackagetwoday;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MasterDrawerViewModel extends AndroidViewModel {
    public LiveData<OWNERitems> Usuarioneolinks;
    public LiveData<OLDneolinksboleto> neonodos;
    public LiveData<Horas> datahoy;
    public LiveData<Horas> dataayer;
    public LiveData<horasstate> datastatehoy;
    public LiveData<horasstate> datastateayer;
    public LiveData<HorasG> datahoyG;
    public LiveData<HorasG> dataayerG;
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
    private manejadordedispositivo FCM;
    private String uid;
    private Calendar ahora = Calendar.getInstance();
    public MutableLiveData<ArrayList<Integer>> date = new MutableLiveData<>();
    public MutableLiveData<Integer> datemode = new MutableLiveData<>();
    public MutableLiveData<String> neolinkF = new MutableLiveData<>();
    public MutableLiveData<String> neolinkGPS = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Integer>> datetoday = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Integer>> datebefore = new MutableLiveData<>();
    public ArrayList<OLDneolinksboleto> listacompleta = new ArrayList<>();

    public MasterDrawerViewModel(@NonNull Application application) {
        super(application);
        appRepo = new UserInfoRepo();
        FCM = new manejadordedispositivo();
        updatetoday();
        setayer();
        settoday();
        setdatemode();
        //neolinkguardadopositivo();

    }
    public void poneruid(String uid){
        this.uid = uid;
        FCM.guardardispositivo(uid);
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
    public void retrivedatag(String neolink){
        final String neo = neolink;
        datahoyG = Transformations.switchMap(date, new Function<ArrayList<Integer>, LiveData<HorasG>>() {
            @Override
            public LiveData<HorasG> apply(ArrayList<Integer> input) {
                return appRepo.damedatahoyG(neo,input.get(0),input.get(1),input.get(2));
            }
        });
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
    public void settoday(){
        ArrayList<Integer> today = new ArrayList<>();
        today.add(ahora.get(Calendar.YEAR)%100);
        today.add(ahora.get(Calendar.MONTH)+1);
        today.add(ahora.get(Calendar.DAY_OF_MONTH));
        datetoday.setValue(today);
    }
    public void setdatemode(){
        datemode.setValue(0);
    }
    public void changemode(int modo){
        datemode.setValue(modo);
    }
    public LiveData<InfoParaGraficos> masterdatedatapackage(String neolink){
        return Transformations.switchMap(datemode, new Function<Integer, LiveData<InfoParaGraficos>>() {
            @Override
            public LiveData<InfoParaGraficos> apply(Integer input) {
                if(input==0){
                    return appRepo.fetchhoyayer(neolink, Objects.requireNonNull(datetoday.getValue()), Objects.requireNonNull(datebefore.getValue()));
                } else if(input==1){
                    return appRepo.fetchestasemana(neolink, diasdelasemana());
                } else if(input==2){
                    return appRepo.fetchestemes();
                } else if(input==3){
                    return appRepo.fetchesteano();
                } else {
                    return appRepo.fetchdiasrandom();
                }
            }
        });
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
    private ArrayList<ArrayList<Integer>> diasdelasemana(){
        ArrayList<Calendar> semana = figurarlasemana();
        ArrayList<ArrayList<Integer>> resultado = new ArrayList<>();
        for(Calendar dias: semana){
            resultado.add(translatesemana(dias));
        }
        return resultado;
    }
    private ArrayList<Calendar> figurarlasemana(){
        ArrayList<Calendar> dias = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        for(int i = 0;i<day;i++){
            calendar.add(Calendar.DATE,-((day-1)-i));
            dias.add(calendar);
        }
        return dias;
    }
    private ArrayList<Integer> translatesemana(Calendar cal){
        ArrayList<Integer> resultado= new ArrayList<>();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
        String day = dateFormat.format(cal.getTime());
        String[] ayer = day.split("/");
        resultado.add(Integer.parseInt(ayer[0])%100);
        resultado.add(Integer.parseInt(ayer[1]));
        resultado.add(Integer.parseInt(ayer[2]));
        return resultado;
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
    public LiveData<Pair<state, Confvalues>> crearpaquetedeconfiguraciones(String neolink){
        return new datadeconfiguracion<>(appRepo.fetchdataconfigracionstate(neolink),appRepo.fetchdataconfigracionconfvalue(neolink));
    }
    public LiveData<statelimitsport> fecthlimits(String neolink){
        return appRepo.fetchlimites(neolink);
    }
    public void saveconfiguracion(String neolink,boolean beep,int beepv,boolean port, int portv, boolean gps, int gpsv, boolean tiempoentreplazos, int tiempoentreplazosv,boolean[] switchs,boolean[] switchsactuales,boolean[] superior,boolean[] inferior,String[] superiorl,String[] inferiorl,double[] valorsuperior,double[] valorinferior){
        appRepo.saveconfiguration(neolink,beep,beepv,port,portv,gps,gpsv,tiempoentreplazos,tiempoentreplazosv,switchs,switchsactuales,superior,inferior,superiorl,inferiorl,valorsuperior,valorinferior);
    }
    public LiveData<Horas> funciondedatosgeneralesk(String name,int hoyano,int hoymes, int hoydia){
        return appRepo.damedatahoyK2(name,hoyano,hoymes,hoydia,"k");
    }
    public LiveData<HorasG> funciondedatosgeneralesg(String name,int hoyano,int hoymes, int hoydia){
        return appRepo.damedatahoyG2(name,hoyano,hoymes,hoydia);
    }
    public LiveData<horasstate> funciondedatosgeneralesstate(String name,int hoyano,int hoymes, int hoydia){
        return appRepo.damedatahoyState2(name,hoyano,hoymes,hoydia);
    }
    public LiveData<notihist> funcionderecoleccionderegistro(String neolink){
        return appRepo.fetchregistro(neolink,datetoday.getValue().get(0),datetoday.getValue().get(1));
    }

}
