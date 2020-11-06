package com.example.neolink_app.adaptadores;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.neolink_app.R;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Calendar;

public class MarkerLineChartDefault extends MarkerView {

    private TextView tvContent;
    private long startClickTime;
    private LinearLayout markerContainerView;
    private static final int MAX_CLICK_DURATION = 500;
    private MasterDrawerViewModel archi;
    private ArrayList<String> timelabels;
    private String neolink;
    private String sensor;
    private String var;
    public float drawingPosX;
    public float drawingPosY;

    public MarkerLineChartDefault(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.datasecundaria);
        markerContainerView = findViewById(R.id.lletiquetasecundaria);
    }
    public MarkerLineChartDefault(Context context, int layoutResource, ArrayList<String> timelabels, String neolink, String sensor, String var , MasterDrawerViewModel archi) {
        super(context, layoutResource);
        this.timelabels=timelabels;
        this.neolink = neolink;
        this.sensor = sensor;
        this.var=var;
        this.archi = archi;
        tvContent = findViewById(R.id.datasecundaria);
        markerContainerView = findViewById(R.id.lletiquetasecundaria);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        String texto = "" + String.valueOf(e.getY());
        tvContent.setText(texto);
        markerContainerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timelabels!=null){
                    String[] time =timelabels.get((int) e.getX()).split("\n");
                    //String a = neolink+"5HEISSEN5"+time[0]+"5ZEIT5"+time[1]+"5ZEIT5"+sensor+"5TYP5"+var;
                    archi.avizarquehayuncomentarionuevo(neolink+"5HEISSEN5"+time[0]+"5ZEIT5"+time[1]+"5ZEIT5"+sensor+"5TYP5"+var);
                }
            }
        });
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

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        super.draw(canvas, posX, posY);
        MPPointF offset = getOffsetForDrawingAtPoint(posX, posY);
        this.drawingPosX = posX + offset.x;
        this.drawingPosY = posY + offset.y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                startClickTime = Calendar.getInstance().getTimeInMillis();
                break;
            }
            case MotionEvent.ACTION_UP: {
                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if(clickDuration < MAX_CLICK_DURATION) {
                    markerContainerView.performClick();
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
