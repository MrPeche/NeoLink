package com.example.neolink_app.clases.SensorG;

import java.util.ArrayList;

public class DiasG {
    private ArrayList<String> dias = new ArrayList<>();
    private ArrayList<HorasG> horas = new ArrayList<>();
    public DiasG(){}
    public DiasG(String dia, HorasG hora){
        this.dias.add(dia);
        this.horas.add(hora);
    }
    public String damedia(int posicion){
        return this.dias.get(posicion);
    }
    public HorasG damehora(int posicion){
        return this.horas.get(posicion);
    }
    public int dametamanoG(){ return this.dias.size();}
    public void tomadias(String dia, HorasG horitas){
        this.dias.add(dia);
        this.horas.add(horitas);
    }
}
