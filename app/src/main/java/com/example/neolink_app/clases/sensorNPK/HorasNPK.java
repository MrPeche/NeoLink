package com.example.neolink_app.clases.sensorNPK;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.neolink_app.clases.SensorG.HorasG;
import com.example.neolink_app.clases.SensorG.MinutosG;

import java.util.ArrayList;

public class HorasNPK {
    private ArrayList<String> horas = new ArrayList<>();
    private ArrayList<MinutosNPK> minutos = new ArrayList<>();
    public HorasNPK(){}
    public HorasNPK(ArrayList<String> horas, ArrayList<MinutosNPK> minutos){
        this.horas=horas;
        this.minutos=minutos;
    }
    public void tomaHoras(String hora, MinutosNPK minuto){
        this.horas.add(hora);
        this.minutos.add(minuto);
    }
    public String damehora(int posicion){
        return this.horas.get(posicion);
    }
    public MinutosNPK dameminutos(int posicion){
        return this.minutos.get(posicion);
    }
    public int dametamanoNPK(){return this.horas.size();}
}
