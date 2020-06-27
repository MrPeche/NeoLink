package com.example.neolink_app;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private LineChart grafico1;
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

    public static planografico newInstance(Horas paquete) {
        planografico fragment = new planografico();

        Bundle args = new Bundle();
        args.putParcelable("paquete",paquete);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.paquete = getArguments().getParcelable("paquete");
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
        grafico1 = view.findViewById(R.id.graficochart1);
        grafico1.setBackgroundColor(Color.TRANSPARENT);
        grafico1.setGridBackgroundColor(Color.BLACK);
        grafico1.setDrawGridBackground(true);
        grafico1.setDrawBorders(true);
        grafico1.getDescription().setEnabled(false);
        grafico1.setTouchEnabled(true);
        grafico1.setDragEnabled(true);
        grafico1.setScaleEnabled(true);
        Legend L = grafico1.getLegend();

        ArrayList<Entry> Yaxis = new ArrayList<>();
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
                Yaxis.add(new Entry(l,paquete.dameminutos(i).damepaquete(j).damedata(0).dameV1().floatValue()));
                Xlabels.add(label2);
                l++;
            }
        }
        Collections.sort(Yaxis, new EntryXComparator());
        LineDataSet set1 = new LineDataSet(Yaxis,"P1");
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
    }

}
