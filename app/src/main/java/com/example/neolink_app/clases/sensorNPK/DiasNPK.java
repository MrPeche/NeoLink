package com.example.neolink_app.clases.sensorNPK;

import com.example.neolink_app.clases.SensorG.HorasG;

import java.util.ArrayList;

public class DiasNPK {
    private ArrayList<String> dias = new ArrayList<>();
    private ArrayList<HorasNPK> horas = new ArrayList<>();
    public DiasNPK(){}
    public DiasNPK(ArrayList<String> dias, ArrayList<HorasNPK> horas){
        this.dias = dias;
        this.horas = horas;
    }
    public String damedia(int posicion){
        return this.dias.get(posicion);
    }
    public HorasNPK damehora(int posicion){
        return this.horas.get(posicion);
    }
    public int dametamanoNPK(){ return this.dias.size();}
    public void tomadias(String dia, HorasNPK horitas){
        this.dias.add(dia);
        this.horas.add(horitas);
    }
}
