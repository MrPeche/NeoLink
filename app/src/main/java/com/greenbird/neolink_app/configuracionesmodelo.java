package com.greenbird.neolink_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.neolink_app.R;
import com.example.neolink_app.actividadbase;
import com.example.neolink_app.adaptadores.adaptadordelimitesneolinkexterior;
import com.example.neolink_app.clases.configuracion.Confvalues;
import com.example.neolink_app.clases.configuracion.state;
import com.example.neolink_app.clases.configuracion.statePortsactive;
import com.example.neolink_app.clases.configuracion.statelimitsport;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class configuracionesmodelo extends Fragment {
    private String neolinkname;
    private MasterDrawerViewModel archi;
    private SwitchCompat beepdelsistema;
    private EditText tiempoentredatos;
    private SwitchCompat portRQ;
    private SwitchCompat gpsRQ;
    //private TextView nombredelwifi;
    private TextView firmware;
    private TextView Gps;
    private TextView lastupdate;


    private TextView port1active;
    private TextView port2active;
    private TextView port3active;
    private TextView port4active;

    private state stateobj;
    private Confvalues confvobj;

    private RecyclerView limitsrc;
    private adaptadordelimitesneolinkexterior adapter;
    private GridLayoutManager glm;


    private ArrayList<android.util.Pair<android.util.Pair<ArrayList<SwitchCompat>,ArrayList<EditText>>, android.util.Pair<ArrayList<EditText>,ArrayList<EditText>>>> datosdelimites = new ArrayList<>();
    private ArrayList<android.util.Pair<android.util.Pair<ArrayList<Integer>,ArrayList<Double>>, android.util.Pair<ArrayList<Double>,ArrayList<Double>>>> datosdelimitesoriginales = new ArrayList<>();


    private int data;


    public configuracionesmodelo() {
        // Required empty public constructor
    }
    /*
    public static configuracionesmodelo newInstance(String param1, String param2) {
        configuracionesmodelo fragment = new configuracionesmodelo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.neolinkname = configuracionesmodeloArgs.fromBundle(getArguments()).getNeonode();
        }
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuracionesmodelo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        beepdelsistema = view.findViewById(R.id.Beepdelsistema);
        tiempoentredatos = view.findViewById(R.id.tiempoentredatos);
        portRQ = view.findViewById(R.id.portrq);
        gpsRQ = view.findViewById(R.id.gpsrq);
        gpsRQ.setVisibility(View.GONE);
        //nombredelwifi = view.findViewById(R.id.nombredelwifi);
        firmware = view.findViewById(R.id.firmware);
        Gps = view.findViewById(R.id.gps);
        lastupdate = view.findViewById(R.id.lastupdate);

        port1active = view.findViewById(R.id.Puertoactivo1);
        port2active = view.findViewById(R.id.Puertoactivo2);
        port3active = view.findViewById(R.id.Puertoactivo3);
        port4active = view.findViewById(R.id.Puertoactivo4);

        limitsrc = view.findViewById(R.id.rclimitesviews);

        archi.crearpaquetedeconfiguraciones(neolinkname).observe(getViewLifecycleOwner(), new Observer<Pair<state, Confvalues>>() {
            @Override
            public void onChanged(Pair<state, Confvalues> stateConfvaluesPair) {
                if(stateConfvaluesPair.first!=null){
                    arrangedataState(stateConfvaluesPair.first);
                    stateobj = stateConfvaluesPair.first;
                }
                if(stateConfvaluesPair.second!=null){
                    arrangedataconfvalues(stateConfvaluesPair.second);
                    confvobj = stateConfvaluesPair.second;
                }
            }
        });
        tiempoentredatos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String nm = tiempoentredatos.getText().toString();
                if((nm.equals("10"))||(nm.equals("15"))||(nm.equals("20"))||(nm.equals("30"))||(nm.equals("60"))){
                    tiempoentredatos.setError(null);
                } else
                    tiempoentredatos.setError("Solo 10, 15, 20, 30 y 60 min son aceptados",null);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ((actividadbase)getActivity()).fabcheck();
        ((actividadbase)getActivity()).fabaparecer();
        ((actividadbase)getActivity()).fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Snackbar Avizoguardado = Snackbar.make(v,"Configuración Guardada", BaseTransientBottomBar.LENGTH_SHORT);
                final Snackbar Avizonoguardo = Snackbar.make(v,"No hay cambio detectado", BaseTransientBottomBar.LENGTH_SHORT);
                doineedtosave(Avizoguardado,Avizonoguardo);
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        ((actividadbase)getActivity()).fabdesparecer();
    }

    private void arrangedataconfvalues(Confvalues object){
        if(object.BEEP_EN!=0){
            beepdelsistema.setChecked(true);
        } else beepdelsistema.setChecked(false);
        //nombredelwifi.setText(object.WIFI_SSID);
        tiempoentredatos.setText(desegundosaminutos(object.SLEEP_TIME));
        if(object.PORT_RQ!=0){
            portRQ.setChecked(true);
        } else portRQ.setChecked(false);
        if(object.GPS_RQ!=0){
            gpsRQ.setChecked(true);
        } else gpsRQ.setChecked(false);
    }
    private void arrangedataState(state object){
        firmware.setText(object.Firmware);
        String gps = object.GPS.LAT+","+object.GPS.LONG;
        Gps.setText(gps);
        lastupdate.setText(arrangelastupload(object.LastUpload));
        arrangeportsactive(object.Port);
        arrangenewlimits(object.Limits);
    }
    private String arrangelastupload(String A){
        String[] list = A.split("\\.");
        return list[2] + "/" + list[1] + "/"+ list[0] + "  " + list[3] + ":" + list[4];
    }
    private void arrangeportsactive(statePortsactive stuff){
        if(!stuff.Port1_Active.equals("NaN")){
            port1active.setVisibility(View.VISIBLE);
        } else port1active.setVisibility(View.GONE);
        if(!stuff.Port2_Active.equals("NaN")){
            port2active.setVisibility(View.VISIBLE);
        } else port2active.setVisibility(View.GONE);
        if(!stuff.Port3_Active.equals("NaN")){
            port3active.setVisibility(View.VISIBLE);
        }  else port3active.setVisibility(View.GONE);
        if(!stuff.Port4_Active.equals("NaN")){
            port4active.setVisibility(View.VISIBLE);
        } else port4active.setVisibility(View.GONE);
    }
    private String desegundosaminutos(int sec){
        return String.valueOf(sec/60);
    }
    private int deminutosasegundos(String min){
        int valor = Integer.parseInt(min);
        if(valor<10){
            valor=10;
        } else if((10<valor)&&(valor<15)){
            valor=15;
        } else if((15<valor)&&(valor<20)){
            valor=20;
        } else if((20<valor)&&(valor<30)){
            valor=30;
        } else if((30<valor)&&(valor<40)){
            valor=40;
        } else if((40<valor)&&(valor<60)){
            valor=60;
        } else if(valor<60){
            valor=60;
        }
        return valor*60;
    }

    private void doineedtosave(Snackbar positivo, Snackbar negativo){
        Boolean beepB = beepdelsistema.isChecked() == translatebool(confvobj.BEEP_EN);
        Boolean tiempoentredatosB = tiempoentredatos.getText().toString().equals(desegundosaminutos(confvobj.SLEEP_TIME));
        Boolean portRQB = portRQ.isChecked() == translatebool(confvobj.PORT_RQ);
        Boolean gpsRQB = gpsRQ.isChecked() == translatebool(confvobj.GPS_RQ);

        //Boolean e = checklimits(stateobj.Limits);
        ArrayList<ArrayList<Boolean>> RVswitchs = new ArrayList<>();
        ArrayList<ArrayList<Boolean>> RVlimsuperior = new ArrayList<>();
        ArrayList<ArrayList<Boolean>> RVliminferior = new ArrayList<>();
        ArrayList<ArrayList<Boolean>> RVespecial = new ArrayList<>();
        ArrayList<ArrayList<Boolean>> swactuales = new ArrayList<>();
        ArrayList<ArrayList<String>> limsuperioractual = new ArrayList<>();
        ArrayList<ArrayList<String>> liminferioractual = new ArrayList<>();
        ArrayList<ArrayList<String>> limespecialactual = new ArrayList<>();
        ArrayList<ArrayList<Double>> limsuperiororiginal = new ArrayList<>();
        ArrayList<ArrayList<Double>> liminferiororiginal = new ArrayList<>();
        ArrayList<ArrayList<Double>> limespecialoriginal = new ArrayList<>();
        for(int i=0;i<datosdelimites.size();i++){
            RVswitchs.add(revizarlosswitchs(datosdelimitesoriginales.get(i).first.first,datosdelimites.get(i).first.first));
            RVlimsuperior.add(revizarloslimites(datosdelimitesoriginales.get(i).first.second,datosdelimites.get(i).first.second));
            RVliminferior.add(revizarloslimites(datosdelimitesoriginales.get(i).second.first,datosdelimites.get(i).second.first));
            RVespecial.add(revizarloslimites(datosdelimitesoriginales.get(i).second.second,datosdelimites.get(i).second.second));
            swactuales.add(damelosvaloresdelosswitsh(datosdelimites.get(i).first.first));
            limsuperioractual.add(damelosvaloresdeloslimites(datosdelimites.get(i).first.second));
            liminferioractual.add(damelosvaloresdeloslimites(datosdelimites.get(i).second.first));
            limespecialactual.add(damelosvaloresdeloslimites(datosdelimites.get(i).second.second));
            limsuperiororiginal.add(datosdelimitesoriginales.get(i).first.second);
            liminferiororiginal.add(datosdelimitesoriginales.get(i).second.first);
            limespecialoriginal.add(datosdelimitesoriginales.get(i).second.second);
        }

        if(!(beepB&&tiempoentredatosB&&portRQB&&gpsRQB&&analizarelarregloboleano(RVswitchs)&&analizarelarregloboleano(RVlimsuperior)&&analizarelarregloboleano(RVliminferior)&&analizarelarregloboleano(RVespecial))){
            positivo.show();
            archi.saveconfiguracion2(neolinkname,!beepB,translateint(beepdelsistema.isChecked()),!portRQB,translateint(portRQ.isChecked()),!gpsRQB,translateint(gpsRQ.isChecked()),!tiempoentredatosB,deminutosasegundos(tiempoentredatos.getText().toString()),RVswitchs,swactuales,RVlimsuperior,RVliminferior,RVespecial,limsuperioractual,liminferioractual,limespecialactual,limsuperiororiginal,liminferiororiginal,limespecialoriginal);
        } else negativo.show();
    }
    private Boolean translatebool(int o){
        return o != 0;
    }
    private int translateint(boolean o){
        if(o){
            return 1;
        } else return 0;
    }

    private ArrayList<Boolean> damelosvaloresdelosswitsh(ArrayList<SwitchCompat> switchs){
        ArrayList<Boolean> resultado = new ArrayList<>();
        for(SwitchCompat sw:switchs){
            resultado.add(sw.isChecked());
        }
        return resultado;
    }
    private ArrayList<String> damelosvaloresdeloslimites(ArrayList<EditText> valores){
        ArrayList<String> resultado = new ArrayList<>();
        for(EditText et:valores){
            resultado.add(et.getText().toString());
        }
        return resultado;
    }
    private ArrayList<Boolean> revizarlosswitchs(ArrayList<Integer> original,ArrayList<SwitchCompat> switchs){
        ArrayList<Boolean> resultado = new ArrayList<>();
        for(int i=0;i<original.size();i++){
            resultado.add(original.get(i)==translateint(switchs.get(i).isChecked()));
        }
        return resultado;
    }
    private ArrayList<Boolean> revizarloslimites(ArrayList<Double> original,ArrayList<EditText> lim){
        ArrayList<Boolean> resultado = new ArrayList<>();
        for(int i=0;i<original.size();i++){
            resultado.add(checklimitstats(lim.get(i).getText().toString(),original.get(i)));
        }
        return resultado;
    }
    private boolean checklimitstats(String actual,double antes){
        if(actual.equals("")){
            return true;
        } else return actual.equals(String.valueOf(antes));
    }

    private Boolean analizarelarregloboleano(ArrayList<ArrayList<Boolean>> obj){
        boolean a = true;
        for(ArrayList<Boolean> port:obj){
            for(Boolean e:port){
                a = a&&e;
            }
        }
        return a;
    }
    private void arrangenewlimits(statelimitsport limits){
        adapter = new adaptadordelimitesneolinkexterior(limits,getActivity());
        glm = new GridLayoutManager(getActivity(),1);
        limitsrc.setLayoutManager(glm);
        limitsrc.setAdapter(adapter);
        this.datosdelimites = adapter.entregardatosporpuerto();
        this.datosdelimitesoriginales = adapter.entregardatosporpuertooriginales();
    }



}