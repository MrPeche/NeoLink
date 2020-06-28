package com.example.neolink_app;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.neolink_app.clases.Horas;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link planografico#newInstance} factory method to
 * create an instance of this fragment.
 */
public class planografico extends Fragment {
    private TextView nombre;
    private String name;
    private LineChart grafico1;
    private LineChart grafico2;
    private Horas paquete;
    private int colopuerto = Color.argb(150,250,128,114);

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public planografico() {
        // Required empty public constructor
    }

    public static planografico newInstance(Horas paquete,String name) {
        planografico fragment = new planografico();

        Bundle args = new Bundle();
        args.putParcelable("paquete",paquete);
        args.putString("nombre",name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.paquete = getArguments().getParcelable("paquete");
            this.name = getArguments().getString("nombre");
        }
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
        propiedadesgraficoPM();
        propiedadesgraficoTem();

        ArrayList<Entry> YPM = new ArrayList<>();
        ArrayList<Entry> YTemp = new ArrayList<>();
        ArrayList<Double> DepthLabel = new ArrayList<>();
        final ArrayList<String> Xlabels = new ArrayList<>();
        String sp = ":";
        String label;
        String label2;
        float l = 0;
        for(int i = 0; i<paquete.dametamano();i++){
            label = paquete.damehora(i);
            for(int j = 0; j<paquete.dameminutos(i).dametamano();j++){
                label2 = label+sp+paquete.dameminutos(i).dameminuto(j);
                String nombre = paquete.dameminutos(i).damepaquete(j).damePuerto(0);
                YPM.add(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(0).dameV1().floatValue()));
                YTemp.add(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(0).dameV2().floatValue()));
                DepthLabel.add(paquete.dameminutos(i).damepaquete(j).damedata(0).dameDepth());
                Xlabels.add(label2);
                l++;
            }
        }
        Collections.sort(YPM, new EntryXComparator());
        LineDataSet set1 = new LineDataSet(YPM,"P1");
        set1.setColor(Color.argb(100,255,99,71));
        //set1.setHighLightColor(Color.argb(100,255,99,71));
        set1.setCircleColor(Color.argb(100,255,99,71));
        set1.setCircleRadius(1f);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.5f);
        set1.setDrawValues(false);
        set1.setLineWidth(2f);
        LineData data = new LineData(set1);
        grafico1.setData(data);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
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
        grafico1.invalidate();

        Collections.sort(YTemp, new EntryXComparator());
        LineDataSet set2 = new LineDataSet(YTemp,"P1");

        set2.setColor(Color.argb(100,255,99,71));
        //set2.setHighLightColor(Color.argb(100,255,99,71));
        set2.setCircleColor(Color.argb(100,255,99,71));
        set2.setCircleRadius(1f);
        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set2.setCubicIntensity(0.5f);
        set2.setDrawValues(false);
        LineData data2 = new LineData(set2);
        grafico2.setData(data);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        XAxis xaxis2= grafico2.getXAxis();
        xaxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        final ArrayList<String> xlab2 = Xlabels;
        ValueFormatter formatter2 = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xlab2.get((int) value);
            }
        };
        xaxis2.setValueFormatter(formatter);
        grafico2.invalidate();


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
        grafico1.setScaleEnabled(true);
        Legend L = grafico1.getLegend();
    }
    public void setdataPM(){

    }
    public void propiedadesgraficoTem(){
        grafico2.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        grafico2.setDrawGridBackground(false);
        grafico2.setDrawBorders(true);
        grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        grafico2.getDescription().setEnabled(false);
        grafico2.setTouchEnabled(true);
        grafico2.setDragEnabled(true);
        grafico2.setScaleEnabled(true);
        Legend L = grafico2.getLegend();
    }
}
