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
import android.widget.EditText;
import android.widget.TextView;

import com.example.neolink_app.R;
import com.example.neolink_app.clases.configuracion.Confvalues;
import com.example.neolink_app.clases.configuracion.state;
import com.example.neolink_app.clases.configuracion.statePortsactive;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

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
    private TextView port1active;
    private TextView port2active;
    private TextView port3active;
    private TextView port4active;




    public configuracionesmodelo() {
        // Required empty public constructor
    }

    public static configuracionesmodelo newInstance(String param1, String param2) {
        configuracionesmodelo fragment = new configuracionesmodelo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

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
        port1active = view.findViewById(R.id.Puertoactivo1);
        port2active = view.findViewById(R.id.Puertoactivo2);
        port3active = view.findViewById(R.id.Puertoactivo3);
        port4active = view.findViewById(R.id.Puertoactivo4);

        archi.crearpaquetedeconfiguraciones(neolinkname).observe(getViewLifecycleOwner(), new Observer<Pair<state, Confvalues>>() {
            @Override
            public void onChanged(Pair<state, Confvalues> stateConfvaluesPair) {
                if(stateConfvaluesPair.first!=null){
                    arrangedataState(stateConfvaluesPair.first);
                }
                if(stateConfvaluesPair.second!=null){
                    arrangedataconfvalues(stateConfvaluesPair.second);
                }
            }
        });
    }
    private void arrangedataconfvalues(Confvalues object){
        if(object.BEEP_EN!=0){
            beepdelsistema.setChecked(true);
        } else beepdelsistema.setChecked(false);
        nombredelwifi.setText(object.WIFI_SSID_DEFAULT);
        tiempoentredatos.setText(String.valueOf(object.SLEEP_TIME));
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
    }
    private String arrangelastupload(String A){
        String[] list = A.split("\\.");
        return list[2] + "/" + list[1] + "/"+ list[0] + " " + list[3] + ":" + list[4];
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
}