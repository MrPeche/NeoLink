package com.example.neolink_app;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.neolink_app.adaptadores.MarkerLineChartAdapter;
import com.example.neolink_app.clases.DepthPackage;
import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.clases.SensorG.HorasG;
import com.example.neolink_app.clases.database_state.horasstate;
import com.example.neolink_app.clases.paquetedatasetPuertos;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;


public class planografico extends Fragment {
    private TextView nombre;
    private String name;
    private LineChart grafico1;
    private LineChart grafico2;
    private LineChart grafico3;
    private LineChart graficopresionbarometrica;
    private LineChart graficohumedadrelativa;
    private LineChart graficotemperaturavulvoseco;
    private LineChart graficohumedaddelsuelo;
    private LineChart graficotemperaturadelsuelo;
    private LineChart graficoconductividaddelsuelo;
    private MasterDrawerViewModel archi;
    private paquetedatasetPuertos YPM2 = new paquetedatasetPuertos();
    private paquetedatasetPuertos YTemp2 = new paquetedatasetPuertos();
    private DepthPackage DepthP2 = new DepthPackage();
    private final ArrayList<String> Xlabels2 = new ArrayList<>();
    private Horas paquete;
    //private MarkerLineChartAdapter adapter;
    private int alpha = 170;
    private int[] colores = {Color.argb(alpha,250,128,114),Color.argb(alpha,60,179,113),Color.argb(alpha,100,149,237),Color.argb(alpha,176,196,222)}; //salmon, medium sea green,corn flower blue, light steel blue https://www.rapidtables.com/web/color/RGB_Color.html
    private int MAX_DATAVISIBLE = 48;
    private float LINEWIDTH = 2.5f;
    private Calendar ahora = Calendar.getInstance();

    private horasstate state;


    public planografico() {
        // Required empty public constructor
    }

    public static planografico newInstance(String name) {
        planografico fragment = new planografico();
        Bundle args = new Bundle();
        //args.putParcelable("paquete",paquete);
        args.putString("nombre",name);
        fragment.setArguments(args);
        return fragment;
    }

    public static planografico newInstance(Pair<Horas, horasstate> paquete, String name) {
        planografico fragment = new planografico();

        Bundle args = new Bundle();
        args.putParcelable("paquete",paquete.first);
        args.putParcelable("state",paquete.second);
        args.putString("nombre",name);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //this.paquete = getArguments().getParcelable("paquete");
            this.name = getArguments().getString("nombre");
            //this.state = getArguments().getParcelable("state");
        }
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planografico, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        nombre = view.findViewById(R.id.Nombrepager);
        nombre.setText(name);
        grafico1 = view.findViewById(R.id.graficochart1);
        grafico2= view.findViewById(R.id.graficochar2);
        grafico3= view.findViewById(R.id.graficoBat);
        graficohumedadrelativa= view.findViewById(R.id.graficoHumedadRelativa);
        graficopresionbarometrica= view.findViewById(R.id.graficoPresionBarometrica);
        graficotemperaturavulvoseco= view.findViewById(R.id.graficotemperaturavulvoseco);
        graficotemperaturadelsuelo= view.findViewById(R.id.graficotemperaturadelsuelo);
        graficohumedaddelsuelo = view.findViewById(R.id.graficohumedaddelsuelo);
        graficoconductividaddelsuelo = view.findViewById(R.id.graficoConductividadSuelo);

        propiedadesgraficoPM();
        propiedadesgraficoTem();
        propiedadesgrafico3();
        propiedadesgraficohumedadrelativa();
        propiedadesgraficopresionbarometrica();
        propiedadesgraficotemperaturavulvoseco();

        propiedadesgraficohumedaddeldelsuelo();
        propiedadesgraficotemperaturadeldelsuelo();
        propiedadesgraficoconductividaddeldelsuelo();

        paquetedatasetPuertos YPM = new paquetedatasetPuertos();
        paquetedatasetPuertos YTemp = new paquetedatasetPuertos();
        DepthPackage DepthP = new DepthPackage();
        //ArrayList<Entry> YPM = new ArrayList<>();
        //ArrayList<Entry> YTemp = new ArrayList<>();
        //ArrayList<Double> DepthLabel = new ArrayList<>();
        int hoyaño = ahora.get(Calendar.YEAR)%100;
        int hoymes = ahora.get(Calendar.MONTH)+1;
        int hoydia = ahora.get(Calendar.DAY_OF_MONTH);
        String sensor = "k";

