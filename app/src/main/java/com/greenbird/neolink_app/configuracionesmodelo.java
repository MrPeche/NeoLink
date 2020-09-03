package com.greenbird.neolink_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.neolink_app.R;
import com.example.neolink_app.actividadbase;
import com.example.neolink_app.clases.configuracion.Confvalues;
import com.example.neolink_app.clases.configuracion.state;
import com.example.neolink_app.clases.configuracion.statePortsactive;
import com.example.neolink_app.clases.configuracion.statelimitsport;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class configuracionesmodelo extends Fragment {
    private String neolinkname;
    private MasterDrawerViewModel archi;
    private SwitchCompat beepdelsistema;
    private EditText tiempoentredatos;
    private SwitchCompat portRQ;
    private SwitchCompat gpsRQ;
    private TextView nombredelwifi;
    private TextView firmware;
    private TextView Gps;
    private TextView lastupdate;
    private LinearLayout limitP1layout;
    private LinearLayout limitP1sensork;
    private SwitchCompat limitP1potencialmatricial;
    private LinearLayout limitP1PMlimitesuperior;
    private LinearLayout limitP1PMlimiteinferior;
    private EditText limitP1PMLS;
    private EditText limitP1PMLI;
    private SwitchCompat limitP1temperatura;
    private LinearLayout limitP1TEMPlimitesuperior;
    private LinearLayout limitP1TEMPlimiteinferior;
    private EditText limitP1TEMLS;
    private EditText limitP1TEMLI;
    private LinearLayout limitP1sensorg;
    private SwitchCompat limitP1humedaddelsuelo;
    private LinearLayout limitP1HSlimitesuperior;
    private LinearLayout limitP1HSlimiteinferior;
    private EditText limitP1HSLS;
    private EditText limitP1HSLI;

    private SwitchCompat limitP1temperaturadelsuelo;
    private LinearLayout limitP1TEMPSlimitesuperior;
    private LinearLayout limitP1TEMPSlimiteinferior;
    private EditText limitP1TMSLS;
    private EditText limitP1TMSLI;

    private SwitchCompat limitP1conductividadelectrica;
    private LinearLayout limitP1CElimitesuperior;
    private LinearLayout limitP1CElimiteinferior;
    private EditText limitP1CELS;
    private EditText limitP1CELI;

    private TextView port1active;
    private TextView port2active;
    private TextView port3active;
    private TextView port4active;

    private state stateobj;
    private Confvalues confvobj;

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
        nombredelwifi = view.findViewById(R.id.nombredelwifi);
        firmware = view.findViewById(R.id.firmware);
        Gps = view.findViewById(R.id.gps);
        lastupdate = view.findViewById(R.id.lastupdate);
        limitP1layout = view.findViewById(R.id.puerto1layout);
        limitP1sensork = view.findViewById(R.id.puerto1sensork);
        limitP1potencialmatricial = view.findViewById(R.id.puerto1potencialmatricial);
        limitP1PMlimitesuperior = view.findViewById(R.id.limitP1limitesuperioPM);
        limitP1PMlimiteinferior = view.findViewById(R.id.limitP1limiteinferiorPM);
        limitP1PMLS = view.findViewById(R.id.port1kPMlimitesuperior);
        limitP1PMLI = view.findViewById(R.id.port1kPMlimiteinferior);
        limitP1temperatura = view.findViewById(R.id.puerto1temperatura);
        limitP1TEMPlimitesuperior = view.findViewById(R.id.limitP1limitesuperiorTEMP);
        limitP1TEMPlimiteinferior = view.findViewById(R.id.limitP1limiteinferiorTEMP);
        limitP1TEMLS = view.findViewById(R.id.port1kTEMPlimitesuperior);
        limitP1TEMLI = view.findViewById(R.id.port1kTEMPlimiteinferior);
        limitP1sensorg = view.findViewById(R.id.puerto1sensorG);
        limitP1humedaddelsuelo = view.findViewById(R.id.puerto1Humedaddelsuelo);
        limitP1HSlimitesuperior = view.findViewById(R.id.limitP1limitesuperioHS);
        limitP1HSlimiteinferior = view.findViewById(R.id.limitP1limiteinferiorHS);
        limitP1HSLS = view.findViewById(R.id.port1gHSlimitesuperior);
        limitP1HSLI = view.findViewById(R.id.port1gHSlimiteinferior);
        limitP1temperaturadelsuelo = view.findViewById(R.id.puerto1temperaturadelsuelo);
        limitP1TEMPSlimitesuperior = view.findViewById(R.id.limitP1limitesuperiorTEMPS);
        limitP1TEMPSlimiteinferior = view.findViewById(R.id.limitP1limiteinferiorTEMPS);
        limitP1TMSLS = view.findViewById(R.id.port1gTEMPSlimitesuperior);
        limitP1TMSLI = view.findViewById(R.id.port1gTEMPSlimiteinferior);

        limitP1temperaturadelsuelo = view.findViewById(R.id.puerto1temperaturadelsuelo);
        limitP1TEMPSlimitesuperior = view.findViewById(R.id.limitP1limitesuperiorTEMPS);
        limitP1TEMPSlimiteinferior = view.findViewById(R.id.limitP1limiteinferiorTEMPS);
        limitP1TMSLS = view.findViewById(R.id.port1gTEMPSlimitesuperior);
        limitP1TMSLI = view.findViewById(R.id.port1gTEMPSlimiteinferior);

        limitP1conductividadelectrica = view.findViewById(R.id.puerto1conductividadelectricadelsuelo);
        limitP1CElimitesuperior = view.findViewById(R.id.limitP1limitesuperiorCS);
        limitP1CElimiteinferior = view.findViewById(R.id.limitP1limiteinferiorCS);
        limitP1CELS = view.findViewById(R.id.port1gCSlimitesuperior);
        limitP1CELI = view.findViewById(R.id.port1gCSlimiteinferior);

        port1active = view.findViewById(R.id.Puertoactivo1);
        port2active = view.findViewById(R.id.Puertoactivo2);
        port3active = view.findViewById(R.id.Puertoactivo3);
        port4active = view.findViewById(R.id.Puertoactivo4);

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

        limitP1potencialmatricial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    limitP1PMlimitesuperior.setVisibility(View.VISIBLE);
                    limitP1PMlimiteinferior.setVisibility(View.VISIBLE);
                } else {
                    limitP1PMlimitesuperior.setVisibility(View.GONE);
                    limitP1PMlimiteinferior.setVisibility(View.GONE);
                }
            }
        });
        limitP1temperatura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    limitP1TEMPlimitesuperior.setVisibility(View.VISIBLE);
                    limitP1TEMPlimiteinferior.setVisibility(View.VISIBLE);
                } else {
                    limitP1TEMPlimitesuperior.setVisibility(View.GONE);
                    limitP1TEMPlimiteinferior.setVisibility(View.GONE);
                }
            }
        });
        limitP1temperaturadelsuelo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    limitP1TEMPSlimitesuperior.setVisibility(View.VISIBLE);
                    limitP1TEMPSlimiteinferior.setVisibility(View.VISIBLE);
                } else {
                    limitP1TEMPSlimitesuperior.setVisibility(View.GONE);
                    limitP1TEMPSlimiteinferior.setVisibility(View.GONE);
                }
            }
        });
        limitP1conductividadelectrica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    limitP1CElimitesuperior.setVisibility(View.VISIBLE);
                    limitP1CElimiteinferior.setVisibility(View.VISIBLE);
                } else {
                    limitP1CElimitesuperior.setVisibility(View.GONE);
                    limitP1CElimiteinferior.setVisibility(View.GONE);
                }
            }
        });
        limitP1humedaddelsuelo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    limitP1HSlimitesuperior.setVisibility(View.VISIBLE);
                    limitP1HSlimiteinferior.setVisibility(View.VISIBLE);
                } else {
                    limitP1HSlimitesuperior.setVisibility(View.GONE);
                    limitP1HSlimiteinferior.setVisibility(View.GONE);
                }
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
        nombredelwifi.setText(object.WIFI_SSID);
        tiempoentredatos.setText(desegundosaminutos(object.SLEEP_TIME));
        if(object.PORT_RQ!=0){
            portRQ.setChecked(true);
        } else portRQ.setChecked(false);
        if(object.GPS_RQ!=0){
            gpsRQ.setChecked(true);
        } else gpsRQ.setChecked(false);
    }
    private void arrangedataState(state object){
        firmware.setText(String.valueOf(object.Firmware));
        String gps = object.GPS.LAT+","+object.GPS.LONG;
        Gps.setText(gps);
        lastupdate.setText(arrangelastupload(object.LastUpload));
        arrangeportsactive(object.Port);
        arrangelimits(object.Limits);
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
        return Integer.parseInt(min)*60;
    }
    private void arrangelimits(statelimitsport limits){
        if(limits.dameP1()!=null){
            if(limits.dameP1().damek()!=null){
                if(limits.dameP1().damek().dameV1().damebool()!=0){
                    limitP1potencialmatricial.setChecked(true);
                    limitP1PMLI.setText(String.valueOf(limits.dameP1().damek().dameV1().dameMin()));
                    limitP1PMLS.setText(String.valueOf(limits.dameP1().damek().dameV1().dameMAX()));
                } else desapareceP1potencialmatricial();
                if(limits.dameP1().damek().dameV2().damebool()!=0){
                    limitP1temperatura.setChecked(true);
                    limitP1TEMLI.setText(String.valueOf(limits.dameP1().damek().dameV2().dameMin()));
                    limitP1TEMLS.setText(String.valueOf(limits.dameP1().damek().dameV2().dameMAX()));
                } else desapareceP1TEMP();

            } else limitP1sensork.setVisibility(View.GONE);

            if(limits.dameP1().dameG()!=null){
                if(limits.dameP1().dameG().dameV1().damebool()!=0){
                    limitP1humedaddelsuelo.setChecked(true);
                    limitP1HSLI.setText(String.valueOf(limits.dameP1().dameG().dameV1().dameMin()));
                    limitP1HSLS.setText(String.valueOf(limits.dameP1().dameG().dameV1().dameMAX()));
                } else desapareceP1HS();

                if(limits.dameP1().dameG().dameV2().damebool()!=0){
                    limitP1temperaturadelsuelo.setChecked(true);
                    limitP1TMSLI.setText(String.valueOf(limits.dameP1().dameG().dameV2().dameMin()));
                    limitP1TMSLS.setText(String.valueOf(limits.dameP1().dameG().dameV2().dameMAX()));
                } else desapareceP1TEMS();

                if(limits.dameP1().dameG().dameV3().damebool()!=0){
                    limitP1conductividadelectrica.setChecked(true);
                    limitP1CELI.setText(String.valueOf(limits.dameP1().dameG().dameV3().dameMin()));
                    limitP1CELS.setText(String.valueOf(limits.dameP1().dameG().dameV3().dameMAX()));
                } else desapareceP1CS();

            } else limitP1sensorg.setVisibility(View.GONE);

        } else limitP1layout.setVisibility(View.GONE);
        /*
        if(limits.dameP1()!=null){

        }
        if(limits.dameP1()!=null){

        }
        if(limits.dameP1()!=null){

        }

         */

    }
    private void desapareceP1potencialmatricial(){
        limitP1potencialmatricial.setChecked(false);
        limitP1PMlimitesuperior.setVisibility(View.GONE);
        limitP1PMlimiteinferior.setVisibility(View.GONE);
    }
    private void desapareceP1TEMP(){
        limitP1temperatura.setChecked(false);
        limitP1TEMPlimitesuperior.setVisibility(View.GONE);
        limitP1TEMPlimiteinferior.setVisibility(View.GONE);
    }
    private void desapareceP1HS(){
        limitP1humedaddelsuelo.setChecked(false);
        limitP1HSlimitesuperior.setVisibility(View.GONE);
        limitP1HSlimiteinferior.setVisibility(View.GONE);
    }
    private void desapareceP1TEMS(){
        limitP1temperaturadelsuelo.setChecked(false);
        limitP1TEMPSlimitesuperior.setVisibility(View.GONE);
        limitP1TEMPSlimiteinferior.setVisibility(View.GONE);
    }
    private void desapareceP1CS(){
        limitP1conductividadelectrica.setChecked(false);
        limitP1CElimitesuperior.setVisibility(View.GONE);
        limitP1CElimiteinferior.setVisibility(View.GONE);
    }
    private void doineedtosave(Snackbar positivo, Snackbar negativo){
        Boolean beepB = beepdelsistema.isChecked() == translatebool(confvobj.BEEP_EN);
        Boolean tiempoentredatosB = tiempoentredatos.getText().toString().equals(desegundosaminutos(confvobj.SLEEP_TIME));
        Boolean portRQB = portRQ.isChecked() == translatebool(confvobj.PORT_RQ);
        Boolean gpsRQB = gpsRQ.isChecked() == translatebool(confvobj.GPS_RQ);
        //Boolean e = checklimits(stateobj.Limits);
        boolean[] switchs = chekSwitchslimits(stateobj.Limits);
        boolean[] superior = checksuperiorlimits(stateobj.Limits);
        boolean[] inferior = checkinferiorlimits(stateobj.Limits);
        boolean[] switchsactuales = switchsactuales();
        String[] superiores = {limitP1HSLS.getText().toString(),limitP1TMSLS.getText().toString(),limitP1CELS.getText().toString(),limitP1PMLS.getText().toString(),limitP1TEMLS.getText().toString()};
        String[] inferiores = {limitP1HSLI.getText().toString(),limitP1TMSLI.getText().toString(),limitP1CELI.getText().toString(),limitP1PMLI.getText().toString(),limitP1TEMLI.getText().toString()};
        double[] superioress = damelosvalorelsimites(stateobj.Limits,1);
        double[] inferioress = damelosvalorelsimites(stateobj.Limits,0);
        if(!(beepB&&tiempoentredatosB&&portRQB&&gpsRQB&&checktogetherthearray(switchs)&&checktogetherthearray(superior)&&checktogetherthearray(inferior))){
            positivo.show();
            archi.saveconfiguracion(neolinkname,!beepB,translateint(beepdelsistema.isChecked()),!portRQB,translateint(portRQ.isChecked()),!gpsRQB,translateint(gpsRQ.isChecked()),!tiempoentredatosB,deminutosasegundos(tiempoentredatos.getText().toString()),switchs,switchsactuales,superior,inferior,superiores,inferiores,superioress,inferioress);
        } else negativo.show();
    }
    private Boolean translatebool(int o){
        if(o!=0){
            return true;
        } else return false;
    }
    private int translateint(boolean o){
        if(o){
            return 1;
        } else return 0;
    }
    private boolean[] switchsactuales(){
        return new boolean[]{limitP1humedaddelsuelo.isChecked(),limitP1temperaturadelsuelo.isChecked(),limitP1conductividadelectrica.isChecked(),limitP1potencialmatricial.isChecked(),limitP1temperatura.isChecked()};
    }

    private boolean[] chekSwitchslimits(statelimitsport port){
        return new boolean[]{(limitP1humedaddelsuelo.isChecked() == translatebool(port.dameP1().dameG().dameV1().damebool())),
                (limitP1temperaturadelsuelo.isChecked() == translatebool(port.dameP1().dameG().dameV2().damebool())),
                (limitP1conductividadelectrica.isChecked() == translatebool(port.dameP1().dameG().dameV3().damebool())),
                (limitP1potencialmatricial.isChecked() == translatebool(port.dameP1().damek().dameV1().damebool())),
                (limitP1temperatura.isChecked() == translatebool(port.dameP1().damek().dameV2().damebool()))};

    }
    private boolean[] checksuperiorlimits(statelimitsport port){

        return new boolean[]{checklimitstats(limitP1HSLS.getText().toString(),port.dameP1().dameG().dameV1().dameMAX()),
                checklimitstats(limitP1TMSLS.getText().toString(),port.dameP1().dameG().dameV2().dameMAX()),
                checklimitstats(limitP1CELS.getText().toString(),port.dameP1().dameG().dameV3().dameMAX()),
                checklimitstats(limitP1PMLS.getText().toString(),port.dameP1().damek().dameV1().dameMAX()),
                checklimitstats(limitP1TEMLS.getText().toString(),port.dameP1().damek().dameV2().dameMAX())};
    }
    private boolean[] checkinferiorlimits(statelimitsport port){
        return new boolean[]{checklimitstats(limitP1HSLS.getText().toString(),port.dameP1().dameG().dameV1().dameMin()),
                checklimitstats(limitP1TMSLI.getText().toString(),port.dameP1().dameG().dameV2().dameMin()),
                checklimitstats(limitP1CELI.getText().toString(),port.dameP1().dameG().dameV3().dameMin()),
                checklimitstats(limitP1PMLI.getText().toString(),port.dameP1().damek().dameV1().dameMin()),
                checklimitstats(limitP1TEMLI.getText().toString(),port.dameP1().damek().dameV2().dameMin())};
    }
    private boolean checklimitstats(String actual,double antes){
        if(actual.equals("")){
            return true;
        } else return actual.equals(String.valueOf(antes));
    }
    private boolean checktogetherthearray(boolean[] obj){
        boolean a = true;
        for(boolean e:obj){
            a = a&&e;
        }
        return a;
    }
    private double[] damelosvalorelsimites(statelimitsport obj, int opcion){
        if(opcion==0){
            return new double[]{obj.dameP1().dameG().dameV1().dameMin(),
                    obj.dameP1().dameG().dameV2().dameMin(),
                    obj.dameP1().dameG().dameV3().dameMin(),
                    obj.dameP1().damek().dameV1().dameMin(),
                    obj.dameP1().damek().dameV2().dameMin()};
        } else return new double[]{obj.dameP1().dameG().dameV1().dameMAX(),
                obj.dameP1().dameG().dameV2().dameMAX(),
                obj.dameP1().dameG().dameV3().dameMAX(),
                obj.dameP1().damek().dameV1().dameMAX(),
                obj.dameP1().damek().dameV2().dameMAX()};
    }


}