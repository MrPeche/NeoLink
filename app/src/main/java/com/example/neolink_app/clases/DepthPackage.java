package com.example.neolink_app.clases;

import java.util.ArrayList;

public class DepthPackage {
    ArrayList<Double> P1;
    ArrayList<Double> P2;
    ArrayList<Double> P3;
    public DepthPackage(){
        this.P1 = new ArrayList<>();
        this.P2 = new ArrayList<>();
        this.P3 = new ArrayList<>();
    }
    public void addP1(Double data){P1.add(data);}
    public void addP2(Double data){P2.add(data);}
    public void addP3(Double data){P3.add(data);}
    public ArrayList<Double> getP1(){ return P1;}
    public ArrayList<Double> getP2(){ return P2;}
    public ArrayList<Double> getP3(){ return P3;}
}
