package com.example.neolink_app.adaptadores;

import android.content.Context;
import android.widget.TextView;

import com.example.neolink_app.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class MarkerLineChartDefault extends MarkerView {

    private TextView tvContent;
    private TextView tvContentT;

    public MarkerLineChartDefault(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.dataetiqueta);
        tvContentT = findViewById(R.id.datadata);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        String texto = "" + e.getY();
        tvContent.setText(texto);
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
