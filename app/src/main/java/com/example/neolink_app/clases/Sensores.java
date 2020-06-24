package com.example.neolink_app.clases;

import java.util.ArrayList;

public class Sensores {
    private ArrayList<String> sensores = new ArrayList<>();
    private ArrayList<Años> años = new ArrayList<>();
    public Sensores(){}
    public Sensores(String sensor,Años año){
        this.sensores.add(sensor);
        this.años.add(año);
    }
    public String damesensor(int posicion){
        return this.sensores.get(posicion);
    }
    public Años dameaños(int posicion){
        return this.años.get(posicion);
    }
}
