package com.example.neolink_app.clases;

import java.util.ArrayList;

public class Dias {
    private ArrayList<String> dias = new ArrayList<>();
    private ArrayList<Horas> horas = new ArrayList<>();
    public Dias(){}
    public Dias(String dia, Horas hora){
        this.dias.add(dia);
        this.horas.add(hora);
    }
    public String damedia(int posicion){
        return this.dias.get(posicion);
    }
    public Horas damehora(int posicion){
        return this.horas.get(posicion);
    }
}
