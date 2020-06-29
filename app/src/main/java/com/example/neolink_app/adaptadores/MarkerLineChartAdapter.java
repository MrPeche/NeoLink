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
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String texto = "";
        String label = orden.get(highlight.getDataSetIndex());
        switch (label){
            case "P1":
                texto = "Profundidad " + depth.getP1().get(dataset.getP1().indexOf(e));
                break;
            case "P2":
                texto = "Profundidad " + depth.getP2().get(dataset.getP2().indexOf(e));
                break;
            case "P3":
                texto = "Profundidad " + depth.getP3().get(dataset.getP3().indexOf(e));
                break;
        }
        tvContent.setText(texto);
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
