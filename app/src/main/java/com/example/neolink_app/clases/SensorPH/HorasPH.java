package com.example.neolink_app.clases.SensorPH;

import com.example.neolink_app.clases.sensorNPK.MinutosNPK;

import java.util.ArrayList;

public class HorasPH {
    private ArrayList<String> horas = new ArrayList<>();
    private ArrayList<MinutosPH> minutos = new ArrayList<>();
    public HorasPH(){}
    public HorasPH(ArrayList<String> horas, ArrayList<MinutosPH> minutos){
        this.horas=horas;
        this.minutos=minutos;
    }
    public void tomaHoras(String hora, MinutosPH minuto){
        this.horas.add(hora);
        this.minutos.add(minuto);
    }
    public String damehora(int posicion){
        return this.horas.get(posicion);
    }
    public MinutosPH dameminutos(int posicion){
        return this.minutos.get(posicion);
    }
    public int dametamanoPH(){return this.horas.size();}
}
