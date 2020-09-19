package com.example.neolink_app.clases;

import java.util.ArrayList;

public class Dias {
    private ArrayList<String> dias = new ArrayList<>();
    private ArrayList<Horas> horas = new ArrayList<>();
    public Dias(){}
    public Dias(ArrayList<String> dia, ArrayList<Horas> hora){
        this.dias=dia;
        this.horas=hora;
    }
    public String damedia(int posicion){
        return this.dias.get(posicion);
    }
    public Horas damehora(int posicion){
        return this.horas.get(posicion);
    }
    public int dametamano(){ return this.dias.size();}
    public void tomadias(String dia, Horas horitas){
        this.dias.add(dia);
        this.horas.add(horitas);
    }
}