        if(archi.datahoyG==null){
            archi.retrivedatag(name);
        }
        if(!archi.datahoyG.hasActiveObservers()){
            archi.datahoyG.observe(getViewLifecycleOwner(), new Observer<HorasG>() {
                @Override
                public void onChanged(HorasG horasG) {
                    if(horasG!=null){
                        cleanG();
                        setdataG(horasG);
                    }
                }
            });
        }

        if(!archi.datahoy.hasActiveObservers()){
            archi.livelydatafull(name,hoyaño,hoymes,hoydia,sensor);
            archi.paquetesdedata.observe(getViewLifecycleOwner(), new Observer<Pair<Horas, horasstate>>() {
                @Override
                public void onChanged(Pair<Horas, horasstate> horashorasstatePair) {
                    if(archi.paquetesdedata.isitready()){
                        cleanthisshit();
                        if(horashorasstatePair.first.dametamano()!=0) {
                            setdatagrafK(horashorasstatePair.first);
                            setdataPM(YPM2, Xlabels2, DepthP2);
                            setdataTemp(YTemp2, Xlabels2, DepthP2);
                        }
                        if(horashorasstatePair.second.dametamano()!=0){
                            setStatedata(horashorasstatePair.second);
                        }
                    }
                }
            });
        }



        /*
        final ArrayList<String> Xlabels = new ArrayList<>();
        String sp = ":";
        String label;
        String label2;
        float l = 0;
        for(int i = 0; i<paquete.dametamano();i++){
            label = paquete.damehora(i);
            for(int j = 0; j<paquete.dameminutos(i).dametamano();j++){
                label2 = label+sp+paquete.dameminutos(i).dameminuto(j);
                Xlabels.add(label2);
                for(int k = 0; k<paquete.dameminutos(i).damepaquete(j).dametamano();k++){
                    String nombrePuerto = paquete.dameminutos(i).damepaquete(j).damePuerto(k);
                    switch (nombrePuerto){
                        case "P1":
                            YPM.addP1(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV1().floatValue()));
                            YTemp.addP1(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV2().floatValue()));
                            DepthP.addP1(paquete.dameminutos(i).damepaquete(j).damedata(k).dameDepth());
                            break;
                        case "P2":
                            YPM.addP2(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV1().floatValue()));
                            YTemp.addP2(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV2().floatValue()));
                            DepthP.addP2(paquete.dameminutos(i).damepaquete(j).damedata(k).dameDepth());
                            break;
                        case "P3":
                            YPM.addP3(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV1().floatValue()));
                            YTemp.addP3(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV2().floatValue()));
                            DepthP.addP3(paquete.dameminutos(i).damepaquete(j).damedata(k).dameDepth());
                            break;
                        case "P4":
                            YPM.addP4(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV1().floatValue()));
                            YTemp.addP4(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV2().floatValue()));
                            DepthP.addP4(paquete.dameminutos(i).damepaquete(j).damedata(k).dameDepth());
                            break;
                    }
                }
                l++;
            }
        }
        setStatedata(state);
        setdataPM(YPM,Xlabels,DepthP);
        setdataTemp(YTemp,Xlabels,DepthP);
         */
    }
    private void cleanthisshit(){
        Xlabels2.clear();
        YPM2.clean();
        YTemp2.clean();
        DepthP2.clean();
        grafico1.clear();
        grafico2.clear();
        grafico3.clear();
    }
    private void cleanG(){
        graficotemperaturadelsuelo.clear();
        graficohumedaddelsuelo.clear();
        graficoconductividaddelsuelo.clear();
    }
    public void propiedadesgraficoPM(){
        grafico1.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        grafico1.setDrawGridBackground(false);
        grafico1.setDrawBorders(false);
        //grafico1.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        grafico1.getDescription().setEnabled(false);
        grafico1.setTouchEnabled(true);
        grafico1.setDragEnabled(true);
        grafico1.setScaleYEnabled(false);
        grafico1.setScaleXEnabled(true);
        Legend L = grafico1.getLegend();
    }
    private void setdatagrafK(Horas paquete){
        String sp = ":";
        String label;
        String label2;
        float l = 0;
        for(int i = 0; i<paquete.dametamano();i++){
            label = paquete.damehora(i);
            for(int j = 0; j<paquete.dameminutos(i).dametamano();j++){
                label2 = label+sp+paquete.dameminutos(i).dameminuto(j);
                Xlabels2.add(label2);
                for(int k = 0; k<paquete.dameminutos(i).damepaquete(j).dametamano();k++){
                    String nombrePuerto = paquete.dameminutos(i).damepaquete(j).damePuerto(k);
                    switch (nombrePuerto){
                        case "P1":
                            YPM2.addP1(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV1().floatValue()));
                            YTemp2.addP1(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV2().floatValue()));
                            DepthP2.addP1(paquete.dameminutos(i).damepaquete(j).damedata(k).dameDepth());
                            break;
                        case "P2":
                            YPM2.addP2(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV1().floatValue()));
                            YTemp2.addP2(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV2().floatValue()));
                            DepthP2.addP2(paquete.dameminutos(i).damepaquete(j).damedata(k).dameDepth());
                            break;
                        case "P3":
                            YPM2.addP3(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV1().floatValue()));
                            YTemp2.addP3(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV2().floatValue()));
                            DepthP2.addP3(paquete.dameminutos(i).damepaquete(j).damedata(k).dameDepth());
                            break;
                        case "P4":
                            YPM2.addP4(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV1().floatValue()));
                            YTemp2.addP4(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(k).dameV2().floatValue()));
                            DepthP2.addP4(paquete.dameminutos(i).damepaquete(j).damedata(k).dameDepth());
                            break;
                    }
                }
                /*
                String nombre = paquete.dameminutos(i).damepaquete(j).damePuerto(0);
                YPM.add(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(0).dameV1().floatValue()));
                YTemp.add(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(0).dameV2().floatValue()));
                DepthLabel.add(paquete.dameminutos(i).damepaquete(j).damedata(0).dameDepth());
                Xlabels.add(label2);
                */
                l++;
            }
        }
    }

    public void setdataPM(paquetedatasetPuertos YPM,ArrayList<String> Xlabels, DepthPackage DepthLabel){
        //Collections.sort(YPM, new EntryXComparator());
        //LineDataSet set1 = new LineDataSet(YPM.getP1(),"P1");
        ArrayList<String> orden = new ArrayList<>();
        LineData data = new LineData();
        if(YPM.getP1().size()!=0) {
            LineDataSet set1 = CreaDataLine(YPM.getP1(), "P1", colores[0]);
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(set1);
            orden.add("P1");
        }
        if(YPM.getP2().size()!=0) {
            LineDataSet set1 = CreaDataLine(YPM.getP2(), "P2", colores[1]);
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(set1);
            orden.add("P2");
        }
        if(YPM.getP3().size()!=0) {
            LineDataSet set1 = CreaDataLine(YPM.getP3(), "P3", colores[2]);
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(set1);
            orden.add("P3");
        }
        if(YPM.getP4().size()!=0) {
            LineDataSet set1 = CreaDataLine(YPM.getP4(), "P4", colores[3]);
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(set1);
            orden.add("P4");
        }
        //grafico1.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        //grafico1.setMaxVisibleValueCount(MAX_DATAVISIBLE);
        //grafico1.setVisibleYRangeMaximum(MAX_DATAVISIBLE, YAxis.AxisDependency.LEFT);
        grafico1.setData(data);
        //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        XAxis xaxis = grafico1.getXAxis();
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        final ArrayList<String> xlab = Xlabels;
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xlab.get((int) value);
            }
        };
        xaxis.setValueFormatter(formatter);
        MarkerLineChartAdapter adapter = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,DepthLabel,YPM,orden);
        grafico1.setMarker(adapter);
        grafico1.invalidate();
        grafico1.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
    }
    public void propiedadesgraficoTem(){
        grafico2.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        grafico2.setDrawGridBackground(false);
        grafico2.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        grafico2.getDescription().setEnabled(false);
        grafico2.setTouchEnabled(true);
        grafico2.setDragEnabled(true);
        grafico2.setScaleYEnabled(false);
        grafico2.setScaleXEnabled(true);
        Legend L = grafico2.getLegend();
    }

    public void setdataTemp(paquetedatasetPuertos YTemp,ArrayList<String> Xlabels,DepthPackage DepthLabel){
        //Collections.sort(YTemp, new EntryXComparator());
        //LineDataSet set2 = new LineDataSet(YTemp,"P1");
        ArrayList<String> orden = new ArrayList<>();
        LineData data = new LineData();
        if(YTemp.getP1().size()!=0) {
            LineDataSet set1 = CreaDataLine(YTemp.getP1(), "P1", colores[0]);
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(set1);
            orden.add("P1");
        }
        if(YTemp.getP2().size()!=0) {
            LineDataSet set1 = CreaDataLine(YTemp.getP2(), "P2", colores[1]);
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(set1);
            orden.add("P2");
        }
        if(YTemp.getP3().size()!=0) {
            LineDataSet set1 = CreaDataLine(YTemp.getP3(), "P3", colores[2]);
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(set1);
            orden.add("P3");
        }
        if(YTemp.getP4().size()!=0) {
            LineDataSet set1 = CreaDataLine(YTemp.getP4(), "P4", colores[3]);
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(set1);
            orden.add("P4");
        }
        //grafico2.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        //grafico2.setMaxVisibleValueCount(MAX_DATAVISIBLE);
        //grafico2.setVisibleYRangeMaximum(MAX_DATAVISIBLE, YAxis.AxisDependency.LEFT);
        grafico2.setData(data);

        //set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        XAxis xaxis2= grafico2.getXAxis();
        xaxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        final ArrayList<String> xlab2 = Xlabels;
        ValueFormatter formatter2 = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xlab2.get((int) value);
            }
        };
        xaxis2.setValueFormatter(formatter2);
        MarkerLineChartAdapter adapter = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,DepthLabel,YTemp,orden);
        grafico2.setMarker(adapter);
        grafico2.invalidate();
        grafico2.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
    }
    public LineDataSet CreaDataLine(ArrayList<Entry> data, String label,int color){
        LineDataSet pset = new LineDataSet(data,label);
        //set1.setHighLightColor(Color.argb(100,255,99,71));
        pset.setColor(color);
        pset.setDrawCircles(false);
        /*
        pset.setCircleColor(color);
        pset.setCircleRadius(5f);
        pset.setCircleHoleColor(Color.WHITE);
        */
        pset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        pset.setCubicIntensity(0.2f);
        pset.setDrawValues(false);
        pset.setLineWidth(LINEWIDTH);
        pset.setDrawHorizontalHighlightIndicator(false);
        pset.setDrawVerticalHighlightIndicator(false);
        return pset;
    }
    public void propiedadesgrafico3(){
        grafico3.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        grafico3.setDrawGridBackground(false);
        grafico3.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        grafico3.getDescription().setEnabled(false);
        grafico3.setTouchEnabled(true);
        grafico3.setDragEnabled(true);
        grafico3.setScaleYEnabled(false);
        grafico3.setScaleXEnabled(true);
        Legend L = grafico3.getLegend();
    }
    public void propiedadesgraficohumedadrelativa(){
        graficohumedadrelativa.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficohumedadrelativa.setDrawGridBackground(false);
        graficohumedadrelativa.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficohumedadrelativa.getDescription().setEnabled(false);
        graficohumedadrelativa.setTouchEnabled(true);
        graficohumedadrelativa.setDragEnabled(true);
        graficohumedadrelativa.setScaleYEnabled(false);
        graficohumedadrelativa.setScaleXEnabled(true);
        Legend L = graficohumedadrelativa.getLegend();
    }
    public void propiedadesgraficopresionbarometrica(){
        graficopresionbarometrica.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficopresionbarometrica.setDrawGridBackground(false);
        graficopresionbarometrica.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficopresionbarometrica.getDescription().setEnabled(false);
        graficopresionbarometrica.setTouchEnabled(true);
        graficopresionbarometrica.setDragEnabled(true);
        graficopresionbarometrica.setScaleYEnabled(false);
        graficopresionbarometrica.setScaleXEnabled(true);
        Legend L = graficopresionbarometrica.getLegend();
    }
    public void propiedadesgraficotemperaturavulvoseco(){
        graficotemperaturavulvoseco.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficotemperaturavulvoseco.setDrawGridBackground(false);
        graficotemperaturavulvoseco.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficotemperaturavulvoseco.getDescription().setEnabled(false);
        graficotemperaturavulvoseco.setTouchEnabled(true);
        graficotemperaturavulvoseco.setDragEnabled(true);
        graficotemperaturavulvoseco.setScaleYEnabled(false);
        graficotemperaturavulvoseco.setScaleXEnabled(true);
        Legend L = graficotemperaturavulvoseco.getLegend();
    }
    public void propiedadesgraficohumedaddeldelsuelo(){
        graficohumedaddelsuelo.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficohumedaddelsuelo.setDrawGridBackground(false);
        graficohumedaddelsuelo.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficohumedaddelsuelo.getDescription().setEnabled(false);
        graficohumedaddelsuelo.setTouchEnabled(true);
        graficohumedaddelsuelo.setDragEnabled(true);
        graficohumedaddelsuelo.setScaleYEnabled(false);
        graficohumedaddelsuelo.setScaleXEnabled(true);
        Legend L = graficohumedaddelsuelo.getLegend();
    }
    public void propiedadesgraficotemperaturadeldelsuelo(){
        graficotemperaturadelsuelo.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficotemperaturadelsuelo.setDrawGridBackground(false);
        graficotemperaturadelsuelo.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficotemperaturadelsuelo.getDescription().setEnabled(false);
        graficotemperaturadelsuelo.setTouchEnabled(true);
        graficotemperaturadelsuelo.setDragEnabled(true);
        graficotemperaturadelsuelo.setScaleYEnabled(false);
        graficotemperaturadelsuelo.setScaleXEnabled(true);
        Legend L = graficotemperaturadelsuelo.getLegend();
    }
    public void propiedadesgraficoconductividaddeldelsuelo(){
        graficoconductividaddelsuelo.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficoconductividaddelsuelo.setDrawGridBackground(false);
        graficoconductividaddelsuelo.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficoconductividaddelsuelo.getDescription().setEnabled(false);
        graficoconductividaddelsuelo.setTouchEnabled(true);
        graficoconductividaddelsuelo.setDragEnabled(true);
        graficoconductividaddelsuelo.setScaleYEnabled(false);
        graficoconductividaddelsuelo.setScaleXEnabled(true);
        Legend L = graficoconductividaddelsuelo.getLegend();
    }
    public void setStatedata(horasstate state){
        final ArrayList<String> XlabelsSTATE = new ArrayList<>();
        ArrayList<Entry> bateria = new ArrayList<>();
        ArrayList<Entry> solar = new ArrayList<>();
        ArrayList<Entry> presionbaro = new ArrayList<>();
        ArrayList<Entry> humedadrelativa = new ArrayList<>();
        ArrayList<Entry> temperaturavulvoseco = new ArrayList<>();
        String sp = ":";
        String label;
        String label2;
        float l = 0;
        for(int i = 0; i<state.dametamano();i++){
            label = state.damehora(i);
            for(int j = 0; j<state.dameminutos(i).dametamano();j++){
                label2 = label+sp+state.dameminutos(i).dameminutos(j);
                XlabelsSTATE.add(label2);
                bateria.add(new Entry(l,(float) state.dameminutos(i).damepaquete(j).BV));
                solar.add(new Entry(l,(float) state.dameminutos(i).damepaquete(j).SV));
                presionbaro.add(new Entry(l,(float) state.dameminutos(i).damepaquete(j).BP));
                humedadrelativa.add(new Entry(l,(float) state.dameminutos(i).damepaquete(j).RH));
                temperaturavulvoseco.add(new Entry(l,(float) state.dameminutos(i).damepaquete(j).dT));
                l++;
            }
        }
        LineDataSet LDS = CreaDataLine(bateria,"Bateria",colores[0]);
        LineDataSet LDSV = CreaDataLine(solar,"Voltaje Solar", colores[1]);
        LineData data = new LineData();
        data.addDataSet(LDS);
        data.addDataSet(LDSV);
        grafico3.setData(data);
        LineDataSet LDhumedadrelativa= CreaDataLine(humedadrelativa,"Humedad Relativa",colores[0]);
        LineDataSet LDpresionbarometrica= CreaDataLine(presionbaro,"Presion Barométrica",colores[0]);
        LineDataSet LDtemperaturavulvoseco= CreaDataLine(temperaturavulvoseco,"Temperatura de bulbo seco",colores[0]);
        XAxis xaxis3= grafico3.getXAxis();
        xaxis3.setPosition(XAxis.XAxisPosition.BOTTOM);
        ValueFormatter formatter3 = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return XlabelsSTATE.get((int) value);
            }
        };
        xaxis3.setValueFormatter(formatter3);
        grafico3.invalidate();
        grafico3.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        adddatatostates(1,LDhumedadrelativa,XlabelsSTATE);
        adddatatostates(2,LDpresionbarometrica,XlabelsSTATE);
        adddatatostates(3,LDtemperaturavulvoseco,XlabelsSTATE);

    }
    private void adddatatostates(int tipo,LineDataSet A, final ArrayList<String> B){
        LineData data = new LineData();
        data.addDataSet(A);
        if(tipo==1){
            graficohumedadrelativa.setData(data);
            XAxis Xaxis = graficohumedadrelativa.getXAxis();
            Xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            ValueFormatter formatter = new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    return B.get((int) value);
                }
            };
            Xaxis.setValueFormatter(formatter);
            graficohumedadrelativa.invalidate();
            graficohumedadrelativa.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        } else if(tipo==2){
            graficopresionbarometrica.setData(data);
            XAxis Xaxis = graficopresionbarometrica.getXAxis();
            Xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            ValueFormatter formatter = new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    return B.get((int) value);
                }
            };
            Xaxis.setValueFormatter(formatter);
            graficopresionbarometrica.invalidate();
            graficopresionbarometrica.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        } else if(tipo==3){
            graficotemperaturavulvoseco.setData(data);
            XAxis Xaxis = graficotemperaturavulvoseco.getXAxis();
            Xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            ValueFormatter formatter = new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    return B.get((int) value);
                }
            };
            Xaxis.setValueFormatter(formatter);
            graficotemperaturavulvoseco.invalidate();
            graficotemperaturavulvoseco.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        }

    }
    public void setdataG(HorasG data){
        paquetedatasetPuertos humS = new paquetedatasetPuertos();
        paquetedatasetPuertos tempeS = new paquetedatasetPuertos();
        paquetedatasetPuertos condE = new paquetedatasetPuertos();
        DepthPackage Depth = new DepthPackage();
        String sp = ":";
        String label;
        String label2;
        ArrayList<String> XlabelsG = new ArrayList<>();
        float l = 0;
        for(int i = 0; i<data.dametamanoG();i++){
            label = data.damehora(i);
            for(int j = 0; j<data.dameminutos(i).dametamanoG();j++){
                label2 = label+sp+data.dameminutos(i).dameminuto(j);
                XlabelsG.add(label2);
                for(int k = 0; k<data.dameminutos(i).damepaquete(j).dametamanoG();k++){
                    String nombrePuerto = data.dameminutos(i).damepaquete(j).damePuerto(k);
                    switch (nombrePuerto){
                        case "P1":
                            humS.addP1(new Entry(l,data.dameminutos(i).damepaquete(j).damedata(k).dameV1().floatValue()));
                            tempeS.addP1(new Entry(l,data.dameminutos(i).damepaquete(j).damedata(k).dameV2().floatValue()));
                            condE.addP1(new Entry(l,data.dameminutos(i).damepaquete(j).damedata(k).dameV3().floatValue()));
                            Depth.addP1(data.dameminutos(i).damepaquete(j).damedata(k).dameDepth());
                            break;
                        case "P2":
                            humS.addP2(new Entry(l,data.dameminutos(i).damepaquete(j).damedata(k).dameV1().floatValue()));
                            tempeS.addP2(new Entry(l,data.dameminutos(i).damepaquete(j).damedata(k).dameV2().floatValue()));
                            condE.addP2(new Entry(l,data.dameminutos(i).damepaquete(j).damedata(k).dameV3().floatValue()));
                            Depth.addP2(data.dameminutos(i).damepaquete(j).damedata(k).dameDepth());
                            break;
                        case "P3":
                            humS.addP3(new Entry(l,data.dameminutos(i).damepaquete(j).damedata(k).dameV1().floatValue()));
                            tempeS.addP3(new Entry(l,data.dameminutos(i).damepaquete(j).damedata(k).dameV2().floatValue()));
                            condE.addP3(new Entry(l,data.dameminutos(i).damepaquete(j).damedata(k).dameV3().floatValue()));
                            Depth.addP3(data.dameminutos(i).damepaquete(j).damedata(k).dameDepth());
                            break;
                        case "P4":
                            humS.addP4(new Entry(l,data.dameminutos(i).damepaquete(j).damedata(k).dameV1().floatValue()));
                            tempeS.addP4(new Entry(l,data.dameminutos(i).damepaquete(j).damedata(k).dameV2().floatValue()));
                            condE.addP4(new Entry(l,data.dameminutos(i).damepaquete(j).damedata(k).dameV3().floatValue()));
                            Depth.addP4(data.dameminutos(i).damepaquete(j).damedata(k).dameDepth());
                            break;
                    }
                }
                l++;
            }
        }
        final ArrayList<String> labels = XlabelsG;
        setsensorG(humS,1,labels,Depth);
        setsensorG(tempeS,2,labels,Depth);
        setsensorG(condE,3,labels,Depth);
    }
    private void setsensorG(paquetedatasetPuertos firedata, int tipo, final ArrayList<String> XlabelsG,DepthPackage depth){
        ArrayList<String> orden = new ArrayList<>();
        final ArrayList<String> labelinuse = XlabelsG;
        LineData data = new LineData();
        if(firedata.getP1().size()!=0) {
            LineDataSet set1 = CreaDataLine(firedata.getP1(), "P1", colores[0]);
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(set1);
            orden.add("P1");
        }
        if(firedata.getP2().size()!=0) {
            LineDataSet set1 = CreaDataLine(firedata.getP2(), "P2", colores[1]);
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(set1);
            orden.add("P2");
        }
        if(firedata.getP3().size()!=0) {
            LineDataSet set1 = CreaDataLine(firedata.getP3(), "P3", colores[2]);
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(set1);
            orden.add("P3");
        }
        if(firedata.getP4().size()!=0) {
            LineDataSet set1 = CreaDataLine(firedata.getP4(), "P4", colores[3]);
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(set1);
            orden.add("P4");
        }
        if(tipo == 1){
            graficohumedaddelsuelo.setData(data);
            XAxis xaxis = graficohumedaddelsuelo.getXAxis();
            xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            ValueFormatter formatter = new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    try {
                        int index = (int) value;
                        return labelinuse.get(index);
                    } catch (Exception e) {
                        return "";
                    }
                }
            };
            xaxis.setValueFormatter(formatter);
            MarkerLineChartAdapter adapter = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,depth,firedata,orden);
            graficohumedaddelsuelo.invalidate();
            graficohumedaddelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        } else if(tipo==2){
            graficotemperaturadelsuelo.setData(data);
            XAxis xaxis = graficotemperaturadelsuelo.getXAxis();
            xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            ValueFormatter formatter = new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    try {
                        int index = (int) value;
                        return labelinuse.get(index);
                    } catch (Exception e) {
                        return "";
                    }
                }
            };
            xaxis.setValueFormatter(formatter);
            MarkerLineChartAdapter adapter = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,depth,firedata,orden);
            graficotemperaturadelsuelo.invalidate();
            graficotemperaturadelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        } else if(tipo ==3){
            graficoconductividaddelsuelo.setData(data);
            XAxis xaxis = graficoconductividaddelsuelo.getXAxis();
            xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            ValueFormatter formatter = new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    try {
                        int index = (int) value;
                        return labelinuse.get(index);
                    } catch (Exception e) {
                        return "";
                    }
                }
            };
            xaxis.setValueFormatter(formatter);
            MarkerLineChartAdapter adapter = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,depth,firedata,orden);
            graficoconductividaddelsuelo.invalidate();
            graficoconductividaddelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        }
    }
}
