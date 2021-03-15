package com.example.neolink_app.clases.clasesparaformargraficos;

import android.util.Pair;

import com.example.neolink_app.clases.DepthPackage;
import com.example.neolink_app.clases.paquetedatasetPuertos;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;

public class phdatapack {
    DepthPackage depth;
    ArrayList<String> datalabelsaxisX;
    Pair<ArrayList<String>, LineData> ph;
    paquetedatasetPuertos Raizph;
    public phdatapack(DepthPackage depth,ArrayList<String> datalabelsaxisX,Pair<ArrayList<String>, LineData> ph, paquetedatasetPuertos Raizph){
        this.depth=depth;
        this.datalabelsaxisX=datalabelsaxisX;
        this.ph=ph;
        this.Raizph=Raizph;
    }
    public DepthPackage sacareldepth(){return this.depth;}
    public ArrayList<String> sacarlabels(){return this.datalabelsaxisX;}
    public paquetedatasetPuertos sacarraizph(){return this.Raizph;}
    public Pair<ArrayList<String>, LineData> sacarelph(){return this.ph;}
}
