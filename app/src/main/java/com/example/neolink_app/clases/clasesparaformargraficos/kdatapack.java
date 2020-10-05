package com.example.neolink_app.clases.clasesparaformargraficos;

import android.util.Pair;

import com.example.neolink_app.clases.DepthPackage;
import com.example.neolink_app.clases.paquetedatasetPuertos;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;

public class kdatapack {
    DepthPackage depth;
    ArrayList<String> datalabelsaxisX;
    Pair<ArrayList<String>, LineData> PM;
    Pair<ArrayList<String>,LineData> temp;
    Pair<paquetedatasetPuertos,paquetedatasetPuertos> Raiz;
    public kdatapack(DepthPackage depth, ArrayList<String> datalabelsaxisX, Pair<ArrayList<String>, LineData> PM, Pair<ArrayList<String>,LineData> temp,Pair<paquetedatasetPuertos,paquetedatasetPuertos> Raiz){
        this.depth = depth;
        this.datalabelsaxisX = datalabelsaxisX;
        this.PM = PM;
        this.temp = temp;
        this.Raiz = Raiz;
    }
    public DepthPackage sacareldepth(){ return this.depth;}
    public ArrayList<String> sacarloslabels(){ return this.datalabelsaxisX;}
    public Pair<ArrayList<String>, LineData> sacarelPM(){ return this.PM;}
    public Pair<ArrayList<String>, LineData> sacareltemp(){ return this.temp;}
    public Pair<paquetedatasetPuertos,paquetedatasetPuertos> sacarlasraizes(){ return this.Raiz;}
}
