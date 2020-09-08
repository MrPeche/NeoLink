package com.example.neolink_app;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.clases.Minutos;
import com.example.neolink_app.clases.Puerto;
import com.example.neolink_app.clases.SensorG.HorasG;
import com.example.neolink_app.clases.SensorG.MinutosG;
import com.example.neolink_app.clases.SensorG.PuertoG;
import com.example.neolink_app.clases.SensorG.dataPuertoG;
import com.example.neolink_app.clases.dataPuerto;
import com.example.neolink_app.clases.database_state.horasstate;
import com.example.neolink_app.clases.database_state.minutosstate;
import com.example.neolink_app.clases.database_state.statePK;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

import org.w3c.dom.Text;

import java.util.Calendar;


public class datosgenerales extends Fragment {

    private String nombre;
    private TextView titulo;
    private Calendar ahora = Calendar.getInstance();
    private MasterDrawerViewModel archi;
    private CardView CVsensor1;
    private TextView titulosensor;
    private TextView humedaddelsuelo;
    private TextView temperaturadelsuelo;
    private TextView ConductividadEC;
    private CardView CVsensor2;
    private TextView titulosensor2;
    private TextView potencialmatricial;
    private TextView temperatura;
    private CardView CVsensor3;
    private TextView voltajesolar;
    private TextView temperaturainterna;
    private TextView temperaturavulvoseco;
    private TextView humedadrelativa;
    private TextView voltajebateria;
    private TextView presionbarometrica;
    private TextView altitud;



    public datosgenerales() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.nombre = datosgeneralesArgs.fromBundle(getArguments()).getNombreNL();
        }
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_datosgenerales, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        titulo = view.findViewById(R.id.tituloDG);
        CVsensor1 = view.findViewById(R.id.cardView1);
        titulosensor = view.findViewById(R.id.titulosensor1);
        humedaddelsuelo = view.findViewById(R.id.primerdato);
        temperaturadelsuelo = view.findViewById(R.id.segundodato);
        ConductividadEC = view.findViewById(R.id.tercerdato);
        CVsensor2 = view.findViewById(R.id.cardView2);
        titulosensor2 = view.findViewById(R.id.titulo2principal);
        potencialmatricial = view.findViewById(R.id.primer2dato);
        temperatura = view.findViewById(R.id.segundo2dato);
        CVsensor3 = view.findViewById(R.id.cardView3);
        voltajesolar = view.findViewById(R.id.cuarto3dato);
        temperaturainterna = view.findViewById(R.id.sexto3dato);
        temperaturavulvoseco = view.findViewById(R.id.tercer3dato);
        humedadrelativa = view.findViewById(R.id.segundo3dato);
        voltajebateria = view.findViewById(R.id.quinto3dato);
        presionbarometrica = view.findViewById(R.id.primer3dato);
        altitud = view.findViewById(R.id.septimo3dato);

        titulo.setText(nombre);
        startupstate();
        int hoyano = ahora.get(Calendar.YEAR)%100;
        int hoymes = ahora.get(Calendar.MONTH)+1;
        int hoydia = ahora.get(Calendar.DAY_OF_MONTH);
        archi.funciondedatosgeneralesg(nombre,hoyano,hoymes,hoydia).observe(getViewLifecycleOwner(), new Observer<HorasG>() {
            @Override
            public void onChanged(HorasG horasG) {
                if(horasG!=null){
                    if(horasG.dametamanoG()!=0){
                        arrangesensor1(horasG);
                    }
                }
            }
        });
        archi.funciondedatosgeneralesk(nombre,hoyano,hoymes,hoydia).observe(getViewLifecycleOwner(), new Observer<Horas>() {
            @Override
            public void onChanged(Horas horas) {
                if(horas!=null){
                    if(horas.dametamano()!=0){
                        arrangesensor2(horas);
                    }
                }
            }
        });
        archi.funciondedatosgeneralesstate(nombre,hoyano,hoymes,hoydia).observe(getViewLifecycleOwner(), new Observer<horasstate>() {
            @Override
            public void onChanged(horasstate horasstate) {
                if(horasstate!=null){
                    if(horasstate.dametamano()!=0){
                        arrangestate(horasstate);
                    }
                }
            }
        });
    }
    private void startupstate(){
        CVsensor1.setVisibility(View.GONE);
        CVsensor2.setVisibility(View.GONE);
        CVsensor3.setVisibility(View.GONE);
    }
    private void arrangesensor1(HorasG obj){
        dataPuertoG data = dameelultimodato(obj);
        CVsensor1.setVisibility(View.VISIBLE);
        String tit = "Sensor "+data.dameDepth();
        titulosensor.setText(tit);
        String humedad = (Math.floor(data.dameV1()*100)/100) + "raw";
        humedaddelsuelo.setText(humedad);
        String temperatura = (Math.floor(data.dameV2()*100)/100) + "°C";
        temperaturadelsuelo.setText(temperatura);
        String conductivida = (Math.floor(data.dameV3()*100)/100) + "uS/cm";
        ConductividadEC.setText(conductivida);
    }
    private dataPuertoG dameelultimodato(HorasG obj){
        MinutosG paquete = obj.dameminutos(obj.dametamanoG()-1);
        PuertoG puerto = paquete.damepaquete(paquete.dametamanoG()-1);
        return puerto.damedata(puerto.dametamanoG()-1);
    }
    private void arrangesensor2(Horas obj){
        dataPuerto data = dameultimodatoK(obj);
        CVsensor2.setVisibility(View.VISIBLE);
        String tit = "Sensor "+data.Depth;
        titulosensor2.setText(tit);
        String PM = (Math.floor(data.dameV1()*100)/100) + "kPa";
        potencialmatricial.setText(PM);
        String temp = (Math.floor(data.dameV2()*100)/100) + "°C";
        temperatura.setText(temp);
    }
    private dataPuerto dameultimodatoK(Horas obj){
        Minutos paquete = obj.dameminutos(obj.dametamano()-1);
        Puerto puerto = paquete.damepaquete(paquete.dametamano()-1);
        return puerto.damedata(puerto.dametamano()-1);
    }
    private void arrangestate(horasstate obj){
        statePK data = dameultimodatostate(obj);
        CVsensor3.setVisibility(View.VISIBLE);
        String PB = (Math.floor(data.giveBP()*100)/100)+"kPa";
        presionbarometrica.setText(PB);
        String HR = (Math.floor(data.giveRH()*100)/100)+"%";
        humedadrelativa.setText(HR);
        String TVS = (Math.floor(data.givedT()*100)/100)+"°C";
        temperaturavulvoseco.setText(TVS);
        String VS = (Math.floor(data.giveSV()*100)/100)+"V";
        voltajesolar.setText(VS);
        String Bat = (Math.floor(data.giveBV()*100)/100)+"V";
        voltajebateria.setText(Bat);
        String tempint = (Math.floor(data.giveiT()*100)/100)+"°C";
        temperaturainterna.setText(tempint);
        String alti = (Math.floor(data.giveAL()*100)/100)+"";
        altitud.setText(alti);
    }
    private statePK dameultimodatostate(horasstate obj){
        minutosstate paquete = obj.dameminutos(obj.dametamano()-1);
        return paquete.damepaquete(paquete.dametamano()-1);
    }
}
