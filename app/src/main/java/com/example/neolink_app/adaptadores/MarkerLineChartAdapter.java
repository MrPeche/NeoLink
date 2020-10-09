package com.example.neolink_app.adaptadores;

import android.content.Context;
import android.widget.TextView;

import com.example.neolink_app.clases.DepthPackage;
import com.example.neolink_app.clases.paquetedatasetPuertos;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.example.neolink_app.R;

import java.util.ArrayList;

public class MarkerLineChartAdapter extends MarkerView {
    private TextView tvContent;
    private TextView tvContentT;
    private DepthPackage depth;
    private paquetedatasetPuertos dataset;
    private ArrayList<String> orden;
    public MarkerLineChartAdapter(Context context, int layoutResource, DepthPackage depth, paquetedatasetPuertos dataset, ArrayList<String> orden) {
        super(context, layoutResource);
        this.depth = depth;
        this.dataset = dataset;
        this.orden = orden;
        // find your layout components
        tvContent = findViewById(R.id.dataetiqueta);
        tvContentT = findViewById(R.id.datadata);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String texto2 = "";
        String texto1 = "";
        String texto3 = "";
        String label = orden.get(highlight.getDataSetIndex());
        switch (label){
            case "P1":
                texto1 = "Profundidad " + depth.getP1().get(dataset.getP1().indexOf(e));
                texto3 = "Puerto 1";
                break;
            case "P2":
                texto1 = "Profundidad " + depth.getP2().get(dataset.getP2().indexOf(e));
                texto3 = "Puerto 2";
                break;
            case "P3":
                texto1 = "Profundidad " + depth.getP3().get(dataset.getP3().indexOf(e));
                texto3 = "Puerto 3";
                break;
            case "P4":
                texto1 = "Profundidad " + depth.getP4().get(dataset.getP3().indexOf(e));
                texto3 = "Puerto 4";
                break;

        }
        texto2 = texto3 + ": "+ String.valueOf(e.getY());
        tvContentT.setText(texto2);
        tvContent.setText(texto1);
        //tvContent.setText("" + e.getY());
        // this will perform necessary layouting
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }
}
