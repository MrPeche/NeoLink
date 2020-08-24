package com.example.neolink_app.adaptadores;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class graficolabelgenerator extends ValueFormatter {
    private ArrayList<String> label = new ArrayList<>();

    public graficolabelgenerator(ArrayList<String> label){
        this.label = label;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int v = (int) value;
        if(v <= label.size()){
            return label.get(v);
        } else return "";

    }
}
