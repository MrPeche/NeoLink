package com.example.neolink_app.clases.sensorNPK;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.neolink_app.clases.SensorG.PuertoG;
import com.example.neolink_app.clases.SensorG.dataPuertoG;

import java.util.ArrayList;

public class PuertoNPK {
    private ArrayList<String> Puerto = new ArrayList<>();
    private ArrayList<dataPuertoNPK> data = new ArrayList<>();
    public PuertoNPK(){}
    public PuertoNPK(ArrayList<String> Puerto, ArrayList<dataPuertoNPK> data){
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
