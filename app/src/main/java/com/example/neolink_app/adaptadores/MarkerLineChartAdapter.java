package com.example.neolink_app.adaptadores;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.example.neolink_app.R;

import java.util.ArrayList;

public class MarkerLineChartAdapter extends MarkerView {
    private TextView tvContent;
    private ArrayList<Double> depth;
    private ArrayList<Entry> dataset;
    public MarkerLineChartAdapter(Context context, int layoutResource, ArrayList<Double> depth,ArrayList<Entry> dataset) {
        super(context, layoutResource);
        this.depth = depth;
        this.dataset = dataset;
        // find your layout components
        tvContent = findViewById(R.id.dataetiqueta);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int posicion = dataset.indexOf(e);
        tvContent.setText("Profundidad"+depth.get(dataset.indexOf(e)));

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