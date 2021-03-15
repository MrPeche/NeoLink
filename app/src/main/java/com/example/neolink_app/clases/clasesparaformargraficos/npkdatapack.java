package com.example.neolink_app.clases.clasesparaformargraficos;

import android.util.Pair;

import com.example.neolink_app.clases.DepthPackage;
import com.example.neolink_app.clases.paquetedatasetPuertos;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;

public class npkdatapack {
    DepthPackage depth;
    ArrayList<String> datalabelsaxisX;
    Pair<ArrayList<String>, LineData> nitrogeno;
    Pair<ArrayList<String>,LineData> nitrato;
    Pair<ArrayList<String>,LineData> fosforo;
    Pair<ArrayList<String>,LineData> fosfato;
    Pair<ArrayList<String>,LineData> potasio;
    paquetedatasetPuertos Raiznitrogeno;
    paquetedatasetPuertos Raiznitrato;
    paquetedatasetPuertos Raizfosforo;
    paquetedatasetPuertos Raizfosfato;
    paquetedatasetPuertos Raizpotasio;
    public npkdatapack(DepthPackage depth, ArrayList<String> datalabelsaxisX, Pair<ArrayList<String>, LineData> nitrogeno, Pair<ArrayList<String>,LineData> nitrato, Pair<ArrayList<String>,LineData> fosforo, Pair<ArrayList<String>,LineData> fosfato, Pair<ArrayList<String>,LineData> potasio, paquetedatasetPuertos Raiznitrogeno, paquetedatasetPuertos Raiznitrato, paquetedatasetPuertos Raizfosforo, paquetedatasetPuertos Raizfosfato, paquetedatasetPuertos Raizpotasio){
        this.depth=depth;
        this.datalabelsaxisX=datalabelsaxisX;
        this.nitrogeno=nitrogeno;
        this.nitrato=nitrato;
        this.fosforo=fosforo;
        this.fosfato=fosfato;
        this.potasio=potasio;
        this.Raiznitrogeno=Raiznitrogeno;
        this.Raiznitrato=Raiznitrato;
        this.Raizfosforo=Raizfosforo;
        this.Raizfosfato=Raizfosfato;
        this.Raizpotasio=Raizpotasio;
    }
    public DepthPackage sacareldepth(){return this.depth;}
    public ArrayList<String> sacarlabels(){return this.datalabelsaxisX;}
    public Pair<ArrayList<String>, LineData> sacarelnitrogeno(){return this.nitrogeno;}
    public Pair<ArrayList<String>, LineData> sacarlanitrato(){return this.nitrato;}
    public Pair<ArrayList<String>, LineData> sacarelfosforo(){return this.fosforo;}
    public Pair<ArrayList<String>, LineData> sacarlafosfato(){return this.fosfato;}
    public Pair<ArrayList<String>, LineData> sacarelpotasio(){return this.potasio;}
    public paquetedatasetPuertos sacarraiznitrogeno(){return this.Raiznitrogeno;}
    public paquetedatasetPuertos sacarraiznitrato(){return this.Raiznitrato;}
    public paquetedatasetPuertos sacarraizfosforo(){return this.Raizfosforo;}
    public paquetedatasetPuertos sacarraizfosfato(){return this.Raizfosfato;}
    public paquetedatasetPuertos sacarraizpotasio(){return this.Raizpotasio;}
}
