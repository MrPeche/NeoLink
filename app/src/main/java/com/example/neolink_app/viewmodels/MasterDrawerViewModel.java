package com.example.neolink_app.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.text.format.DateUtils;
import android.view.View;

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
import com.example.neolink_app.clases.paqueteneolinkasociados;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

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
    public final MutableLiveData<String> nuevoNeonodo = new MutableLiveData<>();
    public final MutableLiveData<String> comentarionuevo = new MutableLiveData<>();
    public final MutableLiveData<String[]> editarcomentario= new MutableLiveData<>();
    private UserInfoRepo appRepo;
    private manejadordedispositivo FCM;
    private String nombredecorreo;
    private MutableLiveData<String> puid = new MutableLiveData<>();
    private String uid;
    private String estadofamiliar;
    private String uidreal;
    private Calendar ahora = Calendar.getInstance();
    public MutableLiveData<ArrayList<Integer>> date = new MutableLiveData<>();
    public MutableLiveData<Integer> datemode = new MutableLiveData<>();
    public MutableLiveData<String> neolinkF = new MutableLiveData<>();
    public MutableLiveData<String> neolinkGPS = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Integer>> datetoday = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Integer>> datebefore = new MutableLiveData<>();
    public ArrayList<OLDneolinksboleto> listacompleta = new ArrayList<>();
    public Snackbar avizonoerespadre;
    private MutableLiveData<paqueteneolinkasociados> dispositivos = new MutableLiveData<>();
    //private paqueteneolinkasociados dispositivos;

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
        this.uidreal = uid;
        this.puid.setValue(uid);
        FCM.guardardispositivo(uid);
    }
    public void ponercorreo(String correo){
        this.nombredecorreo = correo;
    }
    public LiveData<Pair<Boolean,String>> validaruid(){
        return appRepo.validarlaposicionenlafamilia(this.uid);
    }
    public void espadre(){
        this.estadofamiliar="padre";
    }
    public void eshijo(String uidpadre){
        this.estadofamiliar="hijo";
        this.uid = uidpadre;
        this.puid.setValue(uidpadre);
    }
    public void getLiveNL(){
        //Usuarioneolinks = appRepo.dameneolinks(uid);
        Usuarioneolinks = Transformations.switchMap(puid, new Function<String, LiveData<OWNERitems>>() {
            @Override
            public LiveData<OWNERitems> apply(String input) {
                return appRepo.dameneolinks(input);
            }
        });
    }
    public LiveData<OWNERitems> dametodoslosneolinks(){
        return Transformations.switchMap(puid, new Function<String, LiveData<OWNERitems>>() {
            @Override
            public LiveData<OWNERitems> apply(String input) {
                return appRepo.dameneolinks2(input);
            }
        });
    }
    public LiveData<paqueteneolinkasociados> gettodoelpaqueteneolinkasociado(){
        return Transformations.switchMap(puid, new Function<String, LiveData<paqueteneolinkasociados>>() {
            @Override
            public LiveData<paqueteneolinkasociados> apply(String input) {
                return Transformations.switchMap(appRepo.getneolinks(input), new Function<OWNERitems, LiveData<paqueteneolinkasociados>>() {
                    @Override
                    public LiveData<paqueteneolinkasociados> apply(OWNERitems input) {
                        if(input!=null)
                            return appRepo.getpaquetecompletodedispositivos(input.damelista());
                        else return null;
                    }
                });
            }
        });
        /*
        return Transformations.switchMap(appRepo.getneolinks(this.uid), new Function<OWNERitems, LiveData<paqueteneolinkasociados>>() {
            @Override
            public LiveData<paqueteneolinkasociados> apply(OWNERitems input) {
                return appRepo.getpaquetecompletodedispositivos(input.damelista());
            }
        });
         */
    }
    public LiveData<ArrayList<Pair<ArrayList<String>,ArrayList<GPS>>>> recibirtodoslosgps(){
        return Transformations.switchMap(dispositivos, new Function<paqueteneolinkasociados, LiveData<ArrayList<Pair<ArrayList<String>, ArrayList<GPS>>>>>() {
            @Override
            public LiveData<ArrayList<Pair<ArrayList<String>, ArrayList<GPS>>>> apply(paqueteneolinkasociados input) {
                return appRepo.obtenerelgpsdelosdispositivos(input);
            }
        });
        //return appRepo.obtenerelgpsdelosdispositivos(this.dispositivos);
    }
    public void guardarelpaquetedelneolinkasociado(paqueteneolinkasociados obj){
        dispositivos.setValue(obj);
        //this.dispositivos = obj;
    }
    public paqueteneolinkasociados damepaqueteneolinksasociado(){
        return dispositivos.getValue();
        //return this.dispositivos;
    }
    public Boolean cualeselestadofamiliar(){ return this.estadofamiliar.equals("hijo");}
    public void actualizaravizonoerespadre(View vista){ Snackbar.make(vista,"Su cuenta no tiene permiso para esta acción", BaseTransientBottomBar.LENGTH_SHORT).show();}
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
    public LiveData<OLDneolinksboleto> dameneonodos(String name){
        return Transformations.switchMap(puid, new Function<String, LiveData<OLDneolinksboleto>>() {
            @Override
            public LiveData<OLDneolinksboleto> apply(String input) {
                return appRepo.damenodos2(input,name);
            }
        });
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
                    return appRepo.fetchestasemana(neolink, figurarlasemana());
                } else if(input==2){
                    return appRepo.fetchestemes(neolink, Objects.requireNonNull(datetoday.getValue()));
                } else if(input==3){
                    return appRepo.fetchesteano(neolink, figurarlosmeses());
                } else {
                    return appRepo.fetchdiasrandom(neolink,sacarlosdiasentre(datechoosen.getValue()));
                }
            }
        });
    }
    private ArrayList<ArrayList<Integer>> sacarlosdiasentre(String lineadefecha){
        ArrayList<ArrayList<Integer>> tiempo = new ArrayList<>();
        ArrayList<ArrayList<Integer>> los2dias = manejareltiempodesdehasta(manejareldesdehasta(lineadefecha));
        Calendar datedesde = Calendar.getInstance();
        Calendar datehasta = Calendar.getInstance();
        datedesde.set(los2dias.get(0).get(0),los2dias.get(0).get(1)-1,los2dias.get(0).get(2));
        datehasta.set(los2dias.get(1).get(0),los2dias.get(1).get(1)-1,los2dias.get(1).get(2));
        datedesde.get(Calendar.DATE);
        datehasta.get(Calendar.DATE);
        while(!eselmismodia(datedesde,datehasta)){
            tiempo.add(translatesemana(datehasta));
            datehasta.add(Calendar.DATE,-1);
        }
        tiempo.add(translatesemana(datehasta));
        return tiempo;
    }
    private boolean eselmismodia(Calendar o1, Calendar o2){
        return (o1.get(Calendar.YEAR) == o2.get(Calendar.YEAR)) && (o1.get(Calendar.MONTH) == o2.get(Calendar.MONTH)) && o1.get(Calendar.DAY_OF_MONTH) == o2.get(Calendar.DAY_OF_MONTH);
    }
    private ArrayList<String[]> manejareldesdehasta(String data){
        ArrayList<String[]> resultado = new ArrayList<>();
        String[] dias = data.replace(" ","").split("-");
        for(String dia:dias){
            String[] fecha = dia.split("/");
            resultado.add(fecha);
        }
        return resultado;
    }
    private ArrayList<ArrayList<Integer>> manejareltiempodesdehasta(ArrayList<String[]> date){
        ArrayList<ArrayList<Integer>> resultado = new ArrayList<>();
        for(String[] day:date){
            ArrayList<Integer> dia = new ArrayList<>();
            dia.add(translatetoint(day[0],0));
            dia.add(translatetoint(day[1],1));
            dia.add(translatetoint(day[2],2));
            resultado.add(dia);
        }
        return resultado;
    }
    private Integer translatetoint(String line,int modo){
        if(modo==0){
            return Integer.parseInt(line);
        } else if(modo==1){
            if(line.charAt(0)=='0'){
                return Integer.parseInt(line.substring(1,2));
            } else return Integer.parseInt(line);
        } else{
            if(line.charAt(0)=='0'){
                return Integer.parseInt(line.substring(1,2));
            } else return Integer.parseInt(line);
        }
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
    private ArrayList<ArrayList<Integer>> figurarlasemana(){
        ArrayList<ArrayList<Integer>> dias = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for(int i = 0; i<7;i++){
            dias.add(translatesemana(calendar));
            calendar.add(Calendar.DATE,-1);
        }
        //int day = calendar.get(Calendar.DAY_OF_WEEK);
        /*
        for(int i = 0;i<day-1;i++){
            int heyo = i+2-day;//(day-i-((day-1-i)*2))
            calendar.add(Calendar.DATE,i+2-day);
            dias.add(calendar);
        }
         */
        /*
        if(day==1){
            dias.add(translatesemana(calendar));
        } else{
            for(int i = 0;i<day-1;i++){
                if (i != 0) {
                    calendar.add(Calendar.DATE, -1);
                }
                dias.add(translatesemana(calendar));
            }
        }

         */

        return dias;
    }
    private ArrayList<ArrayList<Integer>> figurarlosmeses(){
        ArrayList<ArrayList<Integer>> meses = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for(int i=0;i<12;i++){
            meses.add(translatesemana(calendar));
            calendar.add(Calendar.MONTH,-1);
        }
        return meses;
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
    public LiveData<Boolean> agregarneolink2(String codigo,ArrayList<String> listactual){
        return appRepo.agregarneolink2(codigo,uid,listactual);
    }
    public LiveData<Boolean> agregarneonodo(String codigo,String neolink,ArrayList<String> neonodolista){
        return appRepo.agregarneonodos(codigo,neolink,neonodolista);
    }
    public LiveData<Boolean> segraboelneolink(){
        return neolinkguardado;
    }

    public void avizarquehayuncomentarionuevo(String referencia){
        comentarionuevo.setValue(referencia);
    }
    public void vaciarelavizodecomentarionuevo(){
        comentarionuevo.setValue(null);
    }
    public LiveData<String> haycomentarionuevo(){
        return comentarionuevo;
    }
    public void guardarcomentarioneolink(String neolink, String mensaje){
        Calendar ahorita = Calendar.getInstance();
        ArrayList<Integer> heute = new ArrayList<>();
        heute.add(ahorita.get(Calendar.YEAR)%100);
        heute.add(ahorita.get(Calendar.MONTH)+1);
        heute.add(ahorita.get(Calendar.DAY_OF_MONTH));
        heute.add(ahorita.get(Calendar.HOUR_OF_DAY));
        heute.add(ahorita.get(Calendar.MINUTE));
        heute.add(ahorita.get(Calendar.SECOND));
        appRepo.guardarelmensaje(neolink,heute.get(0),heute.get(1),heute.get(2),heute.get(3),heute.get(4),heute.get(5),mensaje+"5MS5"+nombredecorreo);
    }
    public void borrarcomentario(String neolink,String ano,String mes,String dia,String hora){
        appRepo.borrarmensaje(neolink,ano,mes,dia,hora);
    }

    public void neolinkguardadopositivo(){
        MediatorLiveData<Boolean> transaccion = new MediatorLiveData<>();
        transaccion.setValue(null);
        neolinkguardado = transaccion;
    }
    public void guardarneolinkdeldialogo(String neolink){
        nuevoNeolink.setValue(neolink);
    }
    public void guardarneonododialogo(String neonodo){
        nuevoNeonodo.setValue(neonodo);
    }
    public void vacialneolinkdeldialogo(){
        nuevoNeolink.setValue(null);
    }
    public LiveData<String> neolinkdeldialogo(){
        return nuevoNeolink;
    }
    public LiveData<String> neonododialogo(){
        return nuevoNeonodo;
    }
    public LiveData<Pair<state, Confvalues>> crearpaquetedeconfiguraciones(String neolink){
        return new datadeconfiguracion<>(appRepo.fetchdataconfigracionstate(neolink),appRepo.fetchdataconfigracionconfvalue(neolink));
    }
    public LiveData<statelimitsport> fecthlimits(String neolink){
        return appRepo.fetchlimites(neolink);
    }
    public void saveconfiguracion2(String neolink,boolean beep,int beepv,boolean port, int portv, boolean gps, int gpsv, boolean tiempoentreplazos, int tiempoentreplazosv,ArrayList<ArrayList<Boolean>> switchscomparados,ArrayList<ArrayList<Boolean>> switchsactuales,ArrayList<ArrayList<Boolean>> superiorescomparados,ArrayList<ArrayList<Boolean>> inferiorescomparados,ArrayList<ArrayList<Boolean>> especialcomparados,ArrayList<ArrayList<String>> limsuperioractual,ArrayList<ArrayList<String>> liminferioractual,ArrayList<ArrayList<String>> limespecialactual,ArrayList<ArrayList<Double>> valorsuperiororiginal,ArrayList<ArrayList<Double>> valorinferiororiginal,ArrayList<ArrayList<Double>> valorespecialoriginal,ArrayList<Boolean> depthport, ArrayList<Integer> valueport){
        appRepo.saveconfiguration2(neolink,beep,beepv,port,portv,gps,gpsv,tiempoentreplazos,tiempoentreplazosv,switchscomparados,switchsactuales,superiorescomparados,inferiorescomparados,especialcomparados,limsuperioractual,liminferioractual,limespecialactual,valorsuperiororiginal,valorinferiororiginal,valorespecialoriginal,depthport,valueport);
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
    public LiveData<ArrayList<notihist>> funcionderecoleccionderegistro2(ArrayList<String> dispositivos){
        return appRepo.fetchtodoslosregistros(dispositivos,datetoday.getValue().get(0),datetoday.getValue().get(1));
    }
    public LiveData<notihist> funcionrecoleccionderegristro3(ArrayList<String> dispositivos){
        return appRepo.fetchtodoslosregistros2(dispositivos,datetoday.getValue().get(0),datetoday.getValue().get(1));
    }
    public void guardartokenparavincular(String token){ appRepo.guardartokendevinculo(token,this.uid); }
    public LiveData<String> fetchuiddelhijo(String nombredelhijo){ return appRepo.fetchuiddelhijo(this.uid,nombredelhijo);}
    public void borrarhijo(String uidhijo){ appRepo.borrarhijo(uidhijo,this.uid);}
    public LiveData<String> tokendevinculoanterior(){
        return appRepo.retrivetokendevinculo(this.uid);
    }

    public LiveData<Pair<ArrayList<String>,ArrayList<String>>> mostrarhijos(){
        return appRepo.mostrarhijos(this.uid);
    }
    public LiveData<String[]> dialogoeditarmensaje(){
        return editarcomentario;
    }
    public void dialogoeditarmensajesubir(String fecha,String hora,String contenido){
        editarcomentario.setValue(new String[]{fecha, hora, contenido});
    }
    public void dilaogoeditarmensajeborrar(){
        editarcomentario.setValue(null);
    }
    public void editarmensajes(String fecha,String hora, String contenido){
        appRepo.editarmensaje(fecha,hora,contenido);
    }
    public void borrarunneolink(String neolink,ArrayList<String> packneolinks){
        appRepo.borrarunneolink(neolink,this.uid,packneolinks);}
    public void borrarunneonodo(String neolink, ArrayList<String> packneonodo){
        appRepo.borrarneonodo(neolink,packneonodo);}
}
