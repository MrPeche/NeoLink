package com.example.neolink_app.clases;

import java.util.ArrayList;

public class Minutos {
    private ArrayList<String> minutos= new ArrayList<>();
    private ArrayList<Puerto> paquetes= new ArrayList<>();

    public Minutos(){}
    public Minutos(String minuto, Puerto paquete){
        this.minutos.add(minuto);
        this.paquetes.add(paquete);
    }
    public String dameminuto(int posicion){
        return this.minutos.get(posicion);
    }
    public Puerto damepaquete(int posicion){
        return this.paquetes.get(posicion);
    }
}
