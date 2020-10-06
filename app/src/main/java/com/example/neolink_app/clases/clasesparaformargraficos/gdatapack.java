package com.example.neolink_app.clases.clasesparaformargraficos;

import android.util.Pair;

import com.example.neolink_app.clases.DepthPackage;
import com.example.neolink_app.clases.paquetedatasetPuertos;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;

public class gdatapack {
    DepthPackage depth;
    ArrayList<String> datalabelsaxisX;
    Pair<ArrayList<String>, LineData> humedad;
    Pair<ArrayList<String>,LineData> temperatura;
    Pair<ArrayList<String>,LineData> conductividad;
    paquetedatasetPuertos Raizhumedad;
    paquetedatasetPuertos Raiztemperatura;
    paquetedatasetPuertos Raizconductividad;

    public gdatapack(DepthPackage depth, ArrayList<String> datalabelsaxisX, Pair<ArrayList<String>,LineData> humedad, Pair<ArrayList<String>,LineData> temperatura, Pair<ArrayList<String>,LineData> conductividad, paquetedatasetPuertos Raizhumedad, paquetedatasetPuertos Raiztemperatura, paquetedatasetPuertos Raizconductividad){
        this.depth = depth;
        this.datalabelsaxisX = datalabelsaxisX;
        this.humedad = humedad;
        this.temperatura = temperatura;
        this.conductividad = conductividad;
        this.Raizhumedad = Raizhumedad;
        this.Raiztemperatura = Raiztemperatura;
        this.Raizconductividad = Raizconductividad;
    }

    public DepthPackage sacareldepth(){return this.depth;}
    public ArrayList<String> sacarlabels(){return this.datalabelsaxisX;}
    public Pair<ArrayList<String>, LineData> sacarlahumedad(){return this.humedad;}
    public Pair<ArrayList<String>, LineData> sacarlatemperatura(){return this.temperatura;}
    public Pair<ArrayList<String>, LineData> sacarlaconductividad(){return this.conductividad;}
    public paquetedatasetPuertos sacarraizhumedad(){return this.Raizhumedad;}
    public paquetedatasetPuertos sacarraiztemperatura(){return this.Raiztemperatura;}
    public paquetedatasetPuertos sacarraizconductividad(){return this.Raizconductividad;}
}
