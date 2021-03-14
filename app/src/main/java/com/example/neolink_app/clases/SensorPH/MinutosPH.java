package com.example.neolink_app.clases.SensorPH;

import com.example.neolink_app.clases.sensorNPK.PuertoNPK;

import java.util.ArrayList;

public class MinutosPH {
    private ArrayList<String> minutos= new ArrayList<>();
    private ArrayList<PuertosPH> paquetes= new ArrayList<>();
    public MinutosPH(){}
    public MinutosPH(ArrayList<String> minutos, ArrayList<PuertosPH> paquetes){
        this.minutos=minutos;
        this.paquetes=paquetes;
    }

    public void tomaMinutos(String minuto, PuertosPH paquete){
        this.minutos.add(minuto);
        this.paquetes.add(paquete);
    }
    public String dameminuto(int posicion){
        return this.minutos.get(posicion);
    }
    public PuertosPH damepaquete(int posicion){
        return this.paquetes.get(posicion);
    }
    public int dametamanoNPK(){return minutos.size();}
}
