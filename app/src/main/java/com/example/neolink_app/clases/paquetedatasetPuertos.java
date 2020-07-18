package com.example.neolink_app.clases;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class paquetedatasetPuertos {
    ArrayList<Entry> P1 = new ArrayList<>();
    ArrayList<Entry> P2 = new ArrayList<>();
    ArrayList<Entry> P3 = new ArrayList<>();
    ArrayList<Entry> P4 = new ArrayList<>();

    public paquetedatasetPuertos(){
    }
    public void addP1(Entry data){
        P1.add(data);
    }
    public void addP2(Entry data){
        P2.add(data);
    }
    public void addP3(Entry data){
        P3.add(data);
    }
    public void addP4(Entry data){
        P4.add(data);
    }
    public ArrayList<Entry> getP1(){
        return P1;
    }
    public ArrayList<Entry> getP2(){
        return P2;
    }
    public ArrayList<Entry> getP3(){
        return P3;
    }
    public ArrayList<Entry> getP4(){
        return P4;
    }
    public void clean(){
        P1.clear();
        P2.clear();
        P3.clear();
        P4.clear();
    }
}
