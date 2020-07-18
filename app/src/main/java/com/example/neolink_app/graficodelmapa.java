package com.example.neolink_app;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.neolink_app.adaptadores.MarkerLineChartAdapter;
import com.example.neolink_app.adaptadores.viewpagergrafiquitosAdapter;
import com.example.neolink_app.clases.DepthPackage;
import com.example.neolink_app.clases.Horas;
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
    private Calendar ahora = Calendar.getInstance();
    private MasterDrawerViewModel archi;
    private LineChart grafico1;
    private LineChart grafico2;
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
        cvgrf1 = view.findViewById(R.id.cardVMP1);
        cvgrf2 = view.findViewById(R.id.cardVMP2);
        propiedadesgraficoPM();
        propiedadesgraficoTem();
        int hoyaño = ahora.get(Calendar.YEAR)%100;
        int hoymes = ahora.get(Calendar.MONTH)+1;
        int hoydia = ahora.get(Calendar.DAY_OF_MONTH);
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        String sensor = "k";
        ArrayList<String> nodaso = new ArrayList<>();
        nodaso.add(nombre);
        final ArrayList<String> nodito = nodaso;
        archi.getLivedaylydata(nombre,hoyaño,hoymes,hoydia,sensor);
        archi.datahoy.observe(getViewLifecycleOwner(), new Observer<Horas>(){
            @Override
            public void onChanged(Horas horas) {
                if(horas.dametamano()!=0) {
                    cvgrf1.setVisibility(View.VISIBLE);
                    cvgrf2.setVisibility(View.VISIBLE);
                    setdatagrafK(horas);
                    setdataPM(YPM, Xlabels, DepthP);
                    setdataTemp(YTemp, Xlabels, DepthP);
                }
            }
        });
        if(Xlabels.size()==0){
            cvgrf1.setVisibility(View.INVISIBLE);
            cvgrf2.setVisibility(View.INVISIBLE);
        }
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
        grafico2.setScaleYEnabled(false);
        grafico2.setScaleXEnabled(true);
        Legend L = grafico2.getLegend();
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
        grafico1.setVisibleYRangeMaximum(MAX_DATAVISIBLE, YAxis.AxisDependency.LEFT);
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
            LineDataSet set1 = CreaDataLine(YTemp.getP4(), "P3", colores[2]);
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
    }

}
