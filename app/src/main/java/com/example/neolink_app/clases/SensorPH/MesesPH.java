package com.example.neolink_app.clases.SensorPH;

import com.example.neolink_app.clases.sensorNPK.DiasNPK;

import java.util.ArrayList;

public class MesesPH {
    private ArrayList<String> meses = new ArrayList<>();
    private ArrayList<DiasPH> dias = new ArrayList<>();
    public MesesPH(){}
    public MesesPH(ArrayList<String> meses, ArrayList<DiasPH> dias){
        this.meses = meses;
        this.dias = dias;
    }
    public String damemes(int posicion){return meses.get(posicion);}
    public DiasPH damedias(int posicion){return dias.get(posicion);}
    public ArrayList<DiasPH> damearraydedias(){return this.dias;}
    public int dametamanoNPK(){return this.dias.size();}
    public void tomameses(String mes, DiasPH dia){
        this.meses.add(mes);
        this.dias.add(dia);
    }
}
