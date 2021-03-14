package com.example.neolink_app.clases.SensorPH;

import com.example.neolink_app.clases.sensorNPK.dataPuertoNPK;

import java.util.ArrayList;

public class PuertosPH {
    private ArrayList<String> Puerto = new ArrayList<>();
    private ArrayList<dataPuertoNPK> data = new ArrayList<>();
    public PuertosPH(){}
    public PuertosPH(ArrayList<String> Puerto, ArrayList<dataPuertoNPK> data){
        this.Puerto = Puerto;
        this.data = data;
    }
    public void tomaPuerto(String Puerto, dataPuertoNPK data){
        this.Puerto.add(Puerto);
        this.data.add(data);
    }
    public String damePuerto(int posicion){
        return this.Puerto.get(posicion);
    }
    public dataPuertoNPK damedata(int posicion){
        return this.data.get(posicion);
    }
    public int dametamanoNPK(){return this.Puerto.size();}
}
