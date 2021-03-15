package com.example.neolink_app.clases.SensorPH;

import com.example.neolink_app.clases.sensorNPK.dataPuertoNPK;

import java.util.ArrayList;

public class PuertosPH {
    private ArrayList<String> Puerto = new ArrayList<>();
    private ArrayList<dataPuertoPH> data = new ArrayList<>();
    public PuertosPH(){}
    public PuertosPH(ArrayList<String> Puerto, ArrayList<dataPuertoPH> data){
        this.Puerto = Puerto;
        this.data = data;
    }
    public void tomaPuerto(String Puerto, dataPuertoPH data){
        this.Puerto.add(Puerto);
        this.data.add(data);
    }
    public String damePuerto(int posicion){
        return this.Puerto.get(posicion);
    }
    public dataPuertoPH damedata(int posicion){
        return this.data.get(posicion);
    }
    public int dametamanoPH(){return this.Puerto.size();}
}
