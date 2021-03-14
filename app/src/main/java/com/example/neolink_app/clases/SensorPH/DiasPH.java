package com.example.neolink_app.clases.SensorPH;

import com.example.neolink_app.clases.sensorNPK.HorasNPK;

import java.util.ArrayList;

public class DiasPH {
    private ArrayList<String> dias = new ArrayList<>();
    private ArrayList<HorasPH> horas = new ArrayList<>();
    public DiasPH(){}
    public DiasPH(ArrayList<String> dias, ArrayList<HorasPH> horas){
        this.dias = dias;
        this.horas = horas;
    }
    public String damedia(int posicion){
        return this.dias.get(posicion);
    }
    public HorasPH damehora(int posicion){
        return this.horas.get(posicion);
    }
    public int dametamanoPH(){ return this.dias.size();}
    public void tomadias(String dia, HorasPH horitas){
        this.dias.add(dia);
        this.horas.add(horitas);
    }
}
