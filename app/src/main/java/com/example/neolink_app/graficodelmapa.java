package com.example.neolink_app;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.neolink_app.adaptadores.MarkerLineChartAdapter;
import com.example.neolink_app.adaptadores.MarkerLineChartDefault;
import com.example.neolink_app.adaptadores.graficolabelgenerator;
import com.example.neolink_app.adaptadores.viewpagergrafiquitosAdapter;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;


public class graficodelmapa extends Fragment {
    private String nombre;
    private TextView titulo;
    private CardView cvgrf1;
    private CardView cvgrf2;
    private CardView cvgrf3;
    private CardView cvgrhumedaddelsuelo;
    private CardView cvgrtemperaturadelsuelo;
    private CardView cvgrconductividadelectrica;
    private CardView cvgrHumedadrelativa;
    private CardView cvgrpresionbarometrica;
    private CardView cvgrtemperaturabulboseco;
    private Calendar ahora = Calendar.getInstance();
    private MasterDrawerViewModel archi;
    private LineChart grafico1;
    private LineChart grafico2;
    private LineChart grafico3;
    private LineChart graficopresionbarometrica;
    private LineChart graficohumedadrelativa;
    private LineChart graficotemperaturavulvoseco;
    private LineChart graficoMPhumedaddelsuelo;
    private LineChart graficoMPtemperaturadelsuelo;
    private LineChart graficoMPconductividadelectrica;
    private paquetedatasetPuertos YPM = new paquetedatasetPuertos();
    private paquetedatasetPuertos YTemp = new paquetedatasetPuertos();
    private DepthPackage DepthP = new DepthPackage();
    private final ArrayList<String> Xlabels = new ArrayList<>();
    private int alpha = 170;
    private int[] colores = {Color.argb(alpha,250,128,114),Color.argb(alpha,60,179,113),Color.argb(alpha,100,149,237),Color.argb(alpha,176,196,222)}; //salmon, medium sea green,corn flower blue, light steel blue https://www.rapidtables.com/web/color/RGB_Color.html
    private int MAX_DATAVISIBLE = 48;
    private float LINEWIDTH = 2.5f;

