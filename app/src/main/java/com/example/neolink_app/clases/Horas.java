package com.example.neolink_app.clases;

import java.util.ArrayList;

public class Horas {
    private ArrayList<String> horas = new ArrayList<>();
    private ArrayList<Minutos> minutos = new ArrayList<>();
    public Horas(){}
    public Horas(String hora, Minutos minuto){
        this.horas.add(hora);
        this.minutos.add(minuto);
    }
    public String damehora(int posicion){
        return this.horas.get(posicion);
    }
    public Minutos dameminutos(int posicion){
        return this.minutos.get(posicion);
    }

}
