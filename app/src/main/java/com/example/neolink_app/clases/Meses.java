package com.example.neolink_app.clases;

import java.util.ArrayList;

public class Meses {
    private ArrayList<String> meses = new ArrayList<>();
    private ArrayList<Dias> dias = new ArrayList<>();
    public Meses(){}
    public Meses(String mes, Dias dia){
        this.meses.add(mes);
        this.dias.add(dia);
    }
    public String damemes(int posicion){
        return this.meses.get(posicion);
    }
    public Dias damedias(int posicion){
        return this.dias.get(posicion);
    }
    public void tomameses(String mes, Dias dia){
        this.meses.add(mes);
        this.dias.add(dia);
    }
}
