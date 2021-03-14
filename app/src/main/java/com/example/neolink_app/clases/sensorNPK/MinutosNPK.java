package com.example.neolink_app.clases.sensorNPK;

import android.os.Parcel;

import com.example.neolink_app.clases.SensorG.PuertoG;

import java.util.ArrayList;

public class MinutosNPK {
    private ArrayList<String> minutos= new ArrayList<>();
    private ArrayList<PuertoNPK> paquetes= new ArrayList<>();
    public MinutosNPK(){}
    public MinutosNPK(ArrayList<String> minutos, ArrayList<PuertoNPK> paquetes){
        this.minutos=minutos;
        this.paquetes=paquetes;
    }

    public void tomaMinutos(String minuto, PuertoNPK paquete){
        this.minutos.add(minuto);
        this.paquetes.add(paquete);
    }
    public String dameminuto(int posicion){
        return this.minutos.get(posicion);
    }
    public PuertoNPK damepaquete(int posicion){
        return this.paquetes.get(posicion);
    }
    public int dametamanoNPK(){return minutos.size();}
}