    public graficodelmapa() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.nombre = graficodelmapaArgs.fromBundle(getArguments()).getNombreNLG();
        }
        int hoyaño = ahora.get(Calendar.YEAR)%100;
        int hoymes = ahora.get(Calendar.MONTH)+1;
        int hoydia = ahora.get(Calendar.DAY_OF_MONTH);
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        String sensor = "k";
        archi.livelydatafull(nombre,hoyaño,hoymes,hoydia,sensor);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graficodelmapa, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        titulo = view.findViewById(R.id.tituloGMapa);
        titulo.setText(nombre);
        grafico1 = view.findViewById(R.id.graficoMP1);
        grafico2= view.findViewById(R.id.graficoMP2);
        grafico3= view.findViewById(R.id.graficoMPBateria);
        graficohumedadrelativa= view.findViewById(R.id.graficoMPHumedadRelativa);
        graficopresionbarometrica= view.findViewById(R.id.graficoMPPresionBarometrica);
        graficotemperaturavulvoseco= view.findViewById(R.id.graficoMPtemperaturavulvoseco);
        graficoMPhumedaddelsuelo = view.findViewById(R.id.graficoMPhumedaddelsuelo);
        graficoMPtemperaturadelsuelo = view.findViewById(R.id.graficoMPtemperaturadelsuelo);
        graficoMPconductividadelectrica = view.findViewById(R.id.graficoMPconductividadelectrica);
        cvgrf1 = view.findViewById(R.id.cardVMP1);
        cvgrf2 = view.findViewById(R.id.cardVMP2);
        cvgrf3 = view.findViewById(R.id.cardVMP3); //esta es energia por alguna razon que no quiero ver en este momento ORDENAR LUEGO
        cvgrhumedaddelsuelo = view.findViewById(R.id.cardMPhumedaddelsuelo);
        cvgrtemperaturadelsuelo = view.findViewById(R.id.cardMPtemperaturadelsuelo);
        cvgrconductividadelectrica = view.findViewById(R.id.cardMPconductividadelectrica);
        cvgrHumedadrelativa = view.findViewById(R.id.cardhumedadrelativa);
        cvgrpresionbarometrica = view.findViewById(R.id.cardpresionbarometrica);
        cvgrtemperaturabulboseco = view.findViewById(R.id.cardtemperaturavulvoseco);

        propiedadesgraficoPM();
        propiedadesgraficoTem();
        propiedadesgrafico3();
        propiedadesgraficohumedadrelativa();
        propiedadesgraficopresionbarometrica();
        propiedadesgraficotemperaturavulvoseco();
        propiedadesgraficohumedaddeldelsuelo();
        propiedadesgraficotemperaturadeldelsuelo();
        propiedadesgraficoconductividaddeldelsuelo();
        startposition();
        /*
        int hoyaño = ahora.get(Calendar.YEAR)%100;
        int hoymes = ahora.get(Calendar.MONTH)+1;
        int hoydia = ahora.get(Calendar.DAY_OF_MONTH);
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        String sensor = "k";
        ArrayList<String> nodaso = new ArrayList<>();
        nodaso.add(nombre);
        final ArrayList<String> nodito = nodaso;
        archi.getLivedaylydata(nombre,hoyaño,hoymes,hoydia,sensor);
         */
        archi.paquetesdedata.observe(getViewLifecycleOwner(), new Observer<Pair<Horas, horasstate>>() {
            @Override
            public void onChanged(Pair<Horas, horasstate> horashorasstatePair) {
                if(archi.paquetesdedata.isitready()){
                    cleanthisshit();
                    if(horashorasstatePair.first.dametamano()!=0) {
                        cvgrf1.setVisibility(View.VISIBLE);
                        cvgrf2.setVisibility(View.VISIBLE);
                        setdatagrafK(horashorasstatePair.first);
                        setdataPM(YPM, Xlabels, DepthP);
                        setdataTemp(YTemp, Xlabels, DepthP);
                    }
                    if(horashorasstatePair.second.dametamano()!=0){
                        cvgrf3.setVisibility(View.VISIBLE);
                        cvgrHumedadrelativa.setVisibility(View.VISIBLE);
                        cvgrpresionbarometrica.setVisibility(View.VISIBLE);
                        cvgrtemperaturabulboseco.setVisibility(View.VISIBLE);
                        setStatedata(horashorasstatePair.second);
                    }
                }
            }
        });
        if(archi.datahoyG==null){
            archi.retrivedatag(nombre);
        }
        if(!archi.datahoyG.hasActiveObservers()){
            archi.datahoyG.observe(getViewLifecycleOwner(), new Observer<HorasG>() {
                @Override
                public void onChanged(HorasG horasG) {
                    if(horasG!=null){
                        if(horasG.dametamanoG()!=0){
                            cleanG();
                            cvgrconductividadelectrica.setVisibility(View.VISIBLE);
                            cvgrhumedaddelsuelo.setVisibility(View.VISIBLE);
                            cvgrtemperaturadelsuelo.setVisibility(View.VISIBLE);
                            setdataG(horasG);
                        }
                    }
                }
            });
        }
        //if(!archi.datahoy.hasActiveObservers()){ }
        /*
        archi.datahoy.observe(getViewLifecycleOwner(), new Observer<Horas>(){
            @Override
            public void onChanged(Horas horas) {
                if(horas.dametamano()!=0) {
                    cvgrf1.setVisibility(View.VISIBLE);
                    cvgrf2.setVisibility(View.VISIBLE);
                    cleanthisshit();
                    setdatagrafK(horas);
                    setdataPM(YPM, Xlabels, DepthP);
                    setdataTemp(YTemp, Xlabels, DepthP);
                }
            }
        });
         */
    }
    private void startposition(){
        cvgrf1.setVisibility(View.GONE);
        cvgrf2.setVisibility(View.GONE);
        cvgrf3.setVisibility(View.GONE);
        cvgrHumedadrelativa.setVisibility(View.GONE);
        cvgrpresionbarometrica.setVisibility(View.GONE);
        cvgrtemperaturabulboseco.setVisibility(View.GONE);
        cvgrconductividadelectrica.setVisibility(View.GONE);
        cvgrhumedaddelsuelo.setVisibility(View.GONE);
        cvgrtemperaturadelsuelo.setVisibility(View.GONE);
    }
    private void cleanG(){
        graficoMPtemperaturadelsuelo.clear();
        graficoMPhumedaddelsuelo.clear();
        graficoMPconductividadelectrica.clear();
    }
    private void propiedadesgraficoPM(){
        grafico1.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        grafico1.setDrawGridBackground(false);
        grafico1.setDrawBorders(false);
        //grafico1.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        grafico1.getDescription().setEnabled(false);
        grafico1.setTouchEnabled(true);
        grafico1.setDragEnabled(true);
        grafico1.setScaleEnabled(true);
        grafico1.setScaleYEnabled(false);
        grafico1.setScaleXEnabled(true);
        Legend L = grafico1.getLegend();
    }
    private void propiedadesgraficoTem(){
        grafico2.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        grafico2.setDrawGridBackground(false);
        grafico2.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        grafico2.getDescription().setEnabled(false);
        grafico2.setTouchEnabled(true);
        grafico2.setDragEnabled(true);
        grafico2.setScaleEnabled(true);
        grafico2.setScaleYEnabled(false);
        grafico2.setScaleXEnabled(true);
        Legend L = grafico2.getLegend();
    }
    private void propiedadesgrafico3(){
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
    private void propiedadesgraficohumedadrelativa(){
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
    private void propiedadesgraficopresionbarometrica(){
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
    private void propiedadesgraficotemperaturavulvoseco(){
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
    private void propiedadesgraficohumedaddeldelsuelo(){
        graficoMPhumedaddelsuelo.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficoMPhumedaddelsuelo.setDrawGridBackground(false);
        graficoMPhumedaddelsuelo.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficoMPhumedaddelsuelo.getDescription().setEnabled(false);
        graficoMPhumedaddelsuelo.setTouchEnabled(true);
        graficoMPhumedaddelsuelo.setDragEnabled(true);
        graficoMPhumedaddelsuelo.setScaleYEnabled(false);
        graficoMPhumedaddelsuelo.setScaleXEnabled(true);
        Legend L = graficoMPhumedaddelsuelo.getLegend();
    }
    private void propiedadesgraficotemperaturadeldelsuelo(){
        graficoMPtemperaturadelsuelo.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficoMPtemperaturadelsuelo.setDrawGridBackground(false);
        graficoMPtemperaturadelsuelo.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficoMPtemperaturadelsuelo.getDescription().setEnabled(false);
        graficoMPtemperaturadelsuelo.setTouchEnabled(true);
        graficoMPtemperaturadelsuelo.setDragEnabled(true);
        graficoMPtemperaturadelsuelo.setScaleYEnabled(false);
        graficoMPtemperaturadelsuelo.setScaleXEnabled(true);
        Legend L = graficoMPtemperaturadelsuelo.getLegend();
    }
    private void propiedadesgraficoconductividaddeldelsuelo(){
        graficoMPconductividadelectrica.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficoMPconductividadelectrica.setDrawGridBackground(false);
        graficoMPconductividadelectrica.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficoMPconductividadelectrica.getDescription().setEnabled(false);
        graficoMPconductividadelectrica.setTouchEnabled(true);
        graficoMPconductividadelectrica.setDragEnabled(true);
        graficoMPconductividadelectrica.setScaleYEnabled(false);
        graficoMPconductividadelectrica.setScaleXEnabled(true);
        Legend L = graficoMPconductividadelectrica.getLegend();
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
    private void cleanthisshit(){
        Xlabels.clear();
        YPM.clean();
        YTemp.clean();
        DepthP.clean();
        grafico1.clear();
        grafico2.clear();
    }
    private void setdataPM(paquetedatasetPuertos YPM,ArrayList<String> Xlabels, DepthPackage DepthLabel){
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
        //grafico1.setVisibleYRangeMaximum(MAX_DATAVISIBLE, YAxis.AxisDependency.LEFT); // este era el problema pero talves sea necesario luego
        grafico1.setData(data);
        //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        XAxis xaxis = grafico1.getXAxis();
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setValueFormatter(new graficolabelgenerator(Xlabels));
        MarkerLineChartAdapter adapter = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,DepthLabel,YPM,orden);
        grafico1.setMarker(adapter);
        grafico1.invalidate();
        grafico1.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
    }
    private void setdataTemp(paquetedatasetPuertos YTemp, ArrayList<String> Xlabels, DepthPackage DepthLabel){
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
        grafico2.setVisibleYRangeMaximum(MAX_DATAVISIBLE, YAxis.AxisDependency.LEFT);
        grafico2.setData(data);

        //set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        XAxis xaxis2= grafico2.getXAxis();
        xaxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis2.setValueFormatter(new graficolabelgenerator(Xlabels));
        MarkerLineChartAdapter adapter = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,DepthLabel,YTemp,orden);
        grafico2.setMarker(adapter);
        grafico2.invalidate();
        grafico2.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
    }
    private LineDataSet CreaDataLine(ArrayList<Entry> data, String label,int color){
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

    public void onDestroy() {
        super.onDestroy();
        cleanthisshit();
        grafico1.clear();
        grafico2.clear();
        grafico3.clear();
    }
    private void setStatedata(horasstate state){
        final ArrayList<String> XlabelsSTATE = new ArrayList<>();
        ArrayList<Entry> linedata = new ArrayList<>();
        ArrayList<Entry> solar = new ArrayList<>();
        ArrayList<Entry> presionbaro = new ArrayList<>();
        ArrayList<Entry> humedadrelativa = new ArrayList<>();
        ArrayList<Entry> temperaturavulvoseco = new ArrayList<>();
        ArrayList<Entry> temperaturainterna = new ArrayList<>();
        String sp = ":";
        String label;
        String label2;
        float l = 0;
        for(int i = 0; i<state.dametamano();i++){
            label = state.damehora(i);
            for(int j = 0; j<state.dameminutos(i).dametamano();j++){
                label2 = label+sp+state.dameminutos(i).dameminutos(j);
                XlabelsSTATE.add(label2);
                linedata.add(new Entry(l,(float) state.dameminutos(i).damepaquete(j).BV));
                solar.add(new Entry(l,(float) state.dameminutos(i).damepaquete(j).SV));
                presionbaro.add(new Entry(l,(float) state.dameminutos(i).damepaquete(j).BP));
                humedadrelativa.add(new Entry(l,(float) state.dameminutos(i).damepaquete(j).RH));
                temperaturavulvoseco.add(new Entry(l,(float) state.dameminutos(i).damepaquete(j).dT));
                temperaturainterna.add(new Entry(l,(float) state.dameminutos(i).damepaquete(j).iT));
                l++;
            }
        }
        LineDataSet LDS = CreaDataLine(linedata,"Bateria",colores[0]);
        LineDataSet LDSV = CreaDataLine(solar,"Voltaje Solar", colores[1]);
        LDSV.setAxisDependency(YAxis.AxisDependency.LEFT); //
        LDS.setAxisDependency(YAxis.AxisDependency.RIGHT); //

        LineDataSet LDhumedadrelativa= CreaDataLine(humedadrelativa,"Humedad Relativa",colores[0]);
        LineDataSet LDpresionbarometrica= CreaDataLine(presionbaro,"Presion Barométrica",colores[0]);
        LineDataSet LDtemperaturavulvoseco= CreaDataLine(temperaturavulvoseco,"Temperatura de bulbo seco",colores[0]);
        LineDataSet LDtemperaturainterna = CreaDataLine(temperaturainterna, "Temperatura interna",colores[1]);
        LDtemperaturainterna.enableDashedLine(30,10,10);
        LDtemperaturainterna.setLineWidth(0.8f);

        adddatatostates(1,LDhumedadrelativa,XlabelsSTATE);
        adddatatostates(2,LDpresionbarometrica,XlabelsSTATE);
        adddatatostates2(1,LDS,LDSV,XlabelsSTATE);
        adddatatostates2(2,LDtemperaturavulvoseco,LDtemperaturainterna,XlabelsSTATE);
    }
    private void adddatatostates2(int tipo, LineDataSet A,LineDataSet B, ArrayList<String> C){
        LineData data = new LineData();
        data.addDataSet(A);
        data.addDataSet(B);
        if(tipo==1){
            grafico3.setData(data);
            YAxis yaxisl = grafico3.getAxisLeft(); //
            //yaxisl.setAxisMinimum(0f);
            //yaxisl.setAxisMaximum(8f); //7.5
            yaxisl.setTextColor(colores[1]);
            YAxis yaxisr = grafico3.getAxisRight();
            yaxisr.setAxisMinimum(3.5f);
            yaxisr.setAxisMaximum(4.3f); //4.5
            yaxisr.setTextColor(colores[0]);//
            XAxis xaxis3= grafico3.getXAxis();
            xaxis3.setPosition(XAxis.XAxisPosition.BOTTOM);
            xaxis3.setValueFormatter(new graficolabelgenerator(C));
            grafico3.invalidate();
            grafico3.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        } else if(tipo==2){
            graficotemperaturavulvoseco.setData(data);
            XAxis Xaxis = graficotemperaturavulvoseco.getXAxis();
            Xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            Xaxis.setValueFormatter(new graficolabelgenerator(C));
            graficotemperaturavulvoseco.invalidate();
            graficotemperaturavulvoseco.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        }
    }
    private void adddatatostates(int tipo,LineDataSet A, final ArrayList<String> B){
        LineData data = new LineData();
        data.addDataSet(A);
        if(tipo==1){
            graficohumedadrelativa.setData(data);
            XAxis Xaxis = graficohumedadrelativa.getXAxis();
            Xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            Xaxis.setValueFormatter(new graficolabelgenerator(B));
            MarkerLineChartDefault adapter = new MarkerLineChartDefault(getContext(),R.layout.item_dataetiqueta); //******************
            graficohumedadrelativa.setMarker(adapter); //********************
            graficohumedadrelativa.invalidate();
            graficohumedadrelativa.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        } else if(tipo==2){
            graficopresionbarometrica.setData(data);
            XAxis Xaxis = graficopresionbarometrica.getXAxis();
            Xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            Xaxis.setValueFormatter(new graficolabelgenerator(B));
            MarkerLineChartDefault adapter = new MarkerLineChartDefault(getContext(),R.layout.item_dataetiqueta); //******************
            graficopresionbarometrica.setMarker(adapter); //********************
            graficopresionbarometrica.invalidate();
            graficopresionbarometrica.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        }
    }
    private void setdataG(HorasG data){
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
        setsensorG(humS,1,XlabelsG,Depth);
        setsensorG(tempeS,2,XlabelsG,Depth);
        setsensorG(condE,3,XlabelsG,Depth);
    }
    private void setsensorG(paquetedatasetPuertos firedata, int tipo, ArrayList<String> XlabelsG,DepthPackage depth){
        ArrayList<String> orden = new ArrayList<>();
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
            graficoMPhumedaddelsuelo.setData(data);
            XAxis xaxis = graficoMPhumedaddelsuelo.getXAxis();
            xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xaxis.setValueFormatter(new graficolabelgenerator(XlabelsG));
            MarkerLineChartAdapter adapter = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,depth,firedata,orden);
            graficoMPhumedaddelsuelo.setMarker(adapter);
            graficoMPhumedaddelsuelo.invalidate();
            graficoMPhumedaddelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        } else if(tipo==2){
            graficoMPtemperaturadelsuelo.setData(data);
            XAxis xaxis = graficoMPtemperaturadelsuelo.getXAxis();
            xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xaxis.setValueFormatter(new graficolabelgenerator(XlabelsG));
            MarkerLineChartAdapter adapter = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,depth,firedata,orden);
            graficoMPtemperaturadelsuelo.setMarker(adapter);
            graficoMPtemperaturadelsuelo.invalidate();
            graficoMPtemperaturadelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        } else if(tipo ==3){
            graficoMPconductividadelectrica.setData(data);
            XAxis xaxis = graficoMPconductividadelectrica.getXAxis();
            xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xaxis.setValueFormatter(new graficolabelgenerator(XlabelsG));
            MarkerLineChartAdapter adapter = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,depth,firedata,orden);
            graficoMPconductividadelectrica.setMarker(adapter);
            graficoMPconductividadelectrica.invalidate();
            graficoMPconductividadelectrica.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        }
    }
}
