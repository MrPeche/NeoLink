package com.example.neolink_app.clases.clasesdecharts;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.neolink_app.adaptadores.MarkerLineChartAdapter;
import com.github.mikephil.charting.charts.LineChart;

public class chartlinealparadapter extends LineChart {
    public chartlinealparadapter(Context context) {
        super(context);
    }

    public chartlinealparadapter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public chartlinealparadapter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        boolean handled = true;
        // if there is no marker view or drawing marker is disabled
        if (isShowingMarker() && this.getMarker() instanceof MarkerLineChartAdapter){
            MarkerLineChartAdapter markerView = (MarkerLineChartAdapter) this.getMarker();
            Rect rect = new Rect((int)markerView.drawingPosX,(int)markerView.drawingPosY,(int)markerView.drawingPosX + markerView.getWidth(), (int)markerView.drawingPosY + markerView.getHeight());
            if (rect.contains((int) event.getX(),(int) event.getY())) {
                // touch on marker -> dispatch touch event in to marker
                markerView.dispatchTouchEvent(event);
            }else{
                handled = super.onTouchEvent(event);
            }
        }else{
            handled = super.onTouchEvent(event);
        }
        return handled;
    }

    private boolean isShowingMarker(){
        return mMarker != null && isDrawMarkersEnabled() && valuesToHighlight();
    }

}
