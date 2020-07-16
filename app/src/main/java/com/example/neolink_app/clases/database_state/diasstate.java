package com.example.neolink_app.clases.database_state;

import java.util.ArrayList;

public class diasstate {
    private ArrayList<String> dias = new ArrayList<>();
    private ArrayList<horasstate> horas = new ArrayList<>();
    public diasstate(){}
    public diasstate(ArrayList<String> dias, ArrayList<horasstate> horas){
        this.dias = dias;
        this.horas = horas;
    }
    public String damedia(int posicion){ return this.dias.get(posicion);}
    public horasstate damehoras(int posicion){ return this.horas.get(posicion);}
    public int dametamano(){ return this.dias.size();}
}
